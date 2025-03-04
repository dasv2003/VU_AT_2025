package demoqaTests;

import demoqaPages.HomePage;
import demoqaPages.ElementsPage;
import demoqaPages.WebTablesPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class DemoqaTest2 {
    private static final int WAIT_DURATION = 10;
    private static final String BASE_URL = System.getenv("BASE_URL") != null
            ? System.getenv("BASE_URL")
            : "https://demoqa.com/";

    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION));

        try {
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            Robot robot = new Robot();
            for (int i = 0; i <7; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_CONTROL);
            }

            HomePage homePage = new HomePage(driver, wait);
            homePage.closeCookieNotification();
            homePage.navigateToElements();

            ElementsPage elementsPage = new ElementsPage(driver, wait);
            elementsPage.navigateToWebTables();

            WebTablesPage webTablesPage = new WebTablesPage(driver, wait);
            webTablesPage.selectSecondPage();
            webTablesPage.deleteElement();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
    }
}
