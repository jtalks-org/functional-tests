package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import java.util.List;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;

/**
 * @autor masyan
 * @autor erik
 */
public class JC14BackButtonOnCreateTopicPage {

	String subject = StringHelp.getRandomString(20);
	String message = StringHelp.getRandomString(20);
	TopicPage topicPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		topicPage = new TopicPage();
		topicPage.init(driver);
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void clickBackButtonOnCreateTopicPageTest() {
		//first step
		topicPage.getNewButton().click();
		Assert.assertTrue(topicPage.getSubjectField().isDisplayed());
		Assert.assertTrue(topicPage.getMessageField().isDisplayed());

		//second step
		topicPage.getSubjectField().sendKeys(subject);
		topicPage.getMessageField().sendKeys(message);
		topicPage.getBackButton().click();

		assertNotExistElementOnViewPresent(topicPage.getTopicsList(), subject);
	}
}
