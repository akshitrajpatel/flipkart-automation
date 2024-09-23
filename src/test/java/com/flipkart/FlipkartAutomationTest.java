package com.flipkart;

import org.openqa.selenium.By;
//import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.Scanner;

import org.testng.annotations.Test;

public class FlipkartAutomationTest extends FlipkartAutomation {

    @Test
    public void searchProduct() {
        openFlipkartHomepage(); // Open the homepage
        
        // Search for Samsung F23 Ultra
        driver.findElement(By.name("q")).sendKeys("SAMSUNG Galaxy S24 Ultra");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Test
    public void addProductToCart() throws InterruptedException {
        searchProduct(); // First search for the product
        
        // Wait for search results and click on the first result
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(text(), 'SAMSUNG Galaxy S24 Ultra 5G')]")));
        firstProduct.click();

        // Switch to the new window (product page)
        for (String winHandle : driver.getWindowHandles()) {
            driver.switchTo().window(winHandle);
        }

        // Wait for the Add to Cart button to become clickable and click it
        WebElement addToCartButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#container > div > div._39kFie.N3De93.JxFEK3._48O0EI > div.DOjaWF.YJG4Cf > div.DOjaWF.gdgoEp.col-5-12.MfqIAz > div:nth-child(2) > div > ul > li:nth-child(1) > button")));
     // Scroll into view and click using JavaScript
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartButton);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCartButton);
    }

    @Test
    public void proceedToCheckout() throws InterruptedException {
        addProductToCart();

        // Proceed to cart
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement cartButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[text()='Cart']")));
        cartButton.click();

        // Click on 'Place Order'
        WebElement placeOrderButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class, 'QqFHMw')]")));
        //placeOrderButton.click();
     // Scroll into view if necessary and click using JavaScript Executor
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", placeOrderButton);

        // Click the button using JavaScript to bypass any rendering issues
        js.executeScript("arguments[0].click();", placeOrderButton);
        
     // Wait for login field to appear
        System.out.println("Waiting for the login input field...");
        String currentURL = driver.getCurrentUrl();
        System.out.println("Current URL1: " + driver.getCurrentUrl());
        //driver.get("https://www.flipkart.com/checkout/init?view=FLIPKART&loginFlow=true");
        System.out.println("Current URL2: " + driver.getCurrentUrl());
        //Scanner sc=new Scanner(System.in);
        //sc.nextInt();
        // Login (Enter mobile number or email)
        //WebElement loginField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".r4vIwl BV+Dqf")));
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));//new WebDriverWait(driver, 10);
        try {
            WebElement element = wait1.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".r4vIwl.BV\\+Dqf")));
        	//WebElement element = driver.findElement(By.cssSelector(".r4vIwl.BV\\+Dqf"));
        	element.sendKeys("8318162329");
        } catch( Exception ex) {
            System.out.println("Error: "+ex.toString());
        }
        //loginField.sendKeys("8318162329");
        System.out.println("Mobile entered...");
        
        driver.findElement(By.cssSelector("button.QqFHMw.twnTnD._7Pd1Fp")).click();
    }
}
