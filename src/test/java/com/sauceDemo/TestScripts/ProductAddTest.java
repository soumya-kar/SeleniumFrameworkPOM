package com.sauceDemo.TestScripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sauceDemo.pages.ProductCatalogPage;
import com.sauceDemo.pages.ProuctDetailPage;

public class ProductAddTest extends TestBase {
	ProductCatalogPage catalogPage;
	ProuctDetailPage productDetail;

	@Test(priority = 1)
	public void verifyUserCanSeeAddToCartOnProductDetail() {
		productDetail=loginPage.doLogin("standard_user", "secret_sauce")
				  		        .clickOnProduct("Sauce Labs Backpack");
		Assert.assertTrue(productDetail.isAddToCartButtonDisplayed(),
				"Add to cart button is not displayed on Product detail");

	}

	@Test(priority = 2)
	public void verifyCorrectProductGetsAddedToCart() {
		Assert.assertTrue(productDetail.getProductName().equals("Sauce Labs Backpack"),
				"Correct product name is not displayed");

	}

}
