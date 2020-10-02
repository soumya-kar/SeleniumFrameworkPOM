package com.sauceDemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	private By userName = By.id("user-name");
	private By password = By.id("password");
	private By loginButtn = By.id("login-button");
	private By errorMsg = By.cssSelector("#login_button_container h3");

	public LoginPage setUserName(String uname) {
		type(uname, userName);
		return this;
	}

	public LoginPage setPassword(String pwd) {
		type(pwd, password);
		return this;
	}

	public ProductCatalogPage clickLoginButtn() {
		click(loginButtn);
		return new ProductCatalogPage(driver);
	}

	public String getErrorMsg() {
		return getElementText(errorMsg);
	}

	public ProductCatalogPage doLogin(String uname, String pwd) {
		setUserName(uname)
		.setPassword(pwd);
		return clickLoginButtn();
	}

	public boolean isErrorMessageDisplayed() {
		return isDisplayed(errorMsg);
	}

}
