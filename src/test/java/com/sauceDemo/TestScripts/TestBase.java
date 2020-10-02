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
