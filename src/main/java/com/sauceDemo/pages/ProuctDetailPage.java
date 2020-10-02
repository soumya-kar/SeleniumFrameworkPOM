package com.sauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProuctDetailPage extends BasePage{

	public ProuctDetailPage(WebDriver driver) {
		super(driver);
	}
	
	private By addToCartButton= By.xpath("//button[text()='ADD TO CART']");
	private By productName= By.cssSelector(".inventory_details_name");
	
	public boolean isAddToCartButtonDisplayed(){
		return isDisplayed(addToCartButton);
	}
	
	public String getProductName(){
		return getElementText(productName);
	}

}
