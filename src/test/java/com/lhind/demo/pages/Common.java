package com.lhind.demo.pages;

import org.openqa.selenium.WebDriver;
import com.lhind.demo.utils.TestConfig;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;


import java.io.File;
import java.io.IOException;

public class Common {
    protected WebDriver driver;
    protected WebDriverWait wait;

    // Setup method
    public void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, TestConfig.TIMEOUT);
        driver.get(TestConfig.BASE_URL);
    }

    // Teardown method
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    // Capture screenshot
    protected void captureScreenshot(String fileName) {
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(screenshot, new File("screenshots/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    
}
