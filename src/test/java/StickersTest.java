import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class StickersTest extends Assert {
    private static WebDriver driver;

    /**
     * Открывает главную страницу учебного приложения litecart в браузере Google Chrome.
     */
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/en/");
    }

    /**
     * Проверяет наличие и количество стикеров у всех товаров на главной странице приложения.
     */
    @Test
    public void stickersTest() {
        start();
        List<WebElement> imageWrappers = driver.findElements(By.className("image-wrapper"));
        for (WebElement imageWrapper:imageWrappers
             ) {
            Assert.assertTrue(imageWrapper.findElements(By.className("sticker")).size() == 1);
        }
    }

    /**
     * Закрывает браузер.
     */
    @AfterSuite
    public static void stop() {
        driver.quit();
        driver = null;
    }
}