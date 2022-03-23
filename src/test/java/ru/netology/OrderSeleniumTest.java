package ru.netology;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OrderSeleniumTest {

    private WebDriver driver;

    @BeforeAll
    public static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "./driver/win/chromedriver.exe");
    }

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void testOrderSuccess() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров-Кошкин Геннадий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74955587421");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText);
    }

    @Test
    void testNameInvalid() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров-Кошкин Геннадий1");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+74955587421");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    void testPhoneInvalid() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров-Кошкин Геннадий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+749555874210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText);
    }

    @Test
    void testNameNullPhoneNull() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    void testPhoneNull() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Петров-Кошкин Геннадий");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.tagName("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }
}