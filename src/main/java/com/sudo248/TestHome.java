package com.sudo248;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.WheelInput;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestHome {
    ChromeDriver chrome;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        chrome = new ChromeDriver();
        chrome.manage().window().maximize();
        chrome.get("https://www.dimteam.vn/?fbclid=IwAR3-4pLzq1Cc-_B6xK2gNrp_mgvct-PHTB_A7Vy_c4UF17HfNsNh_FvYiyA");
    }

    @Test
    public void viewDetailProduct() {
        chrome.findElement(By.xpath("//*[@id=\"siteNav\"]/li[1]/a")).click();
        Actions actions = new Actions(chrome);
        waitForSecond(2);
        WheelInput.ScrollOrigin scrollOrigin = WheelInput.ScrollOrigin.fromViewport(10, 10);
        actions
                .scrollFromOrigin(scrollOrigin, 0, 1000)
                .perform();
        WebElement firstElement = chrome.findElement(By.xpath("//*[@id=\"Collection\"]/div/div/div[3]/div[2]/div[1]/div/div[2]/a"));
        String nameProductInHome = firstElement.getText();
        firstElement.click();
        waitForSecond(2);
        String nameProduct = chrome.findElement(By.className("product-single__title")).getText();

        Assert.assertEquals(nameProductInHome.trim(), nameProduct.trim());
    }

    @Test
    public void updateAmountItem() {
        viewDetailProduct();

        WebElement currentQuantity = chrome.findElement(By.id("quantity"));
        int initQuantityValue = Integer.parseInt(currentQuantity.getAttribute("value"));

        WebElement btnAdd = chrome.findElement(By.xpath("//*[@id=\"product_form_7031491952710\"]/div[2]/div[1]/div/a[2]"));
        btnAdd.click();
        waitForSecond(2);
        int currentQuantityValue = Integer.parseInt(currentQuantity.getAttribute("value"));

        Assert.assertEquals(currentQuantityValue, initQuantityValue + 1);
        initQuantityValue = currentQuantityValue;

        WebElement btnMinus = chrome.findElement(By.xpath("//*[@id=\"product_form_7031491952710\"]/div[2]/div[1]/div/a[1]"));
        btnMinus.click();
        waitForSecond(2);
        currentQuantityValue = Integer.parseInt(currentQuantity.getAttribute("value"));

        Assert.assertEquals(currentQuantityValue, initQuantityValue - 1);

    }

    @Test
    public void addToCart() {
        viewDetailProduct();
        String nameProduct = chrome.findElement(By.xpath("//*[@id=\"ProductSection-product-template\"]/div[1]/div[2]/h1")).getText();
        chrome.findElement(By.id("AddToCartText-product-template")).click();
        waitForSecond(3);
        chrome.findElement(By.id("inlinecheckout-cart")).click();
        waitForSecond(2);

        String nameProductInCart = chrome.findElement(By.xpath("//*[@id=\"shopify-section-cart-template\"]/div[2]/form/table/tbody/tr/td[3]/div[1]/a")).getText();

        Assert.assertEquals(nameProduct.toLowerCase(), nameProductInCart.toLowerCase());

    }

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
