import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.concurrent.TimeUnit;

public class MyFirstTest {
    private static WebDriver driver;

    /**
     * Открывает браузер Google Chrome.
     */
    @BeforeClass
    public static void setup() {
        System.setProperty("webdriver.chrome.driver", "C:/Users/dengina.elena/Documents/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Открывает поиск Google.
     */
    @Test
    public void myFirstTest() {
        driver.get("https://google.com");
    }

    /**
     * Закрывает браузер.
     */
    @AfterClass
    public static void stop() {
        driver.quit();
        driver = null;
    }

}