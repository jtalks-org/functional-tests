package org.jtalks.tests.jcommune.forchange;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import java.io.IOException;
import java.util.List;

/**
 * This functional test covers SignUp test case TC-JC7
 * http://jtalks.org/display/jcommune/TC-JC7+Topic+operations+validation
 *
 * @autor masyan
 */
public class TCJC7TopicOperationsValidation extends JCommuneSeleniumTest {
	List<WebElement> branches;
	String acceptedTitle;

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void checkBranchesListTest(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password, appUrl);
		branches = driver.findElements(By.xpath("//a[@class='forum_link']"));
		if (branches.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 2)
	public void clickButtonNewTopicTest() {
		CollectionHelp.getRandomWebElementFromCollection(branches).click();
		driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/topics/new')]")).click();
		try {
			driver.findElement(By.id("topicDto"));
		}
		catch (NoSuchElementException e) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 3)
	public void checkErrorMessageWithEmptyTitleTopicTest() {
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='subject']")));
	}

	@Test(priority = 4)
	public void checkErrorMessageWithShortTitleTopicTest() throws IOException {
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(StringHelp.getRandomString(4));
		driver.findElement(By.id("post")).click();

		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='subject']")));
	}

	//checking on the value of more than 255 characters do not need, because the field is limited to 255 characters


	@Test(priority = 5)
	public void checkErrorMessageWithValidTitleTopicTest() {
		acceptedTitle = StringHelp.getRandomString(255);
		driver.findElement(By.xpath("//input[@id='subject']")).clear();
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(acceptedTitle);
		driver.findElement(By.id("post")).click();
		try {
			driver.findElement(By.xpath("//span[@id='subject']"));
			Assert.assertFalse(true);
		}
		catch (NoSuchElementException e) {
		}
	}

	@Test(priority = 6)
	public void checkErrorMessageWithEmptyBodyTopicTest() {
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='bodyText.errors']")));
	}

	@Test(priority = 7)
	public void checkErrorMessageWithShortBodyTopicTest() {
		String text = StringHelp.getRandomString(4);
		driver.findElement(By.id("tbMsg")).sendKeys(text);
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='bodyText.errors']")));
		Assert.assertEquals(driver.findElement(By.id("tbMsg")).getText(), text);
	}

	@Test(priority = 8)
	public void checkErrorMessageWithLongBodyTopicTest() throws InterruptedException {
		String text = StringHelp.getRandomString(20001);
		driver.findElement(By.id("tbMsg")).clear();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")), text);
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='bodyText.errors']")));
		Assert.assertEquals(driver.findElement(By.id("tbMsg")).getText(), text);
	}

	@Test(priority = 9)
	public void checkErrorMessageWithValidBodyTopicTest() {
		driver.findElement(By.id("tbMsg")).clear();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")), StringHelp.getRandomString(20000));
		driver.findElement(By.id("post")).click();

		Assert.assertNotNull(driver.findElement(By.linkText(acceptedTitle)));
	}

}
