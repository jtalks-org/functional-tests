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

import java.util.ArrayList;
import java.util.List;

/**
 * This functional test covers Seciruty test case TC-JC9
 * http://jtalks.org/display/jcommune/TC-JC9++Security
 *
 * @autor masyan
 */
public class TCJC9security extends JCommuneSeleniumTest {
	ArrayList<String> brancheNames = new ArrayList();
	List<WebElement> topics;

	// Unregistered user

	@Test(priority = 1)
	@Parameters({"app-url"})
	public void checkBranchesListByUnregisteredUserTest(String appUrl) {
		driver.get(appUrl);
		List<WebElement> branches = driver.findElements(By.xpath("//a[@class='forum_link']"));
		//we get names, because when we get new page and back to page with Branches list we cant't use WebElements which found
		for (WebElement branch : branches) {
			brancheNames.add(branch.getText());
		}
		if (branches.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 2)
	public void checkButtonNewTopicByUnregisteredUserTest() {
		driver.findElement(By.linkText(brancheNames.get(0))).click();
		try {
			driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/topics/new')]"));
			//if element exist, then generate false to test. Because user is unregistered
			Assert.assertFalse(true);
		}
		catch (NoSuchElementException e) {
			driver.navigate().back();
		}
	}

	@Test(priority = 3)
	public void checkTopicsListInBranchByUnregisteredUserTest() {
		WebElement branchEl;
		for (String branchName : brancheNames) {
			branchEl = driver.findElement(By.linkText(branchName));
			branchEl.click();
			topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
			if (topics.size() > 0) {
				return;
			}
			driver.navigate().back();
		}
		//if we not found branch with topic, then throw Exception
		Assert.assertFalse(true);
	}

	@Test(priority = 4)
	public void checkButtonAnswerInTopicByUnregisteredUserTest() {
		CollectionHelp.getRandomWebElementFromCollection(topics).click();
		try {
			driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts/new') and contains(@class,'disabled')]"));
		}
		catch (NoSuchElementException e) {
			//if element not exist, then generate false to test. Because user is unregistered
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 5)
	public void checkButtonsDeleteAndEditInTopicByUnregisteredUserTest() {
		try {
			List<WebElement> delButtons = driver.findElements(By.xpath("//a[contains(@href,'javascript:confirmAndDelete') and contains(@class,'button')]"));
			List<WebElement> editButtons = driver.findElements(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts') and contains(@href,'edit')]"));
			//if Delete or Edit buttons exists, then generate false to test. Because user is unregistered
			if (delButtons.size() > 0 || editButtons.size() > 0) {
				Assert.assertFalse(true);
			}
		}
		catch (NoSuchElementException e) {
		}
	}

	//Registered user

	@Test(priority = 6)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void checkButtonNewTopicByRegisteredUserTest(String appUrl, String username, String password) {
		signIn(username, password, appUrl);
		driver.findElement(By.linkText(brancheNames.get(0))).click();
		try {
			driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/topics/new')]"));
			driver.navigate().back();
		}
		catch (NoSuchElementException e) {
			//if element not exist, then generate false to test. Because user is registered
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 7)
	public void checkTopicsListInBranchByRegisteredUserTest() {
		WebElement branchEl;
		for (String branchName : brancheNames) {
			branchEl = driver.findElement(By.linkText(branchName));
			branchEl.click();
			topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
			if (topics.size() > 0) {
				return;
			}
			driver.navigate().back();
		}
		//if we not found branch with topic
		Assert.assertFalse(true);
	}

	@Test(priority = 8)
	public void checkButtonAnswerInTopicByRegisteredUserTest() {
		CollectionHelp.getRandomWebElementFromCollection(topics).click();
		try {
			driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts/new') and @class='button top_button']"));
		}
		catch (NoSuchElementException e) {
			//if element not exist, then generate false to test. Because user is registered
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 9)
	@Parameters({"app-url", "uUsername"})
	public void checkButtonsDeleteAndEditForTheirPostsInTopicByRegisteredUserTest(String appUrl, String username) {
		//create post for test permissions
		driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts/new') and @class='button top_button']")).click();
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(8));
		driver.findElement(By.id("post")).click();

		List<WebElement> posts = driver.findElements(By.xpath("//ul[@class='forum_table']/li"));
		checkButtonsDeleteAndEditForTheirPosts(posts, username, appUrl);
	}


	private void checkButtonsDeleteAndEditForTheirPosts(List<WebElement> posts, String username, String appUrl) {
		String userPost;
		for (WebElement post : posts) {
			userPost = post.findElement(By.xpath("div[@class='forum_userinfo']/a[@class='username']")).getText();
			// try to find buttons Delete and Edit
			try {
				post.findElement(By.xpath("div[@class='forum_message_cell']/div[@class='post_details']/a[contains(@href, 'javascript:confirmAndDelete')]"));
				post.findElement(By.xpath("div[@class='forum_message_cell']/div[@class='post_details']/a[contains(@href,'edit')]"));
				// if founded, then usernaame should be owner this post
				Assert.assertEquals(userPost, username);

			}
			catch (NoSuchElementException e) {
				// if not founded, then usernaame should isn't owner this post
				Assert.assertFalse(userPost.equals(username));
			}
		}
		logOut(appUrl);
	}

	//Admin user

	@Test(priority = 10)
	@Parameters({"app-url", "aUsername", "aPassword"})
	public void checkButtonNewTopicByAdminUserTest(String appUrl, String username, String password) {

		signIn(username, password, appUrl);
		driver.findElement(By.linkText(brancheNames.get(0))).click();
		try {
			driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/topics/new')]"));
			driver.navigate().back();
		}
		catch (NoSuchElementException e) {
			//if element not exist, then generate false to test. Because user is Admin
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 11)
	public void checkTopicsListInBranchByAdminUserTest() {
		WebElement branchEl;
		for (String branchName : brancheNames) {
			branchEl = driver.findElement(By.linkText(branchName));
			branchEl.click();
			topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
			if (topics.size() > 0) {
				return;
			}
			driver.navigate().back();
		}
		//if we not found branch with topic
		Assert.assertFalse(true);
	}

	@Test(priority = 12)
	public void checkButtonAnswerInTopicByAdminUserTest() {
		CollectionHelp.getRandomWebElementFromCollection(topics).click();
		try {
			driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts/new') and @class='button top_button']"));
		}
		catch (NoSuchElementException e) {
			//if element not exist, then generate false to test. Because user is Admin
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 13)
	@Parameters({"app-url"})
	public void checkButtonsDeleteAndEditForAllPostsInTopicByAdminUserTest(String appUrl) {
		//create post for test permissions
		driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts/new') and @class='button top_button']")).click();
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(8));
		driver.findElement(By.id("post")).click();

		List<WebElement> posts = driver.findElements(By.xpath("//ul[@class='forum_table']/li"));
		checkButtonsDeleteAndEditForAllPosts(posts, appUrl);
	}

	private void checkButtonsDeleteAndEditForAllPosts(List<WebElement> posts, String appUrl) {
		for (WebElement post : posts) {
			// try to find buttons Delete and Edit
			try {
				post.findElement(By.xpath("div[@class='forum_message_cell']/div[@class='post_details']/a[contains(@href, 'javascript:confirmAndDelete')]"));
				post.findElement(By.xpath("div[@class='forum_message_cell']/div[@class='post_details']/a[contains(@href,'edit')]"));
			}
			catch (NoSuchElementException e) {
				// if not founded, then generate error. Because user is Admin
				Assert.assertFalse(true);
			}
		}
		logOut(appUrl);
	}
}
