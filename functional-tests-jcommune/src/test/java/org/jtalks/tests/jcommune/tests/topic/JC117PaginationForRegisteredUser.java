package org.jtalks.tests.jcommune.tests.topic;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomSection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createPostsForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.postPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;


/**
 * author: erik
 */
public class JC117PaginationForRegisteredUser {
	String value;
	String postLink;
	Boolean wasValueChanged = false;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setUp(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		mainPage.getProfileLink().click();

		//here we will change count of posts for user in profile if it not equals 5
		if (!driver.findElement(By.xpath("//li[@class='forum_row' and label='Page size']/span")).getText().equals("5")) {
			wasValueChanged = true;
			profilePage.getEditProfileButton().click();
			value = driver.findElement(By.xpath("//li[@class='forum_row' and label= 'Page size']//select")).getAttribute("value");
			Select select = new Select(driver.findElement(By.xpath("//li[@class='forum_row' and label= 'Page size']//select")));
			select.selectByValue("5");
			profilePage.getSaveEditButton().click();
		}

		driver.get(appUrl);
		clickOnRandomSection();
		clickOnRandomBranch();
		createTopicForTest();
		postLink = driver.getCurrentUrl();
		createPostsForTest(5, 50);
	}

	@AfterMethod
	@Parameters({"app-url", "uUsername"})
	public void back(String appUrl, String username) {

		//here we will change count of posts for user in profile how it was before test
		if (wasValueChanged) {
			driver.get(appUrl);
			mainPage.getProfileLink().click();
			profilePage.getEditProfileButton().click();
			Select select = new Select(driver.findElement(By.xpath("//li[@class='forum_row' and label= 'Page size']//select")));
			select.selectByValue(value);
			profilePage.getSaveEditButton().click();
		}

		logOut(appUrl);
	}

	@Test
	public void testPagination() {
		driver.get(postLink);
		if (postPage.getPostsMessages().size() != 5) {
			Assert.fail("Post page contains count of posts that not equals 5");
		}
		driver.findElement(By.xpath("//a[@class='page']")).click();
		if (postPage.getPostsMessages().size() != 1) {
			Assert.fail("Post page contains count of posts that not equals 1");
		}
	}
}
