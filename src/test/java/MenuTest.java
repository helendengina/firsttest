import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.AfterSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class MenuTest {
    private static WebDriver driver;
    private static WebDriverWait wait;

    /**
     * Залогинивается в панели администратора в браузере Google Chrome.
     */
    public void login(){
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/admin/");
        WebElement loginField = (new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.name("username"))));
        loginField.sendKeys("admin");
        WebElement passwordField = driver.findElement(By.name("password"));
        passwordField.sendKeys("admin");
        WebElement loginButton = driver.findElement(By.cssSelector("#box-login > form > div.footer > button"));
        loginButton.click();
    }

    /**
     * Прокликивает последовательно все пункты меню слева, включая вложенные пункты, и
     * для каждой страницы проверяет наличие заголовка.
     */
    @Test
    public void menuTest() {
        login();
        wait = new WebDriverWait(driver, 10);
        // Проверяет первую вкладку.
        clickOnMenuAndCheckHeader(0);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-template > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-logotype > a"));
        // Проверяет вторую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(1);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-catalog > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-product_groups > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-option_groups > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-manufacturers > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-suppliers > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-delivery_statuses > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-sold_out_statuses > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-quantity_units > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-csv > a"));
        // Проверяет третью вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(2);
        // Проверяет четвёртую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(3);
        // Проверяет пятую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(4);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-customers > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-csv > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-newsletter > a"));
        // Проверяет шестую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(5);
        // Проверяет седьмую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(6);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-languages > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-storage_encoding > a"));
        // Проверяет восьмую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(7);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-customer > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-shipping > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-payment > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-order > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-order_total > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-jobs > a"));
        // Проверяет 9-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(8);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-orders > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-order_statuses > a"));
        // Проверяет 10-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(9);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-pages > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-csv > a"));
        // Проверяет 11-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(10);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-monthly_sales > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-most_sold_products > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-most_shopping_customers > a"));
        // Проверяет 12-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(11);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-store_info > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-defaults > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-listings > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-images > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-checkout > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-advanced > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-security > a"));
        // Проверяет 13-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(12);
        // Проверяет 14-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(13);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-tax_rates > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-tax_classes > a"));
        // Проверяет 15-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(14);
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-search > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-scan > a"));
        clickOnButtonAndCheckHeader(By.cssSelector("#doc-csv > a"));
        // Проверяет 16-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(15);
        // Проверяет 17-ую вкладку.
        driver.get("http://localhost/litecart/admin/");
        clickOnMenuAndCheckHeader(16);
    }

    /**
     * Прокликивает пункты меню и проверяет наличие заголовка на каждой странице.
     * @param count номер пункта меню, начиная с 0.
     */
    public void clickOnMenuAndCheckHeader(int count) {
        WebElement menu = (wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#box-apps-menu"))));
        List<WebElement> items = menu.findElements(By.tagName("li"));
        WebElement item = items.get(count);
        item.click();
        isElementPresent(driver, By.tagName("h1"));
    }

    /**
     * Прокликивает вложенные пункты меню и проверяет наличие заголовка на каждой странице.
     * @param locator критерий поиска элемента на странице.
     */
    public void clickOnButtonAndCheckHeader(By locator) {
        WebElement link = driver.findElement(locator);
        link.click();
        isElementPresent(driver, By.tagName("h1"));
    }

    /**
     * Проверяет наличие элемента по заданному критерию поиска.
     * @param driver драйвер.
     * @param locator критерий поиска элемента на странице.
     * @return возвращает true, если элемент найден, и false, если не найден.
     */
    boolean isElementPresent(WebDriver driver, By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException ex) {
            return false;
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
