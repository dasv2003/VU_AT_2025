package demoqaPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProgressBarPage extends BasePage {
    public ProgressBarPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void pressStart() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("startStopButton"))).click();
    }

    public void resetBar() {
        WebElement bar = driver.findElement(By.cssSelector(".progress-bar"));
        wait.until(ExpectedConditions.attributeToBe(bar, "style", "width: 100%;"));
        wait.until(ExpectedConditions.elementToBeClickable(By.id("resetButton"))).click();
        wait.until(ExpectedConditions.attributeContains(bar, "style", "width: 0%"));
        if (bar.getText().equals("0%")) {
            System.out.println("Success! Bar is empty.");
        } else {
            System.out.println("Fail!");
        }
    }
}
