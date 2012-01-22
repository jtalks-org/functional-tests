package org.jtalks.tests.jcommune;

import java.util.List;
import java.util.Random;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.CollectionHelp;
import utils.StringHelp;

/**
 * This functional test covers testing of Permanent post link
 * http://jtalks.org/display/jcommune/TC-JC15+Permanent+post+link
 * 
 * @author erik
 * 
 */
public class TCJC15PermanentPostLink extends JCommuneSeleniumTest {
	List<WebElement> webElementsList;
	String subject;
	String message;
	String postContent;
	String linkToPost;

	@Test(priority = 1)
	@Parameters({ "app-url", "uUsername", "uPassword" })
	public void signInCreateTopicAndPostTest(String appURL, String username,
			String password) {
		driver.get(appURL);
		signIn(username, password, appURL);

		// choose and click on random section
		webElementsList = driver.findElements(By
				.xpath("//a[@class='forum_header_link']"));
		CollectionHelp.getRandomWebElementFromCollection(webElementsList)
				.click();

		// choose and click on random branch where topics present
		chooseAndClickOnBranch("//a[@class='forum_link']",
				"//a[@class='forum_link']");

		// creating new topic
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath()
						+ "/topics/new')]")).click();
		subject = StringHelp.getRandomString(50);
		message = StringHelp.getRandomString(500);
		postContent = StringHelp.getRandomString(500);

		StringHelp.setLongTextValue(driver,
				driver.findElement(By.id("subject")), subject);
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				message);
		driver.findElement(By.id("post")).click();

		Assert.assertEquals(
				driver.findElement(By.xpath("//a[@class='heading']")).getText(),
				subject);
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//div[@class='forum_message_cell_text']"))
						.getText(), message);

		// creating new post
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath()
						+ "/posts/new')]")).click();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				postContent);
		driver.findElement(By.id("post")).click();
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']"))
						.getText(), postContent);
	}

	@Test(priority = 2)
	public void clickOnPermanentLinkTest() {
		Random rnd = new Random();
		driver.findElement(
				By.xpath("//ul[@class='forum_table']//li[@class='forum_row']["
						+ (rnd.nextInt(2) + 1)
						+ "]//a[@class='button postLink']")).click();
		Assert.assertTrue(isElementPresent("//div[@class='jqimessage']"));

	}

	@Test(priority = 3)
	public void copyLinkAndCloseWindowTest() {
		linkToPost = driver.findElement(By.xpath("//div[@class='jqimessage']"))
				.getText();
		driver.findElement(By.xpath("//div[@class='jqiclose']")).click();
		Assert.assertFalse(isElementPresent("//div[@class='jqimessage']"));
	}

	@Test(priority = 4)
	@Parameters({ "app-url" })
	public void goToMainPageAndToPostTest(String appUrl) {
		driver.get(appUrl);
		driver.get(linkToPost);
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']"))
						.getText(), postContent);
	}

	@Test(priority = 5)
	@Parameters({ "app-url" })
	public void deletePostTest(String appUrl) {
		driver.findElement(
				By.xpath("//li[@class='forum_row'][last()]//a[@class='button delete']"))
				.click();
		driver.findElement(By.id("jqi_state0_buttonOk")).click();
		driver.get(appUrl);
		driver.get(linkToPost);
		Assert.assertEquals(
				driver.findElement(By.xpath("//a[@class='heading']")).getText(),
				subject);
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//div[@class='forum_message_cell_text']"))
						.getText(), message);
	}

	@Test(priority = 6)
	@Parameters({ "app-url" })
	public void deleteTopicTest(String appUrl) {
		driver.findElement(
				By.xpath("//li[@class='forum_row'][last()]//a[@class='button delete']"))
				.click();
		driver.findElement(By.id("jqi_state0_buttonOk")).click();

		driver.get(appUrl);
		driver.get(linkToPost);
		Assert.assertTrue(isElementPresent("//span[@class='error_errorpage']"));
		Assert.assertTrue(driver
				.findElement(By.xpath("//div[@class='text_errorpage']/h1"))
				.getText().contains("404"));
	}

	/**
	 * This method gather branches urls and clicks on it, if topics don't exist
	 * in branch, method takes other branch and checks if topics exist etc.
	 * 
	 * @param branchXpath
	 *            xpath of branch webelement
	 * @param topicXpath
	 *            xpath of topic webelement
	 */
	private void chooseAndClickOnBranch(String branchXpath, String topicXpath) {
		webElementsList = driver.findElements(By.xpath(branchXpath));
		for (WebElement branch : webElementsList) {
			branch.click();
			if (isElementPresent(topicXpath))
				break;
		}
	}

	/**
	 * This method checks that webelement presents on page;
	 * 
	 * @param xpath
	 *            of necessary webelement
	 * @return
	 */
	private boolean isElementPresent(String xpath) {
		if (driver.findElements(By.xpath(xpath)).size() > 0) {
			return true;
		}
		return false;
	}
}
