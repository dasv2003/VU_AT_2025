package demoqaPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ElementsPage extends BasePage {
    public ElementsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void navigateToWebTables() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Web Tables']"))).click();
    }
}
