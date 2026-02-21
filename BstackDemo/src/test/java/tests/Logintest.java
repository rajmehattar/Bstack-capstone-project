package tests;

import org.testng.annotations.Test;

import pages.LoginPage;

import utility.Webdriver;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

public class Logintest {
  WebDriver driver;
  pages.LoginPage l;
  Webdriver w;
  
  @BeforeMethod
  public void resetAppState() {
      driver.navigate().to("https://bstackdemo.com/");
  }
  @BeforeTest
  public void setup() {
      w = new Webdriver();
      driver = w.launchbrowser("https://bstackdemo.com/");
      l = new LoginPage(driver);
  }
  @Test(priority = 1)
  public void valid() throws InterruptedException {
	  String message =  l.logincred("demouser", "testingisfun99");
	  System.out.println(message);
	 //String actualTitle = driver.getTitle();
	   // Assert.assertTrue(actualTitle.contains("true"), "Login failed!");
	  Assert.assertTrue(l.isLogoutVisible(), "Login failed!");
	  

	    l.logout();
 } 
  @Test(priority = 2)
  public void invalid() throws InterruptedException {
	  
	  String message =  l.logincred("image_not_loading_user", "hello");
	  System.out.println(message);
	 //String actualTitle = driver.getTitle();
	    Assert.assertTrue(l.isInvalidMsgVisible(), "Expected error message not displayed for invalid login.");
	      driver.navigate().refresh();

  }
  @Test(priority = 3)
  public void empty() throws InterruptedException {
	  String message = l.empty("hello");
	  System.out.println(message);
	 //String actualTitle = driver.getTitle();
	    Assert.assertTrue(l.isInvalidMsgVisible(), "Expected error message not displayed for empty password.");
	      driver.navigate().refresh();
  }
@AfterTest
  public void afterTest() throws InterruptedException {
	 Thread.sleep(5000);
	  driver.quit();
  }

}