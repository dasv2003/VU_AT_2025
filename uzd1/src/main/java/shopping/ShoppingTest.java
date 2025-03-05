package shopping;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;
public class ShoppingTest {
    private static final int WAIT_DURATION = 10;
    private static final String BASE_URL = System.getenv("BASE_URL") != null ? System.getenv("BASE_URL") : "https://demowebshop.tricentis.com/";
    private static final String CONTROL_A = Keys.chord(Keys.CONTROL, "A");
    private static final String BACKSPACE = Keys.chord(Keys.BACK_SPACE);
    public static void main(String[] args) {
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(WAIT_DURATION));
        try {
            driver.get(BASE_URL);
            driver.manage().window().maximize();
            Robot robot = new Robot();

            for (int i = 0; i < 3; i++) {
                robot.keyPress(KeyEvent.VK_CONTROL);
                robot.keyPress(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_SUBTRACT);
                robot.keyRelease(KeyEvent.VK_CONTROL);
            }
            navigateToGiftCards(wait);
            waitFor(2000);
            selectExpensiveProduct(driver, wait);
            fillGiftCardForm(wait);
            addToCartAndWishlist(wait);
            navigateToCustomJewelry(wait);
            customizeJewelry(wait);
            addToCartAndWishlist(wait);
            fillWishlistForm(wait, driver);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (AWTException e) {
            throw new RuntimeException(e);
        } finally {
            driver.quit();
        }
    }
    private static void fillWishlistForm(WebDriverWait wait, WebDriver driver) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("ico-wishlist"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody")));

        List<WebElement> addToCartCheckboxes = driver.findElements(By.xpath("//input[@name='addtocart']"));

        for (WebElement checkbox : addToCartCheckboxes) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
        wait.until(ExpectedConditions.elementToBeClickable(By.name("addtocartbutton"))).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("product-price")));
        WebElement subTotalElement = driver.findElement(By.className("product-price"));
        String subTotal = subTotalElement.getText();

        if (subTotal.equals("1002600.00")) {
            System.out.println("Sub-Total is correctly displayed: " + subTotal);
        } else {
            System.out.println("Sub-Total mismatch. Expected: 1002600.00, but found: " + subTotal);
        }

    }
    private static void addToCartAndWishlist(WebDriverWait wait) throws InterruptedException {
        wait.until(ExpectedConditions.elementToBeClickable(By.className("add-to-cart-button"))).click();
        waitFor(200);
        wait.until(ExpectedConditions.elementToBeClickable(By.className("add-to-wishlist-button"))).click();
    }
    private static void navigateToGiftCards(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Gift Cards"))).click();
    }
    private static void selectExpensiveProduct(WebDriver driver, WebDriverWait wait) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.item-box")));
        List<WebElement> products = driver.findElements(By.cssSelector("div.item-box"));
        for (WebElement product : products) {
            try {
                WebElement priceElement = product.findElement(By.xpath(".//span[contains(@class,'actual-price')]"));
                double price = Double.parseDouble(priceElement.getText());
                if (price > (double) 99) {
                    product.findElement(By.xpath(".//h2[@class='product-title']/a")).click();
                    return;
                }
            } catch (Exception e) {
                System.out.println("Error processing a product: " + e.getMessage());
            }
        }
    }
    private static void fillGiftCardForm(WebDriverWait wait) {
        setInput(wait, By.className("recipient-name"), "Jonas");
        setInput(wait, By.className("sender-name"), "Petras");
        setQuantity(wait, By.className("qty-input"), String.valueOf(5000));
    }
    private static void navigateToCustomJewelry(WebDriverWait wait) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Jewelry"))).click();
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Create Your Own Jewelry"))).click();
    }
    private static void customizeJewelry(WebDriverWait wait) {
        Select dropdown = new Select(wait.until(ExpectedConditions.elementToBeClickable(By.id("product_attribute_71_9_15"))));
        dropdown.selectByVisibleText("Silver (1 mm)");
        setInput(wait, By.id("product_attribute_71_10_16"), "80");
        wait.until(ExpectedConditions.elementToBeClickable(By.id("product_attribute_71_11_17_50"))).click();
        setQuantity(wait, By.className("qty-input"), String.valueOf(26));
    }
    private static void setInput(WebDriverWait wait, By locator, String value) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(value);
    }
    private static void setQuantity(WebDriverWait wait, By locator, String quantity) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        element.sendKeys(CONTROL_A);
        element.sendKeys(BACKSPACE);
        element.sendKeys(quantity);
    }
    private static void waitFor(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Interrupted while waiting", e);
        }
    }

}