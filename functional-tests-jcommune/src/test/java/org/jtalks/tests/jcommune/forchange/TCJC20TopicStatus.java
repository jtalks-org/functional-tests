package org.jtalks.tests.jcommune.forchange;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import java.util.List;

/**
 * This functional test covers Path to the current page test case TC-JC19
 * http://jtalks.org/display/jcommune/TC-JC19+Path+to+the+current+page
 *
 * @autor masyan
 */
public class TCJC20TopicStatus extends JCommuneSeleniumTest {
	List<WebElement> branches;
	List<WebElement> topics;
	String selectedTopicTitle;

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void checkBranchesListOnMainPageTest(String appUrl, String username, String password) {
		driver.get(appUrl);

		signIn(username, password, appUrl);

		branches = driver.findElements(By.xpath("//a[@class='forum_link']"));

		//branches
		if (branches.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 2)
	public void checkTopicsListOnBranchPageTest() {
		CollectionHelp.getRandomWebElementFromCollection(branches).click();
		// create 2 topics
		for (int i = 0; i < 2; i++) {
			driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/topics/new')]")).click();
			driver.findElement(By.id("subject")).sendKeys(StringHelp.getRandomString(10));
			driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(10));
			driver.findElement(By.id("post")).click();
			driver.findElement(By.xpath("//a[contains(text(),'Back')]")).click();
		}
		topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
		if (topics.size() < 2) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 3)
	public void checkPostsListOnTopicPageTest() {
		//topics contains 2 topic elements (minimum). Get not first
		topics.get(1).click();
		selectedTopicTitle = driver.findElement(By.xpath("//a[@class='heading' and @href='#']")).getText();
		List<WebElement> posts = driver.findElements(By.xpath("//li[@class='forum_row']"));
		if (posts.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 4)
	public void checkTopicPostionInListAfterBackClickTest() {
		driver.findElement(By.xpath("//a[contains(text(),'Back')]")).click();
		topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
		Assert.assertEquals(topics.get(1).getText(), selectedTopicTitle);
	}

	@Test(priority = 5)
	public void checkEditFormToTopicTest() {
		topics.get(1).click();
		driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/topics') and text()='Edit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("topicDto")));
	}

	@Test(priority = 6)
	public void checkTopicPostionWithoutSaveChangesTest() {
		driver.findElement(By.id("back")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Back')]")).click();
		topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
		Assert.assertEquals(topics.get(1).getText(), selectedTopicTitle);
	}

	@Test(priority = 7)
	public void checkTopicPositionWithSaveChangesTest() {
		topics.get(1).click();
		driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/topics') and text()='Edit']")).click();
		String oldText = driver.findElement(By.id("tbMsg")).getText();
		driver.findElement(By.id("tbMsg")).sendKeys(oldText + StringHelp.getRandomString(5));
		driver.findElement(By.id("post")).click();
		driver.findElement(By.xpath("//a[contains(text(),'Back')]")).click();
		topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
		Assert.assertEquals(topics.get(0).getText(), selectedTopicTitle);
	}
}
