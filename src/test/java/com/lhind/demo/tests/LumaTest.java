package com.lhind.demo.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.lhind.demo.pages.Common;
import com.lhind.demo.pages.ExtentManager;
import com.lhind.demo.pages.LumaPage;
import com.lhind.demo.utils.TestConfig;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LumaTest extends Common {

    private LumaPage lumaPage;
    private static ExtentReports extent;
    public static ExtentTest test;

    @BeforeMethod
    public void setUpTest() {
        setUp();
        lumaPage = new LumaPage(driver);
        extent = ExtentManager.createInstance("reports/LumaTestReport.html");
    }

    @Test
    public void testHomePage() {
        test = extent.createTest("Home Page Test");

        // Step 1: Verify Home Page is opened
        test.info("Verifying Home Page is displayed.");
        String pageTitleText = lumaPage.isHomePageDisplayed();
        test.info("Page title text: " + pageTitleText);
        Assert.assertTrue(pageTitleText.contains("Home Page"), "Home Page is not displayed as expected!");


        // Step 2: Hover over Women Menu
        test.info("Hovering over Women Menu.");
        lumaPage.womenDropdown();


        // Step 3: Hover over Tops Option and click on Jackets
        test.info("Hovering over Tops and clicking Jackets.");
        lumaPage.hoverTopsAndClickJackets();


        // Step 4: Verify Jackets Page is opened
        test.info("Verifying Jackets Page is displayed.");
        String jacketsPageTitle = lumaPage.isHomePageDisplayed();
        test.info("Jackets Page Title: " + jacketsPageTitle);
        Assert.assertTrue(jacketsPageTitle.contains("Jackets"), "Jackets Page is not displayed as expected!");


        // Step 5: Verify Shopping Option label
        test.info("Verifying Shopping Options label is displayed.");
        String shoppingOptionLabelText = lumaPage.isShoppingOptionDisplayed() ? "Shopping Options" : "Label not found";
        test.info("Shopping Options Label: " + shoppingOptionLabelText);
        Assert.assertTrue(lumaPage.isShoppingOptionDisplayed(), "Shopping Options is not displayed as expected!");


        // Scroll down a bit to make sure the fields are visible
        test.info("Scrolled down");
        lumaPage.scrollDown();


        // Step 6: Hover over Colors Option and select blue
        test.info("Selecting Blue color.");
        lumaPage.hoverAndClickColorOption();
        lumaPage.hoverAndSelectBlueColor();
        // Validate that the "Blue" text is displayed in the filter value span after selecting the color
        WebElement filterValueElement = driver.findElement(TestConfig.filterValue);
        String filterValueText = filterValueElement.getText();
        test.info("Filter Value Text: " + filterValueText);
        Assert.assertTrue(filterValueText.contains("Blue"), "The filter value does not display 'Blue' as expected!");


        // Step 7: Validate that the selected color is Blue for each product
        test.info("Verifying all displayed products have Blue color selected");
        // Get the map of product names and their blue selection status
        Map<String, Boolean> productBlueStatus = lumaPage.verifyBlueColorOnAllFilteredProducts("Blue");

        // Loop through the map to log the product details in the report
        for (Map.Entry<String, Boolean> entry : productBlueStatus.entrySet()) {
            String productName = entry.getKey();
            boolean isBlueSelected = entry.getValue();

            // Log the product name and whether blue is selected
            if (isBlueSelected) {
                test.info("Blue color is selected for products: " + productName);
            } else {
                test.info("Blue color is NOT selected for product: " + productName);
            }
        }



        // Step 8: Select second option of Price filter and verify nr (2) of elements displayed
        test.info("Hovering over the price filter dropdown. Selecting the second price option");
        lumaPage.hoverOverPriceFilter();
        lumaPage.selectSecondPriceOption();
        String nrOfProducts = lumaPage.nrOfProductsDisplayed();
        test.info("Number of products displayed: " + nrOfProducts);  // Log the retrieved number of products



        // Step 9: Verify price is within range $50.00 and $59.99 for each product
        test.info("Retrieve the list of product prices and validate them");
        List<String> productPrices = lumaPage.getAllProductPrices();

        for (int i = 0; i < productPrices.size(); i++) {
            String priceText = productPrices.get(i);
            test.info("Product " + (i + 1) + " Price: " + priceText); // Log the product price in the report
            double price = Double.parseDouble(priceText.replace("$", "").trim());  // Remove the "$" symbol and convert to a number for comparison
            if (price >= 50.00 && price <= 59.99) {
                test.pass("Product " + (i + 1) + " price is within the expected range: " + priceText);
            } else {
                test.fail("Product " + (i + 1) + " price is outside the expected range: " + priceText);
            }
        }



        // Step 10: Clear all filters and check the items number displayed is increased
        test.info("Clear filters and check items nr displayed is increased");
        String itemsBeforeClear = lumaPage.nrOfProductsDisplayed();
        int countBeforeClear = lumaPage.extractItemCount(itemsBeforeClear);
        test.info("Nr of products before "  + countBeforeClear); 
        lumaPage.clearFilters();
        String itemsAfterClear = lumaPage.nrOfProductsDisplayed();
        int countAfterClear = lumaPage.extractItemCount(itemsAfterClear);
        test.info("Nr of products after "  + countAfterClear);

        Assert.assertTrue(countAfterClear > countBeforeClear );



        // Step 11: Sort By Price filter and verify products are sorted based on their price
        test.info("Sort By Price filter and verify products are sorted based on their price");
        lumaPage.sortByPriceFilter();
        boolean isPriceSelected = lumaPage.isPriceOptionSelected();
        if (isPriceSelected) {
            test.info("The 'Price' option has the 'selected' attribute.");
        } else {
            test.info("The 'Price' option has not the 'selected' attribute.");
        }
        Assert.assertTrue(isPriceSelected);



        // Step 12: Add to cart product and verify message
        test.info("Add the first product to the shopping cart");
        lumaPage.addProductToCart(TestConfig.productOneHover, TestConfig.sizeXS, TestConfig.colorSelect);

        String firstProductMessage = lumaPage.getAddedToCartMessage();
        test.info("First product added to cart message: " + firstProductMessage);
        Assert.assertTrue(firstProductMessage.contains("You added"), "First product add-to-cart message not as expected.");

        test.info("Add the second product to the shopping cart");
        lumaPage.clickJacketLink();
        lumaPage.addProductToCart(TestConfig.productTwoHover, TestConfig.sizeXS, TestConfig.colorSelect);

        String secondProductMessage = lumaPage.getAddedToCartMessage();
        test.info("Second product added to cart message: " + secondProductMessage);
        Assert.assertTrue(secondProductMessage.contains("You added"), "Second product add-to-cart message not as expected.");

    }





    @AfterMethod
    public void tearDownTest() {
        try {
            captureScreenshot("TestResult");
        } finally {
            tearDown();
        }
    }

    @AfterClass
    public void finalizeReport() {
        extent.flush();
    }
}
