package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * @autor masyan
 */
public class JC62TopicPositionWithSaveChanges {
	TopicPage topicPage;
	PostPage postPage;
	String editedSubject;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		topicPage = new TopicPage(driver);
		postPage = new PostPage(driver);
		signIn(username, password);
		clickOnRandomBranch();
		//create 2 topics
		createTopicForTest();
		topicPage.getBackButton().click();
		createTopicForTest();
		topicPage.getBackButton().click();

		CollectionHelp.getLastWebElementFromCollection(topicPage.getTopicsList()).click();

	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void topicPositionWithSaveChangesTest() {
		//first step
		editedSubject = topicPage.getTopicSubject().getText();
		postPage.getEditTopicButton().click();
		topicPage.getMessageField().clear();
		topicPage.getMessageField().sendKeys(StringHelp.getRandomString(10));
		topicPage.getPostButton().click();
		topicPage.getBackButton().click();

		//second step
		CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicsList()).click();
		assertEquals(topicPage.getTopicSubject().getText(), editedSubject);
	}
}
