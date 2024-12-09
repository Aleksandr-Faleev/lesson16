package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class PageObject {
    protected WebDriver driver;

    private By phoneNumberInput = By.id("connection-phone");
    private By sumInput = By.id("connection-sum");
    private By emailInput = By.id("connection-email");
    private By submitButton = By.cssSelector(".button.button__default[type='submit']");
    private By paySelect = By.id("pay");

    public PageObject(WebDriver driver) {
        this.driver = driver;
    }

    public void fillPaymentForm(String phone, String sum, String email) {
        WebElement phoneField = driver.findElement(phoneNumberInput);
        phoneField.clear();
        phoneField.sendKeys(phone);

        WebElement sumField = driver.findElement(sumInput);
        sumField.clear();
        sumField.sendKeys(sum);

        WebElement emailField = driver.findElement(emailInput);
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void selectPayOption(String option) {
        Select paySelect = new Select(driver.findElement(this.paySelect));
        paySelect.selectByValue(option);
    }

    public void submitForm() {
        driver.findElement(submitButton).click();
    }
}