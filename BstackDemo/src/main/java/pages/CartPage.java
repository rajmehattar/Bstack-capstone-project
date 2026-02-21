package pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utility.Screenshot;
import utility.Waits;

public class CartPage {

	WebDriver driver;
	Waits wait;
    utility.Screenshot sc;

	@FindBy(xpath="//*[@id=\"1\"]/div[4]")WebElement add1;
	@FindBy(xpath="//*[@id=\"2\"]/div[4]")WebElement add2;
	@FindBy(xpath="//*[@id=\"3\"]/div[4]")WebElement add3;
	@FindBy(xpath="//div[@class='shelf-item'][1]//div[@class='shelf-item__del']")WebElement remove;
    @FindBy(xpath="//span[text()='Logout']") WebElement logoutBtn;
    @FindBy(xpath="//*[@id=\"__next\"]/div/div/div[2]/div[2]/div[2]/div")List<WebElement> countcart;
    @FindBy(xpath="//div[@class='float-cart__close-btn']")WebElement close;
    @FindBy(xpath="//*[@id=\"__next\"]/div/div/div[2]/span/span")WebElement cartimage;
	    
	
	public CartPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
        wait = new Waits(driver, 10);

		}
	
	public void addItem() {
	    wait.waitForClickable(add1).click();
		Screenshot.getScreenshot(driver);

	    wait.waitForClickable(close).click();
	}

	
	public void addingMultiple() {
	    add1.click();
	    add2.click();
	    wait.waitForClickable(close).click();

	    add3.click();
		Screenshot.getScreenshot(driver);
}
	public void closeCart() {
	    wait.waitForClickable(close).click();
	}

	public void remove() throws InterruptedException {
		wait.waitForClickable(remove).click();
		Thread.sleep(3000);
		Screenshot.getScreenshot(driver);

	}
	public int getCartCount() {
	    int count = countcart.size();
		Screenshot.getScreenshot(driver);
		return count;	}

	 public void logout() {
	        wait.waitForClickable(logoutBtn).click();
	    }
}