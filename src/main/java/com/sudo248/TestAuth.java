package com.sudo248;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestAuth {
    ChromeDriver chrome;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chrome = new ChromeDriver();
        chrome.manage().window().maximize();
        chrome.get("https://www.dimteam.vn/?fbclid=IwAR3-4pLzq1Cc-_B6xK2gNrp_mgvct-PHTB_A7Vy_c4UF17HfNsNh_FvYiyA");
        chrome.findElement(By.xpath("//*[@id=\"headerWrapper\"]/header/div[3]/a[2]")).click();
    }


    @Test
    public void signUpWithEmailValid() {
        actionSignUp("Duong", "Le", "Sudo248.dev@gmail.com", "12345678");

        Assert.assertTrue(chrome.findElement(By.xpath("//*[@id=\"create_customer\"]/div")).isDisplayed());
    }

    @Test
    public void signUpCorrect() {
        actionSignUp("Duong", "Le", "hongduong080501@gmail.com", "12345678");

        Assert.assertTrue(chrome.findElement(By.xpath("//*[@id=\"headerWrapper\"]/header/h1/a")).isDisplayed());
    }

//    @Test
//    public void signInWithInvalidEmailOrPassword() {
//        actionSignIn("Sudo248.dev@gmail.com", "1234567891011");
//        waitForSecond(1);
//        Assert.assertTrue(chrome.findElement(By.xpath("//*[@id=\"customer_login\"]/div[1]")).isDisplayed());
//    }
//
//    @Test
//    public void signInCorrect() {
//        actionSignIn("Sudo248.dev@gmail.com", "12345678");
//        waitForSecond(2);
//        Assert.assertTrue(chrome.findElement(By.xpath("//*[@id=\"PageContainer\"]/div[2]/h1")).isDisplayed());
//    }



    public void actionSignIn(String email, String password) {
        chrome.findElement(By.xpath("//*[@id=\"settingsBox\"]/div/p[1]/a")).click();
        waitForSecond(1);
        chrome.findElement(By.id("CustomerEmail")).sendKeys(email);
        chrome.findElement(By.id("CustomerPassword")).sendKeys(password);
        waitForSecond(2);
        chrome.findElement(By.xpath("//*[@id=\"customer_login\"]/div/input")).click();
        waitForSecond(2);
        try {
            WebElement btnSubmit = chrome.findElement(By.xpath("//*[@id=\"PageContainer\"]/div/form/input[2]"));
            if (btnSubmit.isDisplayed()) {
                (new WebDriverWait(chrome, Duration.ofMinutes(1))).until(ExpectedConditions.elementToBeSelected(btnSubmit));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actionSignUp(String firstName, String lastName, String email, String password) {
        chrome.findElement(By.className("register")).click();
        waitForSecond(1);
        chrome.findElement(By.id("FirstName")).sendKeys(firstName);
        chrome.findElement(By.id("LastName")).sendKeys(lastName);
        chrome.findElement(By.id("Email")).sendKeys(email);
        chrome.findElement(By.id("CreatePassword")).sendKeys(password);
        waitForSecond(2);
        chrome.findElement(By.xpath("//*[@id=\"create_customer\"]/p[2]/input")).click();
        waitForSecond(2);
    }

//    @Test
//    public void actionResetPasswordWhenSignUp(String email) {
//        chrome.findElement(By.xpath("//*[@id=\"create_customer\"]/div/ul/li/a")).click();
//        waitForSecond(1);
//        chrome.findElement(By.id("RecoverEmail")).sendKeys(email);
//        waitForSecond(1);
//        chrome.findElement(By.xpath("//*[@id=\"RecoverPasswordForm\"]/div/form/div/p/input")).click();
//        waitForSecond(2);
//    }

    @AfterMethod
    public void clear() {
        chrome.quit();
    }

    public void wait(Duration duration) {
        try {
            Thread.sleep(duration.toMillis());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void waitForSecond(int seconds) {
        wait(Duration.ofSeconds(seconds));
    }
}
