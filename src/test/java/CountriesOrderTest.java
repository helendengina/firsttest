import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class CountriesOrderTest {
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
     * Проверяет сортировку стран и геозон в админке на вкладках Countries и Geo Zones.
     */
    @Test
    public void countriesOrderTest() {
        login();
        wait = new WebDriverWait(driver, 10);
        // Проверка на вкладке Countries.
        clickOnMenu(2);
        List<WebElement> tableLines = returnTableLines();
        ArrayList<String> countriesList = returnCountriesList(tableLines);
        Assert.assertFalse(isOrderCorrect(countriesList));
        for (int i = 0; i < tableLines.size(); i++) {
            WebElement tableLine = tableLines.get(i);
            if (isThereAZone(tableLine)) {
                ((Locatable) tableLine).getCoordinates().inViewPort();
                List<WebElement> elements = tableLine.findElements(By.tagName("td"));
                WebElement editField = elements.get(6);
                WebElement button = editField.findElement(By.cssSelector(".fa.fa-pencil"));
                button.click();
                ArrayList<String> zones = returnZonesList();
                Assert.assertFalse(isOrderCorrect(zones));
                clickOnMenu(2);
            }
            tableLines = returnTableLines();
        }
        // Проверка на вкладке Geo Zones.
        clickOnMenu(5);
        List<WebElement> geoZones = returnTableLines();
        for (int i = 0; i < geoZones.size(); i++) {
            WebElement tableLine = geoZones.get(i);
            ((Locatable) tableLine).getCoordinates().inViewPort();
            List<WebElement> elements = tableLine.findElements(By.tagName("td"));
            WebElement editField = elements.get(4);
            WebElement button = editField.findElement(By.cssSelector(".fa.fa-pencil"));
            button.click();
            ArrayList<String> zones = returnGeoZonesList();
            Assert.assertFalse(isOrderCorrect(zones));
            clickOnMenu(5);
            geoZones = returnTableLines();
        }
    }

    /**
     * Находит строчки в таблице Countries.
     * @return список строк.
     */
    public List<WebElement> returnTableLines() {
        WebElement mainBody = (new WebDriverWait(driver,10)
                .until(ExpectedConditions.presenceOfElementLocated(By.id("main"))));
        WebElement tableBody = mainBody.findElement(By.tagName("tbody"));
        return tableBody.findElements(By.tagName("tr"));
    }

    /**
     * Нажимает на нужную вкладку в меню слева.
     * @param index номер вкладки, начиная с 0.
     */
    public void clickOnMenu(int index){
        WebElement menu = (wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#box-apps-menu"))));
        List<WebElement> items = menu.findElements(By.tagName("li"));
        WebElement item = items.get(index);
        item.click();
    }

    /**
     * Ищет страны в таблице (вкладка Countries).
     * @return список стран.
     */
    public ArrayList<String> returnCountriesList(List<WebElement> tableLines) {
        ArrayList<String> countries = new ArrayList<>();
        for (WebElement tableLine : tableLines) {
            List<WebElement> elements = tableLine.findElements(By.tagName("td"));
            WebElement countryElement = elements.get(4);
            String country = countryElement.getAttribute("textContent");
            countries.add(country);
        }
        return countries;
    }

    /**
     * Ищет зоны в таблице (страница редактирования Country).
     * @return список зон.
     */
    public ArrayList<String> returnZonesList(){
        WebElement table = driver.findElement(By.cssSelector("#main > form > table"));
        ((Locatable) table).getCoordinates().inViewPort();
        List<WebElement> zonesList = table.findElements(By.className("form-control"));
        ArrayList<String> zones = new ArrayList<>();
        for (WebElement element:zonesList
                ) {
            String zone = element.getAttribute("value");
            if (zone.length() > 2) {
                zones.add(zone);
            }
        }
        return zones;
    }

    /**
     * Ищет зоны в таблице (страница редактирования Geo Zone).
     * @return список зон.
     */
    public ArrayList<String> returnGeoZonesList(){
        WebElement table = driver.findElement(By.cssSelector("#main > form > table"));
        ((Locatable) table).getCoordinates().inViewPort();
        WebElement zonesTable = table.findElement(By.tagName("tbody"));
        List<WebElement> zonesList = zonesTable.findElements(By.tagName("tr"));
        ArrayList<String> zones = new ArrayList<>();
        for (WebElement zoneLine:zonesList) {
            List<WebElement> elements = zoneLine.findElements(By.tagName("td"));
            WebElement zoneField = elements.get(2);
            String zone = zoneField.getAttribute("outerText");
            zones.add(zone);
        }
        return zones;
    }

    /**
     * Определяет корркетность порядка стран или зон.
     * @param list список стран или зон.
     * @return true, если порядок корректный, false - иначе.
     */
    public boolean isOrderCorrect(ArrayList<String> list) {
        boolean flag = true;
        for (int i = 0; i < list.size() - 1; i++){
            if (list.get(i).compareTo(list.get(i + 1)) < 0) {
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Проверяет, есть ли у страны зоны (вкладка Countries).
     * @param locator строка, в которой указана страна.
     * @return true, если зон > 0, false, если зон 0.
     */
    public boolean isThereAZone(WebElement locator){
        WebElement zoneField = locator.findElement(By.cssSelector(".text-center"));
        String zoneCount = zoneField.getAttribute("textContent");
        int count = Integer.parseInt(zoneCount);
        return count > 0;
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