package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


import utility.Screenshot;
import utility.Waits;

public class LoginPage {
	WebDriver driver;
	utility.Waits wait;
	utility.Screenshot sc;

	// PageFactory Locators
	@FindBy(id = "signin")
	WebElement signin;

	@FindBy(xpath = "//div[text()='Select Username']")
	WebElement usernameDropdown;

	@FindBy(xpath = "//div[text()='Select Password']")
	WebElement passwordDropdown;

	@FindBy(xpath = "//button[text()='Log In']")
	WebElement loginBtn;

	@FindBy(xpath = "//span[text()='Logout']")
	WebElement logoutBtn;

	@FindBy(xpath = "//*[@id=\"react-select-2-input\"]")
	WebElement userinput;
	@FindBy(xpath = "//*[@id=\"react-select-3-input\"]")
	WebElement passinput;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
		wait = new Waits(driver, 10);
	}

	// Method to login
	public String logincred(String username, String password) throws InterruptedException {
		// Step 1: Click Sign In
		wait.waitForClickable(signin).click();

		wait.waitForClickable(usernameDropdown).click();
		wait.waitForClickable(userinput).clear();

		wait.waitForClickable(userinput).sendKeys(username + Keys.ENTER);
		wait.waitForClickable(passwordDropdown).click();
		wait.waitForClickable(passinput).clear();

		wait.waitForClickable(passinput).sendKeys(password + Keys.ENTER);
		Screenshot.getScreenshot(driver);

		wait.waitForClickable(loginBtn).click();
		Thread.sleep(3000);
		Screenshot.getScreenshot(driver);

		return "Login attempted with user: " + username;

	}

	public String empty(String pwd) throws InterruptedException {
		wait.waitForClickable(signin).click();

		wait.waitForClickable(usernameDropdown).click();
		wait.waitForClickable(userinput).sendKeys(Keys.CONTROL + "a", Keys.BACK_SPACE);
		wait.waitForClickable(usernameDropdown).click();

		wait.waitForClickable(passwordDropdown).click();
		wait.waitForClickable(passinput).clear();

		wait.waitForClickable(passinput).sendKeys(pwd + Keys.ENTER);
		Screenshot.getScreenshot(driver);

		wait.waitForClickable(loginBtn).click();
		Thread.sleep(3000);
		Screenshot.getScreenshot(driver);

		return pwd;

	}

	public void logout() {
		wait.waitForClickable(logoutBtn).click();
	}

	
	
	public boolean isLogoutVisible() {
		try {
			return logoutBtn.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	@FindBy(xpath = "//h3[contains(text(),\"Invalid\")]")
	WebElement invalidMsg;

	public boolean isInvalidMsgVisible() {
		try {
			return invalidMsg.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

}