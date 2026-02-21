package pages;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utility.Screenshot;
import utility.Waits;

public class CheckoutPage {
	WebDriver driver;
	Waits wait;
    @FindBy(xpath="//*[@id=\"__next\"]/div/div/div[2]/span/span")WebElement cartimage;
    @FindBy(xpath="//div[text()=\"Checkout\"]")WebElement checkout;
	@FindBy(id="firstNameInput")WebElement firstn;
	@FindBy(id="lastNameInput")WebElement lastname;
	@FindBy(id="addressLine1Input")WebElement address;
	@FindBy(id="provinceInput")WebElement state;
	@FindBy(id="postCodeInput")WebElement postal;
	@FindBy(id="checkout-shipping-continue")WebElement submit;
	@FindBy(id="confirmation-message")WebElement msg;
	@FindBy(xpath="//input[@type=\"text\"]")WebElement search;
	@FindBy(xpath="//*[@id=\"__next\"]/div/div/div[1]/div/div/div[2]/div/button")WebElement searchicon;
	@FindBy(xpath="//div[@id=1]")WebElement productsdetails;

	
	
	public CheckoutPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
        wait = new Waits(driver, 10);

	}
	public void checkout(String fn,String ln,String ad,String ps,String st) throws InterruptedException {
		wait.waitForClickable(checkout).click();
		wait.waitForClickable(firstn).sendKeys(fn);
		wait.waitForClickable(lastname).sendKeys(ln);
		address.sendKeys(ad);
		state.sendKeys(st);
		postal.sendKeys(ps);
		Screenshot.getScreenshot(driver);

		wait.waitForClickable(submit).click();
		Screenshot.getScreenshot(driver);

		Thread.sleep(5000);
		Screenshot.getScreenshot(driver);

		System.out.println("Confirmation message :" + msg.getText());
		
		}
	
	
}