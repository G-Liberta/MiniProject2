package com.lhind.demo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import com.lhind.demo.utils.TestConfig;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LumaPage {

    private WebDriver driver;
    private WebDriverWait wait;



    public LumaPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Verify we have navigated to Home Page
    public String isHomePageDisplayed() {
        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(TestConfig.pageTitle));
        return pageTitle.getText();
    }

    // Method to hover over Women dropdown
    public void womenDropdown() {
        Actions actions = new Actions(driver);
        WebElement womenElement = driver.findElement(TestConfig.womenOption);
        actions.moveToElement(womenElement).perform();
    }

    // Method to hover over Tops option and click on Jackets
    public void hoverTopsAndClickJackets() {
        Actions actions = new Actions(driver);
        WebElement womenTopsElement = driver.findElement(TestConfig.womenTops);
        actions.moveToElement(womenTopsElement).perform();
        wait.until(ExpectedConditions.elementToBeClickable(TestConfig.jacketsOption)).click();
    }

    // Verify we have navigated to Jackets Page
    public boolean isJacketPageDisplayed() {
        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(TestConfig.pageTitle));
        return pageTitle.getText().contains("Jackets");
    }

    // Verify Shopping Option Panel text
    public boolean isShoppingOptionDisplayed() {
        WebElement pageTitle = wait.until(ExpectedConditions.visibilityOfElementLocated(TestConfig.shoppingOptionLabel));
        return pageTitle.getText().contains("Shopping Options");
    }

    // Scroll down the page
    public void scrollDown() {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("window.scrollBy(0,250)");
    }

    // Methods to hover over Colors option, click on blue color and verify Blue filter
    public void hoverAndClickColorOption() {
        Actions actions = new Actions(driver);
        WebElement colorElement = driver.findElement(TestConfig.colorOption);
        actions.moveToElement(colorElement).perform();
        wait.until(ExpectedConditions.elementToBeClickable(colorElement)).click();
    }

    public void hoverAndSelectBlueColor() {
        Actions actions = new Actions(driver);
        WebElement bluElement = driver.findElement(TestConfig.blueColor);
        actions.moveToElement(bluElement).perform();
        bluElement.click();
     
    }


    // Method to verify that all displayed products have the blue color selected
    public Map<String, Boolean> verifyBlueColorOnAllFilteredProducts(String string) {
        Map<String, Boolean> productBlueStatus = new HashMap<>();
        List<WebElement> productList = driver.findElements(TestConfig.productContainer);
        // Loop through each product and verify the blue color option is selected
        for (WebElement product : productList) {
            // Locate the color option within the product (adjust the CSS selector based on the page structure)
            WebElement blueColorOption = product.findElement(By.cssSelector(".swatch-option[aria-label='Blue']"));

            // Verify if the blue color is selected by checking the 'selected' class or 'aria-checked' attribute
            boolean isBlueSelected = blueColorOption.isDisplayed() && 
                                    (blueColorOption.getDomAttribute("aria-checked").equals("true") || 
                                    blueColorOption.getDomAttribute("class").contains("selected"));
            
            // Get the product name or other identifying information (e.g., text or title)
            String productName = product.getText();  // Or any other locator for identifying the product

            // Store the product name and blue selection status in the map
            productBlueStatus.put(productName, isBlueSelected);
        }

        return productBlueStatus;
    }


    // Methods to hover over Price filter, select second option and verify that 2 products are displayed
    public void hoverOverPriceFilter() {
        Actions actions = new Actions(driver);
        WebElement priceDropdownElement = driver.findElement(TestConfig.priceDropdown);
        actions.moveToElement(priceDropdownElement).perform();
        wait.until(ExpectedConditions.elementToBeClickable(priceDropdownElement)).click();
    }

    public void selectSecondPriceOption() {
        Actions actions = new Actions(driver);
        WebElement secondPriceOptionElement = driver.findElement(TestConfig.secondPriceOption);
        actions.moveToElement(secondPriceOptionElement).perform();
        secondPriceOptionElement.click();
     
    }

    public String nrOfProductsDisplayed() {
        WebElement nrOfProducts = wait.until(ExpectedConditions.visibilityOfElementLocated(TestConfig.productsDisplayedNr));
        return nrOfProducts.getText(); 
    }




    // Method to get the price for each product in the container
    public List<String> getAllProductPrices() {
        List<WebElement> productList = driver.findElements(TestConfig.productContainer); 
        List<String> productPrices = new ArrayList<>();    // Initialize a list to store the prices of each product
        
        for (int i = 0; i < productList.size(); i++) {
            WebElement product = productList.get(i);
    
            try {
                WebElement priceElement = product.findElement(By.xpath("(//span[@class='price'])[" + (i + 1) + "]"));
                String productPrice = priceElement.getText(); // Get the price text
                productPrices.add(productPrice); // Add the product price to the list
                System.out.println("Product " + (i + 1) + " price: " + productPrice);
            } catch (Exception e) {
                System.out.println("Error finding price for product " + (i + 1) + ": " + e.getMessage());
            }
        }
        return productPrices; // Return the list of product prices
    }

    // Method to clear filters
    public void clearFilters() {
        Actions actions = new Actions(driver);
        WebElement clearAllFiltersElement = driver.findElement(TestConfig.clearAllFilters);
        actions.moveToElement(clearAllFiltersElement).perform();
        clearAllFiltersElement.click();
     
    }


    // Method to extract the total number of items displayed
    public int extractItemCount(String itemsText) {
        try {
            String countString = itemsText.split(" ")[0]; // Get the first part before the space
            return Integer.parseInt(countString); // Convert to integer
        } catch (Exception e) {
            System.out.println("Error extracting item count from text: " + itemsText);
            return 0; 
        }
    }



    // Method to click the Sort By dropdown and select the "Price" option
    public void sortByPriceFilter() {
        WebElement sortByDropdownElement = wait.until(ExpectedConditions.elementToBeClickable(TestConfig.sortByDropdown));
        sortByDropdownElement.click(); // Click on the dropdown
        
        WebElement priceOptionFilterElement = wait.until(ExpectedConditions.elementToBeClickable(TestConfig.sortPriceOption));
        priceOptionFilterElement.click(); // Click on the "Price" option
    }


    // Method to verify that the "Price" option is selected  y checking the "selected" attribute
    public boolean isPriceOptionSelected() {
        driver.navigate().refresh();  // Reload the page
        WebElement priceOption = wait.until(ExpectedConditions.visibilityOfElementLocated(TestConfig.sortPriceOption));
        String selectedAttribute = priceOption.getDomAttribute("selected"); // Check the 'selected' attribute
        System.out.println("Selected attribute value: " + selectedAttribute); // Debug log

        boolean isSelected = selectedAttribute != null && selectedAttribute.equals("true");  // Verify if the attribute exists and is set to "selected"
        if (isSelected) {
            System.out.println("'Price' option is correctly selected with the 'selected' attribute.");
        } else {
            System.out.println("'Price' option does not have the 'selected' attribute.");
        }
        return isSelected;
    }



    // Method to hover over a product and add it to the cart
    public void addProductToCart(By productLocator, By sizeLocator, By colorLocator) {

    WebElement productElement = wait.until(ExpectedConditions.visibilityOfElementLocated(productLocator));
    Actions act = new Actions(driver);
    act.moveToElement(productElement).click().perform();

    WebElement sizeElement = wait.until(ExpectedConditions.visibilityOfElementLocated(sizeLocator));
    sizeElement.click();

    WebElement colorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(colorLocator));
    colorElement.click();

    WebElement addToCartBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(TestConfig.addToCartButton));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addToCartBtn);
    addToCartBtn.click();

}

    // Method to retrieve the "added to cart" message
    public String getAddedToCartMessage() {
        WebElement messageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(TestConfig.addToCartMessage));
        return messageElement.getText();
    }



    // Method to navigate to the Jackets section
    public void clickJacketLink() {
        WebElement jacketLinkElement = wait.until(ExpectedConditions.elementToBeClickable(TestConfig.jacketLink));
        jacketLinkElement.click();
    }

}

