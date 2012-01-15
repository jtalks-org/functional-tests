package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import java.util.List;

/**
 * This functional test covers Topic edit validation  test case TC-JC10
 * http://jtalks.org/display/jcommune/TC-JC10+Topic+edit+validation
 *
 * @autor masyan
 */
public class TopicEditValidationTCJC10 extends JCommuneSeleniumTest {
	List<WebElement> branches;
	String subject;
	String message;

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
	public void createTopicForCheckValidationTest() {
		CollectionHelp.getRandomWebElementFromCollection(branches).click();
		driver.findElement(By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]")).click();
		driver.findElement(By.id("subject")).sendKeys(StringHelp.getRandomString(10));
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(20));
		driver.findElement(By.id("post")).click();
	}

	@Test(priority = 3)
	public void clickButtonEditTopicTest() {
		driver.findElement(By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics') and contains(@href, 'edit')]")).click();
		Assert.assertNotNull(driver.findElement(By.id("topicDto")));
	}

	@Test(priority = 4)
	public void checkErrorMessageWithEmptyTitleTopicTest() {
		driver.findElement(By.id("tbMsg")).clear();
		driver.findElement(By.xpath("//input[@id='subject']")).clear();
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='subject']")));
	}

	@Test(priority = 5)
	public void checkErrorMessageWithShortTitleTopicTest() {
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(StringHelp.getRandomString(3));
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[@id='subject']")));
	}

	//checking on the value of more than 255 characters do not need, because the field is limited to 255 characters

	@Test(priority = 6)
	public void checkErrorMessageWithValidTitleTopicTest() {
		subject = StringHelp.getRandomString(10);
		driver.findElement(By.xpath("//input[@id='subject']")).clear();
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(subject);
		driver.findElement(By.id("post")).click();
		try {
			driver.findElement(By.xpath("//span[@id='subject']"));
			//if element exists, then generate false to test. Because title is valid
			Assert.assertFalse(true);
		}
		catch (NoSuchElementException e) {
		}
	}

	@Test(priority = 7)
	public void checkErrorMessageWithEmptyBodyTopicTest() {
		driver.findElement(By.id("tbMsg")).clear();
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.id("bodyText.errors")));
	}

	@Test(priority = 8)
	public void checkErrorMessageWithShortBodyTopicTest() {
		driver.findElement(By.id("tbMsg")).clear();
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(3));
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.id("bodyText.errors")));
	}

	@Test(priority = 9)
	public void checkErrorMessageWithLongBodyTopicTest() {
		driver.findElement(By.id("tbMsg")).clear();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")), StringHelp.getRandomString(20001));
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.id("bodyText.errors")));
	}

	@Test(priority = 10)
	public void checkErrorMessageWithValidBodyTopicTest() {
		message = StringHelp.getRandomString(20000);
		driver.findElement(By.id("tbMsg")).clear();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")), message);
		driver.findElement(By.id("post")).click();
	}

	@Test(priority = 11)
	public void checkEditedSubjectTopicTest() {
		String currentSubject = driver.findElement(By.xpath("//a[@class='heading']")).getText();
		Assert.assertEquals(currentSubject, subject);
	}

	@Test(priority = 12)
	@Parameters({"app-url"})
	public void checkEditedMessageTopicTest(String appUrl) {
		String currentMessage = driver.findElement(By.xpath("//div[@class='forum_message_cell_text']")).getText();
		Assert.assertEquals(currentMessage, message);
		logOut(appUrl);
	}
}
