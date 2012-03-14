package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistElementOnViewPresent;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 * @autor erik
 */
public class JC14BackButtonOnCreateTopicPage {

	String subject = StringHelp.getRandomString(20);
	String message = StringHelp.getRandomString(20);
	TopicPage topicPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		topicPage = new TopicPage(driver);
	}

	@AfterMethod(alwaysRun = true)
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
