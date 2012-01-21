package org.jtalks.tests.jcommune;

import java.util.List;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.CollectionHelp;
import utils.StringHelp;

/**
 * This functional test covers Deleting post in the topic, test case TC-JC3
 * http://jtalks.org/display/jcommune/TC-JC3+Delete+Post
 * 
 * @author erik
 */

public class TCJC3DeletePost extends JCommuneSeleniumTest {

	List<WebElement> webElementsList;
	String topicURL;
	String postContent;
	String postContent2;

	@BeforeTest
	public void init() {
		postContent = StringHelp.getRandomString(200);
		postContent2 = StringHelp.getRandomString(250);
	}

	@Test(priority = 1)
	@Parameters({ "app-url", "uUsername", "uPassword" })
	public void addPostTest(String appURL, String username, String password) {
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
		// choose and click on random topic
		webElementsList = driver.findElements(By
				.xpath("//a[@class='forum_link']"));
		CollectionHelp.getRandomWebElementFromCollection(webElementsList)
				.click();
		// save topic's url
		topicURL = driver.getCurrentUrl();
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath()
						+ "/posts/new')]")).click();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				postContent);
		driver.findElement(By.id("post")).click();
		

		// assert that post created
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']")).getText(), postContent);
		assertThatDeleteButtonPresent(username);
	}

	@Test(priority = 2)
	@Parameters({ "app-url", "uUsername2", "uPassword2" })
	public void addAnotherPostTest(String appURL, String username,
			String password) {
		logOut(appURL);
		signIn(username, password, appURL);
		driver.get(topicURL);
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath()
						+ "/posts/new')]")).click();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				postContent2);
		driver.findElement(By.id("post")).click();

		// assert that post created
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']"))
						.getText(), postContent2);
		assertThatDeleteButtonPresent(username);
	}

	@Test(priority = 3)
	@Parameters({ "uUsername2" })
	public void deletePostTest(String username2) {

		// click on delete button and dismiss alert
		driver.findElement(
				By.xpath("//li[@class='forum_row'][last()]//a[@class='button delete']"))
				.click();
		driver.findElement(By.id("jqi_state0_buttonCancel")).click();
		// assert that last created post presents
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//li[@class='forum_row'][last()]//a[@class='username']"))
						.getText(), username2);
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']"))
						.getText(), postContent2);

		// click on delete button and accept alert
		driver.findElement(
				By.xpath("//li[@class='forum_row'][last()]//a[@class='button delete']"))
				.click();
		driver.findElement(By.id("jqi_state0_buttonOk")).click();
		// assert that last created post not presents
		Assert.assertFalse(driver
				.findElement(
						By.xpath("//li[@class='forum_row'][last()]//a[@class='username']"))
				.getText().equals(username2));
		Assert.assertFalse(driver
				.findElement(
						By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']"))
				.getText().equals(postContent2));

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

	/**
	 * this method asserts that delete button presents only in that post rows
	 * where userName is creator, and delete button absent where userName is not
	 * creator
	 * 
	 * @param userName
	 */
	private void assertThatDeleteButtonPresent(String userName) {
		for (int i = 1; i < driver.findElements(By.xpath("//li[@class='forum_row']")).size() + 1; i++) {
			if (driver
					.findElement(
							By.xpath("//li[@class='forum_row'][" + i
									+ "]//a[@class='username']")).getText()
					.equals(userName)) {
				Assert.assertTrue(isElementPresent("//li[@class='forum_row']["
						+ i + "]//a[@class='button delete']"));
			} else {
				Assert.assertFalse(isElementPresent("//li[@class='forum_row']["
						+ i + "]//a[@class='button delete']"));
			}
		}
	}
}
