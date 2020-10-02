package com.sauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductCatalogPage extends BasePage {

	public ProductCatalogPage(WebDriver driver) {
		super(driver);
	}

	private By productHeaderLabel = By.cssSelector("#inventory_filter_container div");
	private String productLink = "//div[text()='%s']//parent::a";
	private By getProductLink(String linkText){
		return By.xpath(String.format(productLink, linkText));
	}
	
	
	public boolean isProductLabelDisplayed(){
		return isDisplayed(productHeaderLabel);
	}
	
	public ProuctDetailPage clickOnProduct(String productName){
		click(getProductLink(productName));
		return new ProuctDetailPage(driver);
	}
	
}
