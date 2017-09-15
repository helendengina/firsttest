import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ProductTest {
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
     * Открывает главную страницу учебного приложения litecart в браузере Mozilla Firefox.
     */
    public void startMozillaFirefox(){
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/en/");
    }

    /**
     * Открывает главную страницу учебного приложения litecart в браузере IE.
     */
    public void startIE(){
        driver = new InternetExplorerDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/en/");
    }

    /**
     * Проверяет совпадение названия товара на главной странице и на странице товара.
     */
    @Test
    public void productNameTest(){
        start();
        String productNameFromMainPage = getProductNameFromMainPage();
        String productNameFromProductPage = getProductNameFromProductPage();
        Assert.assertEquals(productNameFromMainPage, productNameFromProductPage);
        stop();
    }

    /**
     * Проверяет равенство обычной и акционной цен товара на главной странице и на странице товара.
     */
    @Test
    public void priceEqualityTest(){
        start();
        ArrayList<Integer> pricesFromMainPage = getPrice(getMainPageLocator());
        ArrayList<Integer> pricesFromProductPage = getPrice(getProductPageLocator());
        int regularPriceFromMainPage = pricesFromMainPage.get(0);
        int regularPriceFromProductPage = pricesFromProductPage.get(0);
        Assert.assertEquals(regularPriceFromMainPage, regularPriceFromProductPage);
        int campaignPriceFromMainPage = pricesFromMainPage.get(1);
        int campaignPageFromProductPage = pricesFromProductPage.get(1);
        Assert.assertEquals(campaignPriceFromMainPage, campaignPageFromProductPage);
        stop();
    }

    /**
     * Проверяет стиль цен (цвет, форматирование, разницу в размерах шрифта для обычной и акционной цен) на главной странице.
     */
    @Test
    public void priceStyleOnMainPageTest(){
        start();
        // Проверяет стиля обычной цены.
        ArrayList<String> regularPriceStyle = getPriceStyle(getRegularPriceLocation(getMainPageLocator()));
        String regularPriceColor = regularPriceStyle.get(0);
        Assert.assertEquals(regularPriceColor, "rgba(51, 51, 51, 1)");
        String regularPriceTextDecoration = regularPriceStyle.get(1);
        Assert.assertEquals(regularPriceTextDecoration, "S");
        String regularPriceSizeLine = regularPriceStyle.get(2);
        double regularPriceSize = Double.parseDouble(regularPriceSizeLine.substring(0, regularPriceSizeLine.length() - 2));
        stop();
        // Проверяет стиля аукционной цены.
        start();
        ArrayList<String> campaignPriceStyle = getPriceStyle(getCampaignPriceLocation(getMainPageLocator()));
        String campaignPriceColor = campaignPriceStyle.get(0);
        Assert.assertEquals(campaignPriceColor, "rgba(204, 0, 0, 1)");
        String campaignPriceTextDecoration = campaignPriceStyle.get(1);
        Assert.assertEquals(campaignPriceTextDecoration, "STRONG");
        String campaignPriceSizeLine = campaignPriceStyle.get(2);
        double campaignPriceSize = Double.parseDouble(campaignPriceSizeLine.substring(0, campaignPriceSizeLine.length() - 2));
        Assert.assertTrue(regularPriceSize < campaignPriceSize);
        stop();
    }

    /**
     * Проверяет стиль цен (цвет, форматирование, разницу в размерах шрифта для обычной и акционной цен) на странице продукта.
     */
    @Test
    public void priceStyleOnProductPageTest(){
        start();
        // Проверяет стиля обычной цены.
        ArrayList<String> regularPriceStyle = getPriceStyle(getRegularPriceLocation(getProductPageLocator()));
        String regularPriceColor = regularPriceStyle.get(0);
        Assert.assertEquals(regularPriceColor, "rgba(51, 51, 51, 1)");
        String regularPriceTextDecoration = regularPriceStyle.get(1);
        Assert.assertEquals(regularPriceTextDecoration, "DEL");
        String regularPriceSizeLine = regularPriceStyle.get(2);
        double regularPriceSize = Double.parseDouble(regularPriceSizeLine.substring(0, regularPriceSizeLine.length() - 2));
        stop();
        // Проверяет стиля аукционной цены.
        start();
        ArrayList<String> campaignPriceStyle = getPriceStyle(getCampaignPriceLocation(getProductPageLocator()));
        String campaignPriceColor = campaignPriceStyle.get(0);
        Assert.assertEquals(campaignPriceColor, "rgba(204, 0, 0, 1)");
        String campaignPriceTextDecoration = campaignPriceStyle.get(1);
        Assert.assertEquals(campaignPriceTextDecoration, "STRONG");
        String campaignPriceSizeLine = campaignPriceStyle.get(2);
        double campaignPriceSize = Double.parseDouble(campaignPriceSizeLine.substring(0, campaignPriceSizeLine.length() - 2));
        Assert.assertTrue(regularPriceSize < campaignPriceSize);
        stop();
    }

    /**
     * Находит сравниваемый товар на главной странице.
     * @return локатор сравниваемого товара.
     */
    public WebElement getMainPageLocator(){
        List<WebElement> mainPageInfos = driver.findElements(By.cssSelector("#box-campaign-products > div > div > div > a > div.info"));
        WebElement locator = mainPageInfos.get(0);
        for (WebElement info:mainPageInfos
             ) {
            if (info.isDisplayed()){
                locator = info;
            }
        }
        return locator;
    }

    /**
     * Находит сравниваемый товар на странице товара.
     * @return локатор сравниваемого товара.
     */
    public WebElement getProductPageLocator(){
        WebElement productField = driver.findElement(By.cssSelector("#content > div.tab-content"));
        List<WebElement> links = productField.findElements(By.cssSelector("#box-campaign-products > div > div > div > a"));
        for (WebElement link:links
             ) {
            if (link.isDisplayed()) {
                link.click();
            }
        }
        return driver.findElement(By.cssSelector("body > div.featherlight.active > div"));
    }

    /**
     * Находит обычную и акционную цены товара.
     * @return 2 целых числа, первое - обычная цена, второе - акционная.
     */
    public ArrayList<Integer> getPrice(WebElement locator){
        ArrayList<Integer> prices = new ArrayList<>();
        WebElement regularPriceField = getRegularPriceLocation(locator);
        String regularPriceLine = regularPriceField.getAttribute("textContent").substring(1);
        Integer regularPrice = Integer.parseInt(regularPriceLine);
        prices.add(regularPrice);
        WebElement campaignPriceField = getCampaignPriceLocation(locator);
        String campaignPriceLine = campaignPriceField.getAttribute("textContent").substring(1);
        Integer campaignPrice = Integer.parseInt(campaignPriceLine);
        prices.add(campaignPrice);
        return prices;
    }

    /**
     * Сохраняет стиль цен (цвет, форматирование, размер шрифта.
     * @param priceField веб-элемент, содержащий цену.
     * @return стилевые свойства элемента: цвет (color), форматирование (textDecoration), размер шрифта (size).
     */
    public ArrayList<String> getPriceStyle(WebElement priceField) {
        ArrayList<String> priceProperties = new ArrayList<>();
        String color = priceField.getCssValue("color");
        String textDecoration = priceField.getAttribute("tagName");
        String size = priceField.getCssValue("font-size");
        priceProperties.add(color);
        priceProperties.add(textDecoration);
        priceProperties.add(size);
        return priceProperties;
    }

    /**
     * Находит веб-элемент, содержащий обычную цену.
     * @param locator локатор товара.
     * @return веб-элемент, содержащий обычную цену.
     */
    public WebElement getRegularPriceLocation(WebElement locator){
        WebElement priceWrapper = locator.findElement(By.className("price-wrapper"));
        return priceWrapper.findElement(By.className("regular-price"));
    }

    /**
     * Находит веб-элемент, содержащий аукционную цену.
     * @param locator локатор товара.
     * @return веб-элемент, содержащий аукционную цену.
     */
    public WebElement getCampaignPriceLocation(WebElement locator){
        WebElement priceWrapper = locator.findElement(By.className("price-wrapper"));
        return priceWrapper.findElement(By.className("campaign-price"));
    }

    /**
     * Возвращает название продукта на главной странице.
     * @return название продукта на главной странице.
     */
    public String getProductNameFromMainPage() {
        WebElement locator = getMainPageLocator();
        WebElement name = locator.findElement(By.className("name"));
        return name.getAttribute("textContent");
    }

    /**
     * Возвращает название продукта на странице продукта.
     * @return название продукта на странице продукта.
     */
    public String getProductNameFromProductPage () {
        WebElement locator = getProductPageLocator();
        WebElement nameField = locator.findElement(By.cssSelector("#box-product > div > div:nth-child(2)"));
        WebElement name = nameField.findElement(By.tagName("h1"));
        return name.getAttribute("textContent");
    }

    /**
     * Закрывает браузер.
     */
    public static void stop() {
        driver.quit();
        driver = null;
    }
}