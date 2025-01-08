package com.lhind.demo.utils;


import java.time.Duration;
import org.openqa.selenium.By;

public class TestConfig {

    // Base URL for the application
    public static final String BASE_URL = "https://magento.softwaretestingboard.com/";

    // Timeout duration for WebDriverWait
    public static final Duration TIMEOUT = Duration.ofSeconds(10);



    // RegisterPage Locators
    public static final By pageTitle = By.xpath("//span[@class='base']");
    //public static final By signInLink = By.xpath("//div[@class='panel header']//ul//li[@class='authorization-link']");
    public static final By womenOption = By.xpath("//a[@id='ui-id-4']");
    public static final By womenTops = By.xpath("//a[@id='ui-id-9']");
    public static final By jacketsOption = By.xpath("//a[@id='ui-id-11']");
    public static final By shoppingOptionLabel = By.xpath("//div[@class='block-content filter-content']//strong[text()='Shopping Options']");
    public static final By colorOption = By.xpath("//div[text()='Color']");
    public static final By blueColor = By.xpath("//a[@aria-label='Blue']//div");
    public static final By filterValue = By. xpath("//span[@class='filter-value']");
    public static final By productContainer = By. xpath("//div[@class='products wrapper grid products-grid']");
    public static final By priceDropdown = By.xpath("//div[text()='Price']");
    public static final By secondPriceOption = By.xpath("//a[.//span[@class='price' and text()='$50.00'] and .//span[@class='price' and text()='$59.99']]");
    public static final By productsDisplayedNr = By.xpath("(//p[@id='toolbar-amount'])[1]");
    public static final By productPrice = By.xpath("//span[@class='price']");
    public static final By clearAllFilters = By.xpath("//a[@class='action clear filter-clear']");
    public static final By sortByDropdown = By.xpath("(//div[@class='toolbar-sorter sorter']//select[@id='sorter'])[1]");
    public static final By sortPriceOption = By.xpath("(//div[@class='toolbar-sorter sorter']//select[@id='sorter']//option[@value='price'])[1]");
    public static final By selectedPriceOption = By.xpath("(//div[@class='toolbar-sorter sorter']//select[@id='sorter']//option[@selected='selected'])[1]");
    public static final By productOneHover = By.xpath("(//li[@class='item product product-item'])[1]");
    public static final By productTwoHover = By.xpath("(//li[@class='item product product-item'])[2]");
    public static final By addToCartButton = By.xpath("//button[@id='product-addtocart-button']");
    public static final By sizeXS = By.xpath("//div[@id='option-label-size-143-item-166']");
    public static final By colorSelect = By.xpath("//div[@id='option-label-color-93-item-50']");
    public static final By addToCartMessage = By.xpath("//div[@role='alert']");
    public static final By jacketLink = By.xpath("//a[contains(text(), 'Jackets')]");    
}


