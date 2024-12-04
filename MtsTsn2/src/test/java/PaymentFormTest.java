import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class PaymentFormTest {
    private WebDriver driver;
    private WebDriverWait wait;
    private CharSequence phone;


    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get("https://www.mts.by/");
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
        driver.quit();
        }
    }

    @Test
    void testPaymentFormLabels() {

        Select paySelect = new Select(driver.findElement(By.id("pay")));
        paySelect.selectByValue("Услуги связи");
        fillPaymentForm("297777777", "500,23", "laik@laik.com");


        String[] paymentOptions = {"Домашний интернет", "Рассрочка", "Задолженность"};
        for (String option : paymentOptions) {
            long startTime = System.currentTimeMillis();  // Начало замера времени
            paySelect.selectByValue(option);
            long elapsedTime = System.currentTimeMillis() - startTime;  // Замер времени

            System.out.println("Время переключения на " + option + ": " + elapsedTime + " мс");
            fillPaymentForm("297777777", "500,23", "laik@laik.com");
            try {
                driver.findElement(By.cssSelector(".button.button__default[type='submit']")).click();
            } catch (Exception e) {
                System.err.println("Не удалось нажать на кнопку отправки формы: " + e.getMessage());
            }
        }
    }

    private void fillPaymentForm(String phone, String sum, String email) {
        WebElement phoneField = driver.findElement(By.id("connection-phone"));
        phoneField.clear();
        phoneField.sendKeys(phone);

        WebElement sumField = driver.findElement(By.id("connection-sum"));
        sumField.clear();
        sumField.sendKeys(sum);

        WebElement emailField = driver.findElement(By.id("connection-email"));
        emailField.clear();
        emailField.sendKeys(email);
    }

    @Test
    void testBankCardInputValidation() {
        Select paySelect = new Select(driver.findElement(By.id("pay")));
        paySelect.selectByValue("Услуги связи");
        fillPaymentForm("297777777", "500,23", "laik@laik.com");
        WebElement phoneField = driver.findElement(By.id("connection-phone"));
        driver.findElement(By.cssSelector(".button.button__default[type='submit']")).click();


        WebElement cardNumberField = driver.findElement(By.id("cc-number"));
        WebElement expiryDateField = driver.findElement(By.id("expirydate"));
        WebElement cvvField = driver.findElement(By.id("cvv"));
        WebElement holderNameField = driver.findElement(By.id("holdername"));

        Assertions.assertEquals("Номер карты", cardNumberField.getAttribute("placeholder"), "Метка для номера карты неверна.");
        Assertions.assertEquals("Дата истечения срока действия", expiryDateField.getAttribute("placeholder"), "Метка для даты истечения срока действия неверна.");
        Assertions.assertEquals("CVV/CVC", cvvField.getAttribute("placeholder"), "Метка для CVV/CVC неверна.");
    }
    }