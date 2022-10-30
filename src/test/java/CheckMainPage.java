import pom.MainPage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

@RunWith(Parameterized.class)
public class CheckMainPage {
    private WebDriver driver;
    private final int defaultTimeOut = 30;
    private final int questionNumber;
    private final String expectedText;

    public CheckMainPage(int questionNumber, String expectedText) {
        this.questionNumber = questionNumber;
        this.expectedText = expectedText;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {0, "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {1, "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {2, "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {3, "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {4, "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {5, "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {6, "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {7, "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
        };
    }

    @Before
    public void startUp() {
        String br = System.getenv("browser");
        if ("ff".equals(br)) {
            FirefoxOptions firefoxOptions = new FirefoxOptions();
            firefoxOptions.setHeadless(true);
            firefoxOptions.setAcceptInsecureCerts(false);
            driver = new FirefoxDriver(firefoxOptions);
        } else {
            ChromeOptions chromeOptions = new ChromeOptions();
            //chromeOptions.addArguments("--no-sandbox", "--headless", "--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions);
        }
        driver.get(MainPage.URL);
    }

    @Test
    public void importantQuestionsTest() {
        MainPage mainPage = new MainPage(driver);
        mainPage.waitForLoad(defaultTimeOut);
        mainPage.clickCookieButton();
        mainPage.waitForLoadQuestion(defaultTimeOut, questionNumber);
        mainPage.clickQuestion(questionNumber);
        mainPage.waitForLoadAnswer(defaultTimeOut, questionNumber);
        String actualText = mainPage.getAnswer(questionNumber);
        Assert.assertEquals(expectedText, actualText);
    }

    @After
    public void driverQuit() {
        driver.quit();
    }
}