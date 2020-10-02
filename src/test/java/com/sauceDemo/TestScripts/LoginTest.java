package com.sauceDemo.TestScripts;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sauceDemo.pages.ProductCatalogPage;

public class LoginTest extends TestBase {
	
	@Test(priority=4,description= "Verify user is able to log in with standard user")
	public void vertifySuccessfullLogin(){
		ProductCatalogPage catalogPage= loginPage.doLogin("standard_user", "secret_sauce");
		Assert.assertTrue(catalogPage.isProductLabelDisplayed(), "Product label is not displayed");
	}
	
	@Test(priority=1,description= "Verify user is unable to login with locked out user")
	public void verifyLockedOutUserLogin(){
		loginPage.doLogin("locked_out_user", "secret_sauce");
		Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
		Assert.assertTrue(loginPage.getErrorMsg().contains("Sorry, this user has been locked out."), "Correct Error message is not displayed");
	}
	
	@Test(priority=2,description= "Verify user is unable to login when no pasword is given")
	public void verifyLoginShouldFailWithoutPassword(){
		loginPage.setUserName("Test")
				  .setPassword("")
				  .clickLoginButtn();
		Assert.assertTrue(loginPage.isErrorMessageDisplayed(), "Error message is not displayed");
	}
	
	@Test(priority=3,description= "Verify user is unable to login when username and password is invalid")
	public void verifyUnableToLoginWithInvalidCredentials(){
		loginPage.doLogin("Testuser", "12345");
		Assert.assertTrue(loginPage.getErrorMsg().contains("Username and password do not match any user in this service"), "Correct Error message is not displayed");
	}
	
	

}
