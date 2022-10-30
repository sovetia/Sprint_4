import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import pom.MainPage;
import pom.OrderPage;

import java.time.Duration;

@RunWith(Parameterized.class)
public class TestOrderPage {
    private final String orderButton;
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metro;
    private final String phone;
    private final String whereToBringScooter;
    private final String rentalPeriod;
    private final String scooterColor;
    private final String comments;
    private WebDriver driver;
    private final int defaultTimeOut = 60;
    private final String successfulOrderText = "Заказ оформлен";

    public TestOrderPage(String orderButton, String firstName, String lastName, String address, String metro, String phone, String whereToBringScooter, String rentalPeriod, String scooterColor, String comments) {
        this.orderButton = orderButton;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metro = metro;
        this.phone = phone;
        this.whereToBringScooter = whereToBringScooter;
        this.rentalPeriod = rentalPeriod;
        this.scooterColor = scooterColor;
        this.comments = comments;
    }

    @Before
    public void startUp() {
        String br = System.getenv("browser");
        if ("chrome".equals(br)) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions);
        } else if ("ff".equals(br)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setHeadless(true);
            firefoxOptions.setAcceptInsecureCerts(false);
            driver = new FirefoxDriver(firefoxOptions);
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions);
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(defaultTimeOut));
        driver.get("https://qa-scooter.praktikum-services.ru/");
    }

    @After
    public void teardown() {
        driver.quit();
    }

    @Parameterized.Parameters
    public static Object[][] getPersonData() {
        return new Object[][]{
                {"up", "Иван", "Крокодилов", "Ул.Мира 55", "Черкизовская", "89652315465", "29.10.2022", "сутки", "black", "Спасибо!"},
                {"down", "Людмила", "Чеснокова", "Ул.Ленина 14", "Теплый стан", "89052664415", "01.11.2022", "двое суток", "grey", "Спасибо!"},
                {"up", "Алексей", "Воронин", "Ул.Вишневая 98", "Сокольники", "89982323313", "03.11.2022", "семеро суток", null, "Вход со двора"}
        };
    }

    @Test
    public void checkOrder() {
        MainPage mainPage = new MainPage(driver);
        OrderPage orderPage = new OrderPage(driver);

        mainPage.clickCookieButton();
        mainPage.clickOrderButton(orderButton);

        orderPage.fillPersonForm(firstName, lastName, address, metro, phone);
        orderPage.clickNextButton();
        orderPage.fillRentForm(whereToBringScooter, rentalPeriod, scooterColor, comments);
        orderPage.clickOrderButton();
        orderPage.clickWantToPlaceAnOrderYesButton();

        String actualText = orderPage.getOrderModalHeaderText();
        Assert.assertTrue(actualText.contains(successfulOrderText));
    }
}
