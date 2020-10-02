# SeleniumFrameworkPOM
Selenium automation framework with POM pattern

1.	Create a Maven project
2.	Add below dependencies and plugins in the pom.xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>

  <groupId>SeleniumFrameworkPOM</groupId>
  <artifactId>SeleniumFrameworkPOM</artifactId>
  <version>0.0.1-SNAPSHOT</version>
 
  <name>SeleniumFrameworkPOM</name>
  <url>http://maven.apache.org</url>

  <properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <build>
		<plugins>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-compiler-plugin</artifactId>
				<version>3.8.1</version>
				<configuration>
					<source>1.8</source>
					<target>1.8</target>
				</configuration>
			</plugin>
			<plugin>
				<groupId>org.apache.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>3.0.0-M1</version>
				<configuration>
					<suiteXmlFiles>
						<suiteXmlFile>testng.xml</suiteXmlFile>
					</suiteXmlFiles>
				</configuration>
			</plugin>
		</plugins>
	</build>
	<dependencies>
		<dependency>
			<groupId>org.seleniumhq.selenium</groupId>
			<artifactId>selenium-java</artifactId>
			<version>3.141.59</version>
		</dependency>
		<dependency>
			<groupId>io.github.bonigarcia</groupId>
			<artifactId>webdrivermanager</artifactId>
			<version>4.0.0</version>
		</dependency>
		<dependency>
			<groupId>org.testng</groupId>
			<artifactId>testng</artifactId>
			<version>6.14.3</version>
			<scope>test</scope>
		</dependency>

	</dependencies>
</project>

3.	Create ‘pages’ folder under src/main/java
4.	Add BasePage under ‘pages’ folder
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
5.	Create other pages which will inherit BasePage
LoginPage.java will look like below:

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

6.	Add TestScripts folder and add TestBase.java under that folder
package com.sauceDemo.TestScripts;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import com.sauceDemo.pages.LoginPage;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TestBase {
	
	private WebDriver driver;
	private final String URL = "https://www.saucedemo.com/";
	
	protected LoginPage loginPage;
	
	@BeforeTest
	public void setup(){
		WebDriverManager.chromedriver().setup();
		driver= new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(URL);
		loginPage= new LoginPage(driver);		
	}

	@AfterTest
	public void tearDown(){
		if(driver !=null){
			driver.quit();
		}
	}


}


7.	Our project looks like below
 
8.	Add  the test classes under TestScripts folder
LoginTest.class will look like below
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

9.	If we run our .xml file, it will execute in chrome
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE suite SYSTEM "http://testng.org/testng-1.0.dtd">
<suite name="Suite"  parallel="none" thread-count="2">
  <test name="Test1">
    <classes>
      <class name="com.sauceDemo.TestScripts.LoginTest"/>
    </classes>
  </test> <!-- Test -->
  
  <test name="Test2">
    <classes>
      <class name="com.sauceDemo.TestScripts.ProductAddTest"/>
    </classes>
  </test> <!-- Test -->
</suite> <!-- Suite -->

 
10.	We can run the project using Maven as well
Open git bash
Go to project directory
Hit mvn clean test
 



