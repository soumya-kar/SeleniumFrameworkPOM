package com.sauceDemo.pages;

import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public abstract class BasePage {

	protected WebDriver driver;

	public BasePage(WebDriver driver) {
		this.driver = driver;
	}

	protected WebElement getElement(By locator) {
		return driver.findElement(locator);
	}

	protected void type(String text, By locator) {
		getElement(locator).clear();
		if(text.length()>0)
			getElement(locator).sendKeys(text);
	}

	protected void click(By locator) {
		getElement(locator).click();
	}

	protected String getTitle() {
		return driver.getTitle();
	}

	protected boolean isDisplayed(By locator) {
		try {
			return getElement(locator).isDisplayed();
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	protected String getElementText(By locator){
		return getElement(locator).getText();
	}
}
