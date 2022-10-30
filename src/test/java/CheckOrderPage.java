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
public class CheckOrderPage {
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

    public CheckOrderPage(String orderButton, String firstName, String lastName, String address, String metro, String phone, String whereToBringScooter, String rentalPeriod, String scooterColor, String comments) {
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

    @Parameterized.Parameters
    public static Object[][] getPersonData() {
        return new Object[][]{
                {"up", "Кошка", "Сирота", "Котманду", "Котельники", "89000000000", "31.10.2022", "сутки", "black", "Самокаты котам!"},
                {"down", "Полиграф", "Шариков", "Обухов переулок, 1, квартира 12", "Кропоткинская", "+79000000000", "01.01.2023", "двое суток", "grey", "Самокаты псам!"}
        };
    }

    @Before
    public void startUp() {
        String br = System.getenv("browser");
        // проверка в браузере Mozilla Firefox
        if ("ff".equals(br)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setHeadless(true);
            firefoxOptions.setAcceptInsecureCerts(false);
            driver = new FirefoxDriver(firefoxOptions);
        }
        // проверка в браузере Google Chrome
        else {
            ChromeOptions chromeOptions = new ChromeOptions();
            //chromeOptions.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions);
        }

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(defaultTimeOut));
        driver.get(MainPage.URL);
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

    @After
    public void driverQuit() {
        driver.quit();
    }
}
