import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;

public class LoginTest {
    private static WebDriver driverChrome;
    private static WebDriver driverFirefox;
    private static WebDriver driverIE;

    /**
     * Открывает браузер Google Chrome.
     */
    @Test
    public static void openChrome() {
        driverChrome = new ChromeDriver();
        driverChrome.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Открывает браузер Mozilla Firefox.
     */
    @Test
    public static void openFirefox() {
        driverFirefox = new FirefoxDriver();
        driverFirefox.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Открывает браузер IE.
     */
    @Test
    public static void openIE() {
        driverIE = new InternetExplorerDriver();
        driverIE.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    /**
     * Залогинивается в панели администрирования учебного приложения в браузере Google Chrome.
     */
    @Test(dependsOnMethods = {"openChrome"}, alwaysRun = true)
    public void loginTestChrome() throws InterruptedException {
        driverChrome.get("http://localhost/litecart/admin/");
        WebElement loginField = (new WebDriverWait(driverChrome,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.name("username"))));
        loginField.sendKeys("admin");
        WebElement passwordField = driverChrome.findElement(By.name("password"));
        passwordField.sendKeys("admin");
        WebElement loginButton = driverChrome.findElement(By.cssSelector("#box-login > form > div.footer > button"));
        loginButton.click();
    }

    /**
     * Залогинивается в панели администрирования учебного приложения в браузере Mozilla Firefox.
     */
    @Test(dependsOnMethods = {"openFirefox"}, alwaysRun = true)
    public void loginTestFirefox() throws InterruptedException {
        driverFirefox.get("http://localhost/litecart/admin/");
        WebElement loginField = (new WebDriverWait(driverFirefox,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.name("username"))));
        loginField.sendKeys("admin");
        WebElement passwordField = driverFirefox.findElement(By.name("password"));
        passwordField.sendKeys("admin");
        WebElement loginButton = driverFirefox.findElement(By.cssSelector("#box-login > form > div.footer > button"));
        loginButton.click();
    }

    /**
     * Залогинивается в панели администрирования учебного приложения в браузере IE.
     */
    @Test(dependsOnMethods = {"openIE"}, alwaysRun = true)
    public void loginTestIE() throws InterruptedException {
        driverIE.get("http://localhost/litecart/admin/");
        WebElement loginField = (new WebDriverWait(driverIE,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.name("username"))));
        loginField.sendKeys("admin");
        WebElement passwordField = driverIE.findElement(By.name("password"));
        passwordField.sendKeys("admin");
        WebElement loginButton = driverIE.findElement(By.cssSelector("#box-login > form > div.footer > button"));
        loginButton.click();
    }

    /**
     * Закрывает браузеры.
     */
    @AfterSuite
    public static void stop() {
        if (driverChrome != null) {
            driverChrome.quit();
            driverChrome = null;
        }
        if (driverFirefox != null) {
            driverFirefox.quit();
            driverFirefox = null;
        }
        if (driverIE != null) {
            driverIE.quit();
            driverIE = null;
        }
    }
}