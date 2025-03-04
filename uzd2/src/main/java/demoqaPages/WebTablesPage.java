package demoqaPages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebTablesPage extends BasePage {
    public WebTablesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    public void selectSecondPage() throws InterruptedException {
        WebElement totalPagesElement = driver.findElement(By.xpath("//span[contains(@class, '-totalPages')]"));
        int totalPages = Integer.parseInt(totalPagesElement.getText().trim());
        if (totalPages < 2) {
            WebElement addButton = driver.findElement(By.id("addNewRecordButton"));
            for (int i = 1; i <= 10; i++) {
                addButton.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("firstName")));
                driver.findElement(By.id("firstName")).sendKeys("First" + i);
                driver.findElement(By.id("lastName")).sendKeys("Last" + i);
                driver.findElement(By.id("userEmail")).sendKeys("email" + i + "@example.com");
                driver.findElement(By.id("age")).sendKeys("25");
                driver.findElement(By.id("salary")).sendKeys("5000");
                driver.findElement(By.id("department")).sendKeys("Dept" + i);
                driver.findElement(By.id("submit")).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("firstName")));
                totalPagesElement = driver.findElement(By.xpath("//span[contains(@class, '-totalPages')]"));
                totalPages = Integer.parseInt(totalPagesElement.getText().trim());
                if (totalPages >= 2) {
                    break;
                }
            }
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Next']"))).click();
        }
    }

    public void deleteElement() throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@title='Delete']"))).click();
        WebElement currentPageInput = driver.findElement(By.xpath("//div[contains(@class, '-pageJump')]//input[@aria-label='jump to page']"));
        currentPageInput.click();
        driver.findElement(By.id("app")).click();
        wait.until(ExpectedConditions.attributeToBe(currentPageInput, "value", "1"));
        WebElement totalPagesElement = driver.findElement(By.xpath("//span[contains(@class, '-totalPages')]"));
        wait.until(ExpectedConditions.textToBePresentInElement(totalPagesElement, "1"));
        if(totalPagesElement.getText().equals("1")) {
            System.out.println("Success!");
        } else {
            System.out.println("Fail!");
        }
    }
}
