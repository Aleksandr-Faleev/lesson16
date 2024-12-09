package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import pageObjects.PageObject;

import java.time.Duration;

public class PaymentFormTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private PageObject pageObject;

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.mts.by/");
        pageObject = new PageObject(driver); // Инициализация PageObject
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {

        }
    }

    @Test
    void testPaymentFormLabels() {
        pageObject.selectPayOption("Услуги связи");
        pageObject.fillPaymentForm("297777777", "500,23", "laik@laik.com");

        String[] paymentOptions = {"Домашний интернет", "Рассрочка", "Задолженность"};
        for (String option : paymentOptions) {
            long startTime = System.currentTimeMillis();  // Начало замера времени
            pageObject.selectPayOption(option);
            long elapsedTime = System.currentTimeMillis() - startTime;  // Замер времени

            System.out.println("Время переключения на " + option + ": " + elapsedTime + " мс");
            pageObject.fillPaymentForm("297777777", "500,23", "laik@laik.com");
            try {
                pageObject.submitForm();
            } catch (Exception e) {
                System.err.println("Не удалось нажать на кнопку отправки формы: " + e.getMessage());
            }
        }
    }

    @Test
    void testBankCardInputValidation() {
        pageObject.selectPayOption("Услуги связи");
        pageObject.fillPaymentForm("297777777", "500,23", "laik@laik.com");
    }
}
