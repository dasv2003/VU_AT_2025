package demoWebShop;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


public class OrderTest extends BaseTest {
    @BeforeClass
    public static void setUpUser() {
        if (UserCreationTest.email == null || UserCreationTest.password == null) {
            UserCreationTest.testUserRegistration();
        }
    }

    @Test
    public void testOrderWithData1()  {
        placeOrder("data1.txt");
    }

    @Test
    public void testOrderWithData2()    {
        placeOrder("data2.txt");
    }

    private void placeOrder(String filename) {
        driver.get("https://demowebshop.tricentis.com/");
        driver.manage().window().maximize();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("Email"))).sendKeys(UserCreationTest.email);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("Password"))).sendKeys(UserCreationTest.password);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Log in']"))).click();

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Digital downloads"))).click();

        List<String> products = readProductData(filename);
        for (String product : products) {
            wait.until(ExpectedConditions.elementToBeClickable(By.linkText(product))).click();
            wait.until(ExpectedConditions.elementToBeClickable(By.className("add-to-cart-button"))).click();
            driver.navigate().back();
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Shopping cart"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("termsofservice"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();

        List<WebElement> addressSelectList = driver.findElements(By.id("billing-address-select"));

        if (!addressSelectList.isEmpty() && addressSelectList.get(0).isDisplayed()) {

            wait.until(ExpectedConditions.elementToBeClickable(
                            By.className("new-address-next-step-button")))
                    .click();

        } else {
            WebElement countryDropdownElement = driver.findElement(By.id("BillingNewAddress_CountryId"));
            Select countryDropdown = new Select(countryDropdownElement);
            countryDropdown.selectByVisibleText("Lithuania");

            wait.until(ExpectedConditions.elementToBeClickable(By.id("BillingNewAddress_City")))
                    .sendKeys("MyCity");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("BillingNewAddress_Address1")))
                    .sendKeys("123 Street");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("BillingNewAddress_ZipPostalCode")))
                    .sendKeys("12345");
            wait.until(ExpectedConditions.elementToBeClickable(By.id("BillingNewAddress_PhoneNumber")))
                    .sendKeys("1234567890");

            wait.until(ExpectedConditions.elementToBeClickable(
                            By.className("new-address-next-step-button")))
                    .click();
        }

        wait.until(ExpectedConditions.elementToBeClickable(By.className("payment-method-next-step-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("payment-info-next-step-button"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.className("confirm-order-next-step-button"))).click();

        WebElement successMessageElement = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.order-completed strong")
                )
        );

        String successMessage = successMessageElement.getText();
        Assert.assertEquals("Your order has been successfully processed!", successMessage);

    }

    private List<String> readProductData(String filename) {
        try {
            return Files.readAllLines(Paths.get("src/main/resources/" + filename));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
