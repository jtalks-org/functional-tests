package org.jtalks.tests.jcommune;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * This functional test covers Viewing user profiles behaviour  test case TC-JC12
 * http://jtalks.org/display/jcommune/TC-JC12+Viewing+user+profiles+behaviour
 *
 * @autor masyan
 */
public class ViewingUserProfilesBehaviourTCJC12 extends JCommuneSeleniumTest {

	String lUsername = "Username";
	String lEmail = "Email";
	String lFirstName = "First name";
	String lLastName = "Last name";
	String lLastLogin = "Last login";
	String lPostCount = "Post count";
	String lLanguage = "Language";
	String lNumPostsOnPage = "Number of topics on page";

	String lInbox = "Inbox";

	ArrayList<String> brancheNames = new ArrayList();
	List<WebElement> topics;

	@BeforeClass(alwaysRun = true)
	@Parameters({"app-url","uUsername","uUsername2","uPassword2","aUsername"})
	public void sendPrivateMessageToAdminAndAnyUserTest(String appUrl, String username1, String username2, String password2, String admin){
		driver = new FirefoxDriver();
	   driver.get(appUrl);
		signIn(username2, password2, appUrl);
		driver.findElement(By.xpath("//a[@href='/jcommune/inbox']")).click();
		driver.findElement(By.xpath("//a[@href='/jcommune/pm/new']")).click();
		driver.findElement(By.id("recipient")).sendKeys(username1);
		driver.findElement(By.id("title")).sendKeys(StringHelp.getRandomString(8));
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(8));
		driver.findElement(By.id("post")).click();
		driver.findElement(By.xpath("//a[@href='/jcommune/inbox']")).click();
		driver.findElement(By.xpath("//a[@href='/jcommune/pm/new']")).click();
		driver.findElement(By.id("recipient")).sendKeys(admin);
		driver.findElement(By.id("title")).sendKeys(StringHelp.getRandomString(8));
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(8));
		driver.findElement(By.id("post")).click();
		logOut(appUrl);
	}

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void viewProfileUserTest(String appUrl, String username, String password) {
		signIn(username, password, appUrl);
		driver.findElement(By.xpath("//a[@class='currentusername']")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + username + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lUsername + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lEmail + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lFirstName + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastName + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLanguage + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lNumPostsOnPage + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastLogin + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lPostCount + "']")));
	}

	@Test(priority = 2)
	@Parameters("app-url")
	public void viewProfileSenderOfPrivateMessagesByUserTest(String appUrl) {
		driver.findElement(By.xpath("//a[@href='/jcommune/inbox']")).click();
		driver.findElement(By.xpath("//a[text()='"+lInbox+"']"));
		List<WebElement> elements = driver.findElements(By.xpath("//li[@class='forum_row']"));
		if (elements.size() == 0) {
			Assert.assertFalse(true);
		}
		else {
			String sender = elements.get(0).findElement(By.xpath("//div[@class='forum_answer_left']/a[contains(@href,'/jcommune/users')]")).getText();
			elements.get(0).findElement(By.xpath("//div[@class='forum_answer_left']/a[contains(@href,'/jcommune/users')]")).click();
			Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + sender + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lUsername + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lFirstName + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastName + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastLogin + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lPostCount + "']")));
			logOut(appUrl);
		}
	}

	@Test(priority = 3)
	@Parameters({"app-url", "aUsername", "aPassword"})
	public void viewProfileCurrentAdminTest(String appUrl, String username, String password) {
		signIn(username, password, appUrl);
		driver.findElement(By.xpath("//a[@class='currentusername']")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + username + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lUsername + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lEmail + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lFirstName + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastName + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLanguage + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lNumPostsOnPage + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastLogin + "']")));
		Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lPostCount + "']")));
	}

	@Test(priority = 4)
	@Parameters("app-url")
	public void viewProfileSenderOfPrivateMessagesByAdminTest(String appUrl) {
		driver.findElement(By.xpath("//a[@href='/jcommune/inbox']")).click();
		driver.findElement(By.xpath("//a[text()='"+lInbox+"']"));
		List<WebElement> elements = driver.findElements(By.xpath("//li[@class='forum_row']"));
		if (elements.size() == 0) {
			Assert.assertFalse(true);
		}
		else {
			String sender = elements.get(0).findElement(By.xpath("//div[@class='forum_answer_left']/a[contains(@href,'/jcommune/users')]")).getText();
			elements.get(0).findElement(By.xpath("//div[@class='forum_answer_left']/a[contains(@href,'/jcommune/users')]")).click();
			Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + sender + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lUsername + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lFirstName + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastName + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastLogin + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lPostCount + "']")));
		}
	}


	@Test(priority = 5)
	@Parameters({"app-url"})
	public void checkBranchesListTest(String appUrl) {
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

	@Test(priority = 6)
	public void checkTopicsListInBranchTest() {
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

	@Test(priority = 7)
	@Parameters({"app-url"})
	public void viewProfileByClickUsernameInTopicTest(String appUrl) {
		CollectionHelp.getRandomWebElementFromCollection(topics).click();
		List<WebElement> elements = driver.findElements(By.xpath("//li[@class='forum_row']"));
		if (elements.size() == 0) {
			Assert.assertFalse(true);
		}
		else {
			String ownerPost = elements.get(0).findElement(By.xpath("//div[@class='forum_userinfo']/a[contains(@href,'/jcommune/users')]")).getText();
			elements.get(0).findElement(By.xpath("//div[@class='forum_userinfo']/a[contains(@href,'/jcommune/users')]")).click();
			Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + ownerPost + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lUsername + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lFirstName + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastName + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lLastLogin + "']")));
			Assert.assertNotNull(driver.findElement(By.xpath("//label[text()='" + lPostCount + "']")));
			logOut(appUrl);
		}

	}

}
