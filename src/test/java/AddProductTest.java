import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AddProductTest {
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
     * Проверяет добавление товара в каталоге.
     */
    @Test
    public void addProductTest(){
        login();
        addNewProduct();

    }

    /**
     * Добавляет новый товар в каталог на вкладке Catalog.
     */
    public void addNewProduct(){
        clickOnMenu();
        WebElement newProductButton = driver.findElement(By.cssSelector("#main > ul > li:nth-child(3) > a"));
        newProductButton.click();
        // Заполняет поля на вкладке General.
        WebElement content = driver.findElement(By.className("tab-content"));
        WebElement productGroupsCheckboxField = content.findElement(By.cssSelector("#tab-general > div > div:nth-child(1) > div:nth-child(4)"));
        WebElement productGroupsCheckbox = productGroupsCheckboxField.findElement(By.cssSelector("#tab-general > div > div:nth-child(1) > div:nth-child(4) > div > div"));
        List<WebElement> checkItems = productGroupsCheckbox.findElements(By.className("checkbox"));
        checkItems.get(2).click();
        WebElement nameForm = driver.findElement(By.cssSelector("#tab-general > div > div:nth-child(2) > div:nth-child(1)"));
        WebElement nameField = nameForm.findElement(By.name("name[en]"));
        String name = "Orange Duck";
        nameField.sendKeys(name);
        WebElement codeForm = driver.findElement(By.cssSelector("#tab-general > div > div:nth-child(2) > div:nth-child(2)"));
        WebElement codeField = codeForm.findElement(By.name("code"));
        codeField.sendKeys("1234");
        WebElement SKUField = driver.findElement(By.name("sku"));
        SKUField.sendKeys("123456");
        WebElement GTINField = driver.findElement(By.name("gtin"));
        GTINField.sendKeys("1234567");
        WebElement TARICField = driver.findElement(By.name("taric"));
        TARICField.sendKeys("23445543");
        WebElement quantityForm = driver.findElement(By.cssSelector("#tab-general > div > div:nth-child(2) > div:nth-child(4)"));
        WebElement quantityField = quantityForm.findElement(By.name("quantity"));
        quantityField.sendKeys(Keys.BACK_SPACE + "10");
        WebElement weightForm = driver.findElement(By.cssSelector("#tab-general > div > div:nth-child(2) > div:nth-child(5)"));
        WebElement weightField = weightForm.findElement(By.name("weight"));
        weightField.clear();
        weightField.sendKeys("0,25");
        WebElement parametersForm = driver.findElement(By.cssSelector("#tab-general > div > div:nth-child(2) > div:nth-child(6) > div"));
        WebElement widthField = parametersForm.findElement(By.name("dim_x"));
        widthField.sendKeys(Keys.LEFT, Keys.LEFT, Keys.LEFT, Keys.DELETE + "22");
        WebElement heightField = parametersForm.findElement(By.name("dim_y"));
        heightField.clear();
        heightField.sendKeys("2,34");
        WebElement lengthField = parametersForm.findElement(By.name("dim_z"));
        new Actions(driver)
                .moveToElement(lengthField)
                .keyDown(Keys.CONTROL)
                .clickAndHold()
                .sendKeys("a")
                .keyUp(Keys.CONTROL)
                .pause(5)
                .sendKeys("14.6")
                .perform();
        WebElement imagesForm = driver.findElement(By.id("images"));
        WebElement imageField = imagesForm.findElement(By.name("new_images[]"));
        File image = new File("https://github.com/helendengina/firsttest/blob/master/src/test/java/OrangeDuck.jpg");
        String absolutePathToImage = image.getAbsolutePath();
        imageField.sendKeys(absolutePathToImage);
        // Переходит на вкладку Information.
        WebElement informationButton = driver.findElement(By.linkText("Information"));
        informationButton.click();
        // Заполняет поля на вкладке Information.
        WebElement informationContent = driver.findElement(By.className("tab-content"));
        Select selectManufacturer = new Select(informationContent.findElement(By.name("manufacturer_id")));
        selectManufacturer.selectByIndex(1);
        WebElement shortDescriptionField = informationContent.findElement(By.name("short_description[en]"));
        shortDescriptionField.sendKeys("Short Description");
        WebElement descriptionForm = informationContent.findElement(By.cssSelector("#tab-information > div:nth-child(4) > div"));
        WebElement descriptionField = descriptionForm.findElement(By.className("trumbowyg-editor"));
        descriptionField.sendKeys("Description");
        WebElement attributesForm = informationContent.findElement(By.cssSelector("#tab-information > div:nth-child(5) > div"));
        WebElement attributesField = attributesForm.findElement(By.name("attributes[en]"));
        attributesField.sendKeys("Color: orange.");
        // Переходит на вкладку Prices.
        WebElement pricesButton = driver.findElement(By.linkText("Prices"));
        pricesButton.click();
        // Заполняет поля на вкладке Prices.
        WebElement purchasePriceForm = driver.findElement(By.cssSelector("#prices > div > div:nth-child(1)"));
        WebElement purchasePriceField = purchasePriceForm.findElement(By.name("purchase_price"));
        purchasePriceField.clear();
        purchasePriceField.sendKeys("2,1");
        Select selectCurrency = new Select(purchasePriceForm.findElement(By.name("purchase_price_currency_code")));
        selectCurrency.selectByIndex(2);
        Select selectTaxClass = new Select(driver.findElement(By.name("tax_class_id")));
        selectTaxClass.selectByIndex(0);
        WebElement priceUSDField = driver.findElement(By.name("prices[USD]"));
        priceUSDField.clear();
        priceUSDField.sendKeys("2,19");
        WebElement priceEURField = driver.findElement(By.name("prices[EUR]"));
        priceEURField.clear();
        priceEURField.sendKeys("2,1");
        WebElement grossPriceUSD = driver.findElement(By.name("gross_prices[USD]"));
        grossPriceUSD.clear();
        grossPriceUSD.sendKeys("2,23");
        WebElement grossPriceEUR = driver.findElement(By.name("gross_prices[EUR]"));
        grossPriceEUR.clear();
        grossPriceEUR.sendKeys("2,23");
        // Переходит на вкладку General для сохранения продукта.
        WebElement generalLink = driver.findElement(By.linkText("General"));
        generalLink.click();
        saveSettings();
        clickOnMenu();
        Assert.assertFalse(isProductInCatalog(name));
    }

    /**
     * Кликает на вкладку Catalog в меню.
     */
    public void clickOnMenu(){
        WebElement sidebar = driver.findElement(By.id("sidebar"));
        WebElement menu = sidebar.findElement(By.cssSelector("#box-apps-menu"));
        List<WebElement> items = menu.findElements(By.tagName("li"));
        WebElement catalogField = items.get(1);
        catalogField.click();
    }

    /**
     * Проверяет, есть ли добавленный товар в каталоге товаров.
     * @param productName название добавленного товара.
     * @return true, если есть, false, если нет.
     */
    public boolean isProductInCatalog (String productName){
        boolean flag = false;
        WebElement table = driver.findElement(By.className("tbody"));
        List<WebElement> tableLines = table.findElements(By.tagName("tr"));
        for (WebElement line:tableLines
             ) {
            String lineText = line.getAttribute("textContent");
            if (lineText.indexOf(productName) > -1) {
                flag = true;
            }
        }
        return flag;
    }

    /**
     * Сохраняет настройки.
     */
    public void saveSettings() {
        WebElement button = driver.findElement(By.name("save"));
        button.click();
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