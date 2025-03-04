package demoqaPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WidgetsPage extends BasePage {
    public WidgetsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void navigateToProgressBar() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Progress Bar']"))).click();
    }
}
