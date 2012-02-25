package org.jtalks.tests.jcommune.forchange;

import java.util.List;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.CollectionHelp;
import utils.StringHelp;

/**
 * This functional test covers Adding post to topic test case TC-JC2
 * http://jtalks.org/display/jcommune/TC-JC2+Add+post+to+the+topic
 *
 * @author erik
 */
public class TCJC2AddPostToTopic extends JCommuneSeleniumTest {
	List<WebElement> webElementsList;
	String postContent;
	String postContent2;
	String topicURL;

	@BeforeClass
	public void init() {
		postContent = StringHelp.getRandomString(150);
		postContent2 = StringHelp.getRandomString(200);
	}

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void clickOnAnswerButtonTest(String appURL, String username,
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
		// choose and click on random topic
		webElementsList = driver.findElements(By
				.xpath("//a[@class='forum_link']"));
		CollectionHelp.getRandomWebElementFromCollection(webElementsList)
				.click();
		// save topic's url
		topicURL = driver.getCurrentUrl();
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/posts/new')]"))
				.click();
		Assert.assertTrue(driver.findElement(By.id("tbMsg")).isDisplayed());
	}

	@Test(priority = 2)
	public void addPostTest() {
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				postContent);
		driver.findElement(By.id("post")).click();
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']"))
						.getText(), postContent);
	}

	@Test(priority = 3)
	@Parameters({"app-url", "uUsername2", "uPassword2"})
	public void clickOnAnswerButtonByAnotherUserTest(String appURL,
													 String username2, String password2) {
		logOut(appURL);
		signIn(username2, password2, appURL);
		driver.get(topicURL);
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/posts/new')]"))
				.click();
		Assert.assertTrue(driver.findElement(By.id("tbMsg")).isDisplayed());
	}

	@Test(priority = 4)
	public void AddPostByAnotherUserTest() {
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				postContent2);
		driver.findElement(By.id("post")).click();
		Assert.assertTrue(driver.getCurrentUrl().contains(topicURL));
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']"))
						.getText(), postContent2);
	}

	@Test(priority = 5)
	@Parameters({"app-url"})
	public void PostsPresentTest(String appURL) {
		logOut(appURL);
		driver.get(topicURL);
		webElementsList = driver.findElements(By
				.xpath("//div[@class='forum_message_cell_text']"));
		Assert.assertTrue(assertThatTopicPresent(webElementsList, postContent));
		Assert.assertTrue(assertThatTopicPresent(webElementsList, postContent2));
	}

	/**
	 * this method return true when in list presents the desired text
	 *
	 * @param list The list of webelements
	 * @param text The desired text
	 * @return
	 */
	private boolean assertThatTopicPresent(List<WebElement> list, String text) {
		for (WebElement webElement : list) {
			String t = webElement.getText();
			if (t.equals(text)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * This method gather branches urls and clicks on it, if topics don't exist
	 * in branch, method takes other branch and checks if topics exist etc.
	 *
	 * @param branchXpath xpath of branch webelement
	 * @param topicXpath  xpath of topic webelement
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
	 * @param xpath of necessary webelement
	 * @return
	 */
	private boolean isElementPresent(String xpath) {
		if (driver.findElements(By.xpath(xpath)).size() > 0) {
			return true;
		}
		return false;
	}

}
