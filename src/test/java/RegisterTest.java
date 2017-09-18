import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RegisterTest {
    private static WebDriver driver;

    /**
     * Открывает главную страницу учебного приложения litecart.
     */
    public void start() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://localhost/litecart/en/");
    }

    /**
     * Регистрируется и залогинивается под новыми данными.
     */
    @Test
    public void registerTest(){
        start();
        List<String> data = register();
        String email = data.get(0);
        String password = data.get(1);
        logout();
        signIn(email, password);
    }

    /**
     * Залогинивается под указанными данными.
     * @param email e-mail пользователя.
     * @param password пароль.
     */
    public void signIn(String email, String password) {
        WebElement loginBox = driver.findElement(By.id("box-account-login"));
        WebElement emailField = loginBox.findElement(By.name("email"));
        WebElement passwordField = loginBox.findElement(By.name("password"));
        WebElement loginButton = loginBox.findElement(By.name("login"));
        emailField.sendKeys(email);
        passwordField.sendKeys(password);
        loginButton.click();
    }

    /**
     * Создаёт достаточно уникальный e-mail.
     * @return e-mail.
     */
    public String returnEmail(){
        int randomNumber1 = (int) (Math.random() * 100);
        int randomNumber2 = (int) (Math.random() * 100);
        int randomNumber = randomNumber1 * randomNumber2;
        return "user" + randomNumber + "@test.ru";
    }

    /**
     * Возвращает пароль.
     * @return пароль.
     */
    public String returnPassword() {
        return "12345";
    }

    /**
     * Регистрируется в приложении.
     * @return email и пароль, с которыми зарегистрировался.
     */
    public ArrayList<String> register(){
        WebElement loginBox = driver.findElement(By.id("box-account-login"));
        WebElement link = loginBox.findElement(By.className("text-center"));
        link.click();
        WebElement createAccountBox = driver.findElement(By.cssSelector("#box-create-account"));
        WebElement form = createAccountBox.findElement(By.name("customer_form"));
        List<WebElement> rows = form.findElements(By.className("row"));
        WebElement row2 = rows.get(1);
        WebElement firstNameField = row2.findElement(By.name("firstname"));
        firstNameField.sendKeys("John");
        WebElement lastNameField = row2.findElement(By.name("lastname"));
        lastNameField.sendKeys("Smith");
        WebElement row5 = rows.get(4);
        WebElement countryField = row5.findElement(By.name("country_code"));
        Select selectCountry = new Select(countryField);
        selectCountry.selectByValue("US");
        WebElement zoneField = row5.findElement(By.name("zone_code"));
        Select selectZone = new Select(zoneField);
        selectZone.selectByIndex(2);
        WebElement row6 = rows.get(5);
        WebElement emailField = row6.findElement(By.name("email"));
        String email = returnEmail();
        emailField.sendKeys(email);
        WebElement row7 = rows.get(6);
        WebElement desiredPasswordField = row7.findElement(By.name("password"));
        WebElement confirmedPasswordField = row7.findElement(By.name("confirmed_password"));
        String password = returnPassword();
        desiredPasswordField.sendKeys(password);
        confirmedPasswordField.sendKeys(password);
        WebElement createAccountButton = form.findElement(By.name("create_account"));
        createAccountButton.click();
        // Формирует данные (email и пароль) для последующего входа в сервис.
        ArrayList<String> signInData = new ArrayList<>();
        signInData.add(email);
        signInData.add(password);
        return signInData;
    }

    /**
     * Разлогинивается из-под учётной записи.
     */
    public void logout(){
        WebElement accountBox = driver.findElement(By.id("box-account"));
        WebElement link = accountBox.findElement(By.linkText("Logout"));
        link.click();
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
