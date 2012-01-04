package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import java.util.List;

/**
 * This functional test covers SignUp test case TC-JC7
 * http://jtalks.org/display/jcommune/TC-JC7+Topic+operations+validation
 *
 * @autor masyan
 */
public class TopicOperationsValidationTCJC7 extends JCommuneSeleniumTest {
	List<WebElement> branches;
	boolean elementExists;
	String acceptedTitle;

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void checkBranchesListTest(String appUrl, String username, String password) {
		driver = new FirefoxDriver();
		driver.get(appUrl);
		signIn(username, password, appUrl);
		branches = driver.findElements(By.xpath("//a[@class='forum_link']"));
		if (branches.size() == 0) {
			throw new NoSuchElementException("Branches not found");
		}
	}

	@Test(priority = 2)
	public void clickButtonNewTopicTest() {
		CollectionHelp.getRandomWebElementFromCollection(branches).click();
		driver.findElement(By.xpath("//a[contains(@href,'/jcommune/topics/new')]")).click();
		driver.findElement(By.id("topicDto"));
	}

	@Test(priority = 3)
	public void checkErrorMessageWithEmptyTitleTopicTest() {
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='subject']")));
	}

	@Test(priority = 4)
	public void checkErrorMessageWithShortTitleTopicTest() {
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(StringHelp.getRandomString(4));
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='subject']")));
	}


	//    TODO  Uncommented Long Test. After fixed input text on JSP (now max length = 60)
//	@Test(priority = 5)
//	public void checkErrorMessageWithLongTitleTopicTest(){
//		driver.findElement(By.xpath("//input[@id='subject']")).clear();
//		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(StringHelp.getRandomString(256));
//		driver.findElement(By.id("post")).click();
//		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='subject']")));
//	}

	//    TODO  Change tittle size to 255. After fixed input text on JSP (now max length = 60)
	@Test(priority = 6)
	public void checkErrorMessageWithValidTitleTopicTest() {
		acceptedTitle = StringHelp.getRandomString(60);
		driver.findElement(By.xpath("//input[@id='subject']")).clear();
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(acceptedTitle);
		driver.findElement(By.id("post")).click();
		try {
			driver.findElement(By.xpath("//span[@id='subject']"));
			elementExists = true;
		}
		catch (NoSuchElementException e) {
			elementExists = false;
		}
	}

	@Test(priority = 7)
	public void checkErrorMessageWithEmptyBodyTopicTest() {
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='bodyText.errors']")));
	}

	@Test(priority = 8)
	public void checkErrorMessageWithShortBodyTopicTest() {
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(4));
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='bodyText.errors']")));
	}

	@Test(priority = 9)
	public void checkErrorMessageWithLongBodyTopicTest() throws InterruptedException {
		driver.findElement(By.id("tbMsg")).clear();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")), StringHelp.getRandomString(20001));
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='bodyText.errors']")));
	}

	@Test(priority = 10)
	public void checkErrorMessageWithValidBodyTopicTest() {
		driver.findElement(By.id("tbMsg")).clear();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")), StringHelp.getRandomString(20000));
		driver.findElement(By.id("post")).click();

		Assert.assertNotNull(driver.findElement(By.linkText(acceptedTitle)));
	}

}
