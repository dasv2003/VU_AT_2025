package demoqaPages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    public void closeCookieNotification() {
        try {
            WebElement cookieNotification = driver.findElement(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]"));
            if (cookieNotification.isDisplayed()) {
                cookieNotification.click();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
