package demoqaPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePage {
    public HomePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void navigateToElements() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Elements']"))).click();
    }

    public void navigateToWidgets() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//h5[text()='Widgets']"))).click();
    }
}
