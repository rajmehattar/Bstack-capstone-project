package tests;

import org.testng.annotations.Test;

import pages.CartPage;
import pages.CheckoutPage;
import pages.LoginPage;
import pages.ProductPage;
import utility.Webdriver;

import org.testng.annotations.BeforeTest;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;

public class Checkouttest {
	WebDriver driver;
	LoginPage l;
	CartPage c;
	CheckoutPage checkout;
	ProductPage pr;
	Webdriver w;
	JavascriptExecutor js;
	
	@BeforeTest
	  public void launch() {
		  w = new Webdriver();
	      driver = w.launchbrowser("https://bstackdemo.com/");
	      l = new LoginPage(driver);
	      c= new CartPage(driver);
	      checkout = new CheckoutPage(driver); 
	      pr = new ProductPage(driver);// initialize here

	      js = (JavascriptExecutor) driver;
	  }
 
	 @Test(priority = 1)
	  public void login() throws InterruptedException {
		 l.logincred("demouser", "testingisfun99");
		  
		  
	 }
	 @Test(priority = 2)
	  public void productsearch() throws InterruptedException {
		 pr.product("iphone 12");
		  
	  }
	 @Test(priority = 3)
	  public void addtocart() {

		 c.addingMultiple();

     }
	 @Test(priority = 4)
	  public void checkout() throws InterruptedException {
			js.executeScript("window.scrollBy(0,200)");

		 checkout.checkout("john", "doe", "Hyderabad", "500001", "Telangana");
	 }
		  
	 @AfterTest
  public void afterTest() {
	  driver.close();
  }

}