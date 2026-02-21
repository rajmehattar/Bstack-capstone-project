package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import utility.Screenshot;
import utility.Waits;

public class ProductPage {
	WebDriver driver;
	Waits wait;
	@FindBy(xpath="//input[@type=\"text\"]")WebElement search;
	@FindBy(xpath="//*[@id=\"__next\"]/div/div/div[1]/div/div/div[2]/div/button")WebElement searchicon;
	@FindBy(xpath="//div[@id=1]")WebElement productsdetails;
	public ProductPage(WebDriver driver) {
		this.driver=driver;
		PageFactory.initElements(driver,this);
        wait = new Waits(driver, 10);
}
	public void product(String item) throws InterruptedException {
		search.sendKeys(item);
		Thread.sleep(3000);
		Screenshot.getScreenshot(driver);

		searchicon.click();
	    Screenshot.getScreenshot(driver);

		String fullText = driver.findElement(By.xpath("//small[@class='products-found']")).getText();
		System.out.println("Products info: " + fullText);
		String productdetails = productsdetails.getText();
		System.out.println("product details "+productdetails);

		
		}
}