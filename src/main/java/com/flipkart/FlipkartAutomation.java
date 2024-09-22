package com.flipkart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
public class FlipkartAutomation {
    protected WebDriver driver;

    @BeforeMethod
    public void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    
    public void openFlipkartHomepage() {
        driver.get("https://www.flipkart.com/");
        // Close login pop-up if it appears
        try {
            driver.findElement(By.xpath("//button[contains(text(),'✕')]")).click();
        } catch (Exception e) {
            System.out.println("Login popup not displayed.");
        }
    }

    // Additional methods for searching, adding to cart, etc.
}
