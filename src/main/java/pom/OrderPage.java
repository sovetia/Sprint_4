package pom;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.xpath;

public class OrderPage {

    private final WebDriver driver;
    private final static String page = "https://qa-scooter.praktikum-services.ru/";
    private final By nameText = xpath("//div[@class='Order_Form__17u6u']/div[1]/input"); //Имя.
    private final By secondNameText = xpath("//div[@class='Order_Form__17u6u']/div[2]/input"); //Фамилия
    private final By inputAddress = xpath("//div[@class='Order_Form__17u6u']/div[3]/input"); //Адрес: Куда привезти
    private final By userPhone = xpath("//div[@class='Order_Form__17u6u']/div[5]/input"); //Телефон: на него позвонит курьер
    private final By metroStation = xpath("//div[@class='Order_Form__17u6u']/div[4]"); //Станция метро(Раскрыть)
    private final By metroStationChoice = xpath("//div[@class='select-search__select']/ul/li/button/div[2]"); //Выбрать станцию Бульвар Рокосовского
    private final By nextPageButton = cssSelector("div.Order_NextButton__1_rCA > button.Button_Middle__1CSJM"); // "Далее"
    private final By calendarDate = xpath("//div[@class='Order_Form__17u6u']/div/div/div/input");  //Когда привезти самокат
    private final By rentTime = xpath("//div[@class='Dropdown-control']"); //Срок аренды (открыть список)
    private final By rentalDurationChoice = xpath("//div[@class='Dropdown-menu']/div[2]"); //Выбор срока аренды (из выпадающего списка)
    private final By scooterColour = xpath("//div[@class='Order_Checkboxes__3lWSI']/label[1]"); //Цвет самоката
    private final By userComment = xpath("//div[@class='Input_InputContainer__3NykH']/input[@class='Input_Input__1iN_Z Input_Responsible__1jDKN']");  //Комментарий для курьера
    private final By orderButton = xpath("//div[@class='Order_Buttons__1xGrp']/button[2]"); //"Заказать"
    private final By confirmButton = xpath("//div[@class='Order_Modal__YZ-d3']/div[2]/button[2]"); //"Да"
    private final By orderCreated = xpath("//*[contains(text(), 'Заказ оформлен')]"); //Заказ оформлен
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy"); // Форматирование даты
    String date = LocalDate.now().plusMonths(1).format(formatter); // Добавить месяц к текущей дате

    public OrderPage(WebDriver driver) {
        this.driver = driver;
    }

    public void startPage() {
        driver.get(page);
    }
    public void findCheckAndClickOrderButton(By buttonAddress) {
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.visibilityOfElementLocated(buttonAddress));
        Object elementOrderButton = driver.findElement(buttonAddress);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", elementOrderButton);
        new WebDriverWait(driver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(buttonAddress));
        driver.findElement(buttonAddress).click();
    }
    public void userName(String name){
        driver.findElement(nameText).sendKeys(name);
    }
    public void userSurname(String surname) {
        driver.findElement(secondNameText).sendKeys(surname);
    }
    public void userAddress(String address) {
        driver.findElement(inputAddress).sendKeys(address);
    }
    public void userPhone(String phone) {
        driver.findElement(userPhone).sendKeys(phone);
    }
    public void metroStation() {
        driver.findElement(metroStation).click();
    }
    public void metroStationChoice() {
        driver.findElement(metroStationChoice).click();
    }
    public void nextPageButton() {
        driver.findElement(nextPageButton).click();
    }
    public void calendarDate() {
        driver.findElement(calendarDate).click();
        driver.findElement(calendarDate).sendKeys(date);
        driver.findElement(calendarDate).sendKeys(Keys.ENTER);
    }
    public void rentalDuration() {
        driver.findElement(rentTime).click();
        driver.findElement(rentalDurationChoice).click();
    }
    public void scooterColour() {
        driver.findElement(scooterColour).click();
    }
    public void userComment(String comment) {
        driver.findElement(userComment).sendKeys(comment);
    }
    public void orderButton() {
        driver.findElement(orderButton).click();
    }
    public void confirmButton() {
        driver.findElement(confirmButton).click();
    }


    public boolean CreatedOrder() {
        try {
            return driver.findElement(orderCreated).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
