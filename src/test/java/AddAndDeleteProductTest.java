import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class AddAndDeleteProductTest {
    private static WebDriver driver;

    /**
     * Проверяет добавление товаров в корзину.
     */
    @Test
    public void addToCartTest(){
        start();
        goToPopularProducts();
        int productCountBeforeAdding = productCounter();
        List<WebElement> products = getProducts();
        for (int i = 0; i < 3; i++){
            WebElement product = products.get(i);
            addToShoppingCart(product);
            closeProductWindow();
        }
        int productCountAfterAdding = productCounter();
        Assert.assertEquals(productCountAfterAdding - productCountBeforeAdding, 3);
    }

    /**
     * Открывает главную страницу учебного приложения litecart.
     */
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/en/");
    }

    /**
     * Добавляет товар в корзину.
     * @param linkOnProduct ссылка на товар.
     */
    public void addToShoppingCart(WebElement linkOnProduct){
        linkOnProduct.click();
        WebElement window = driver.findElement(By.className("featherlight-content"));
        String productName = window.findElement(By.tagName("h1")).getAttribute("textContent");
        if (productName.compareTo("Yellow Duck") == 0) {
            WebElement selectField = window.findElement(By.cssSelector("#box-product > div > div:nth-child(3) > div.buy_now > form > div:nth-child(3)"));
            Select selectSize = new Select(selectField.findElement(By.name("options[Size]")));
            selectSize.selectByIndex(1);
        }
        WebElement addToCartButton = window.findElement(By.name("add_cart_product"));
        addToCartButton.click();
    }

    /**
     * Закрывает карточку товара.
     */
    public void closeProductWindow(){
        WebElement window = driver.findElement(By.className("featherlight-content"));
        WebElement closeButton = window.findElement(By.cssSelector("body > div.featherlight.active > div > button"));
        closeButton.click();
    }

    /**
     * Возвращает количество продуктов в корзине.
     * @return количество продуктов в корзине.
     */
    public int productCounter() {
        WebElement counterField = driver.findElement(By.cssSelector("#cart > a > div"));
        WebElement counterLine = counterField.findElement(By.cssSelector("#cart > a > div > div:nth-child(2)"));
        WebElement counter = counterLine.findElement(By.className("quantity"));
        return Integer.parseInt(counter.getAttribute("textContent"));
    }

    /**
     * Переходит на ссылку Popular Products на главной странице.
     */
    public void goToPopularProducts(){
        WebElement content = driver.findElement(By.id("content"));
        WebElement link = content.findElement(By.linkText("Popular Products"));
        link.click();
    }

    /**
     * Находит товары на вкладке Popular Products.
     * @return список товаров.
     */
    public List<WebElement> getProducts() {
        WebElement content = driver.findElement(By.className("tab-content"));
        WebElement products = content.findElement(By.cssSelector("#box-popular-products > div"));
        return products.findElements(By.tagName("div"));
    }

    /**
     * Закрывает браузер.
     */
    //@AfterSuite
    public static void stop() {
        driver.quit();
        driver = null;
    }
}