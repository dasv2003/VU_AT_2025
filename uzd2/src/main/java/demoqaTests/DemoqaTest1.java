package demoqaTests;

import demoqaPages.HomePage;
import demoqaPages.WidgetsPage;
import demoqaPages.ProgressBarPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

public class DemoqaTest1 {
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
            for (int i = 0; i < 5; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_CONTROL);
            }

            HomePage homePage = new HomePage(driver, wait);
            homePage.closeCookieNotification();
            homePage.navigateToWidgets();

            WidgetsPage widgetsPage = new WidgetsPage(driver, wait);
            widgetsPage.navigateToProgressBar();

            ProgressBarPage progressBarPage = new ProgressBarPage(driver, wait);
            progressBarPage.pressStart();
            progressBarPage.resetBar();

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
    }
}
