package demoWebShop;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class UserCreationTest{
    public static String email;
    public static String password;
    private static final int WAIT_DURATION = 10;

    public static void testUserRegistration(){
        WebDriverManager.chromedriver().setup();
        WebDriver userDriver = new ChromeDriver();
        userDriver.manage().window().maximize();
        userDriver.get("https://demowebshop.tricentis.com/");

        WebDriverWait userwait = new WebDriverWait(userDriver, Duration.ofSeconds(WAIT_DURATION));
        userwait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in"))).click();
        userwait.until(ExpectedConditions.elementToBeClickable(By.linkText("Register"))).click();
        email = "user" + System.currentTimeMillis() + "@example.com";
        password = "Password123!";
        userwait.until(ExpectedConditions.elementToBeClickable(By.id("gender-male"))).click();
        userwait.until(ExpectedConditions.elementToBeClickable(By.id("FirstName"))).sendKeys("Jonas");
        userwait.until(ExpectedConditions.elementToBeClickable(By.id("LastName"))).sendKeys("Jonaitis");
        userwait.until(ExpectedConditions.elementToBeClickable(By.id("Email"))).sendKeys(email);
        userwait.until(ExpectedConditions.elementToBeClickable(By.id("Password"))).sendKeys(password);
        userwait.until(ExpectedConditions.elementToBeClickable(By.id("ConfirmPassword"))).sendKeys(password);
        userwait.until(ExpectedConditions.elementToBeClickable(By.id("register-button"))).click();
        userwait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@value='Continue']"))).click();
        userDriver.quit();
    }
}
