package pom;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage {
    private final WebDriver driver;

    // url страницы
    public static final String URL = "https://qa-scooter.praktikum-services.ru/";

    //Кнопка принять куки
    private final By cookieButton = By.id("rcc-confirm-button");

    //Кнопка Заказать вверху страницы
    private final By upButtonOrderButton = By.xpath(".//div[@class='Header_Nav__AGCXC']/button[@class='Button_Button__ra12g']");

    //Кнопка Заказать внизу страницы
    private final By downButtonOrderButton = By.xpath(".//div[@class='Home_FinishButton__1_cWm']/button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    public MainPage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickCookieButton() {
        driver.findElement(cookieButton).click();
    }

    public void waitForLoad(int seconds) {
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(By.className("Home_FAQ__3uVm4")));
    }

    public void clickUpOrderButton() {
        driver.findElement(upButtonOrderButton).click();
    }

    public void clickDownButtonOrderButton() {
        driver.findElement(downButtonOrderButton).click();
    }

    public void clickQuestion(int questionNumber) {
        By questionLocator = By.id("accordion__heading-" + questionNumber);
        WebElement questionElement = driver.findElement(questionLocator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", questionElement);
        Actions actions = new Actions(driver);
        actions.moveToElement(questionElement)
                .click()
                .build()
                .perform();
    }

    public String getAnswer(int questionNumber) {
        By answerLocator = By.xpath(".//div[@id='accordion__panel-" + questionNumber + "']/p");
        return driver.findElement(answerLocator).getText();
    }

    public void waitForLoadQuestion(int seconds, int questionNumber) {
        By questionLocator = By.id("accordion__heading-" + questionNumber);
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(questionLocator));
    }

    public void waitForLoadAnswer(int seconds, int answerNumber) {
        By answerLocator = By.xpath(".//div[@id='accordion__panel-" + answerNumber + "']/p");
        new WebDriverWait(driver, Duration.ofSeconds(seconds))
                .until(ExpectedConditions.visibilityOfElementLocated(answerLocator));
    }

    public void clickOrderButton(String orderButton) {
        if ("up".equals(orderButton)) {
            clickUpOrderButton();
        } else if ("down".equals(orderButton)) {
            clickDownButtonOrderButton();
        }
    }
}