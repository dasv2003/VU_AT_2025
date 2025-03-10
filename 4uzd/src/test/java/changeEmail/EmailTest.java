package changeEmail;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Objects;

public class EmailTest extends BaseTest{

    private static final String CONTROL_A = Keys.chord(Keys.CONTROL, "A");
    private static final String BACKSPACE = Keys.chord(Keys.BACK_SPACE);
    @BeforeClass
    public static void setUpUser() {
        if (UserCreationTest.email == null || UserCreationTest.password == null) {
            UserCreationTest.testUserRegistration();
        }
    }
    @Test
    public void testChangeEmail() {
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("Email"))).sendKeys(UserCreationTest.email);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("Password"))).sendKeys(UserCreationTest.password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log in']"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("account"))).click();
        WebElement emailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("Email"))
        );
        emailField.sendKeys(CONTROL_A);
        emailField.sendKeys(BACKSPACE);
        String newEmail = "user" + System.currentTimeMillis() + "@example.com";
        emailField.sendKeys(newEmail);
        wait.until(ExpectedConditions.elementToBeClickable(By.name("save-info-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log out"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("Email"))).sendKeys(UserCreationTest.email);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("Password"))).sendKeys(UserCreationTest.password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log in']"))).click();
        boolean oldEmailLoginFailed = Objects.requireNonNull(driver.getPageSource()).contains("Login was unsuccessful");
        Assert.assertTrue("Expected login to fail with old email, but it succeeded.", oldEmailLoginFailed);

        WebElement newEmailField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("Email"))
        );
        newEmailField.sendKeys(CONTROL_A);
        newEmailField.sendKeys(BACKSPACE);
        newEmailField.sendKeys(newEmail);
        WebElement passwordField = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("Password"))
        );
        passwordField.sendKeys(CONTROL_A);
        passwordField.sendKeys(BACKSPACE);
        passwordField.sendKeys(UserCreationTest.password);
        driver.findElement(By.xpath("//input[@value='Log in']")).click();

        WebElement accountLink = driver.findElement(By.cssSelector("a.account"));
        String displayedEmail = accountLink.getText();

        Assert.assertEquals("Email mismatch in My account link", newEmail, displayedEmail);


    }

}
