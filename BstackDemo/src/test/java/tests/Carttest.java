package tests;

import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import utility.Webdriver;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class Carttest {
  WebDriver driver;
  LoginPage l;
  Webdriver w;
  CartPage c;
  JavascriptExecutor js;
  

  @BeforeTest
  public void setup() {
      w = new Webdriver();
      driver = w.launchbrowser("https://bstackdemo.com/");
      l = new LoginPage(driver);
      c= new CartPage(driver);
      js = (JavascriptExecutor) driver;
  }
@Test(priority = 1)
  public void login() throws InterruptedException {
	   l.logincred("demouser", "testingisfun99");
  }


@Test(priority = 2)
public void addingMul() throws InterruptedException {
	js.executeScript("window.scrollBy(0,500)");

	c.addingMultiple();
	 int total = c.getCartCount();
	    System.out.println("Cart count  " + total);
	    Assert.assertEquals(total, 3, "Cart count before removing should be 3");

	   }

@Test(priority = 3)
public void remove() throws InterruptedException {
	c.remove();;
	   
}
@Test(priority = 4)
public void verifyCartCount() {
	 int totalAfter = c.getCartCount();
	    System.out.println("Cart count after removing: " + totalAfter);
	    Assert.assertEquals(totalAfter, 2, "Cart count after removing should be 2");
	}

@AfterTest
public void afterTest() {
	  driver.close();
}
}