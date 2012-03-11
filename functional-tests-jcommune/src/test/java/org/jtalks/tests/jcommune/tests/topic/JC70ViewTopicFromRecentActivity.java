package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * @autor masyan
 */
public class JC70ViewTopicFromRecentActivity {
	TopicPage topicPage;
	MainPage mainPage;

	String createdTopicTitle;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		topicPage = new TopicPage(driver);
		mainPage = new MainPage(driver);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		createdTopicTitle = topicPage.getTopicSubject().getText();
		logOut(appUrl);
		mainPage.getRecentActivityLink().click();
	}


	@Test
	public void recentActivityTest() {
		CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicLinksFromRecentActivity()).click();

		assertEquals(topicPage.getTopicSubject().getText(), createdTopicTitle);
	}
}
