package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotContainsInString;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertNotEquals;

/**
 * @autor masyan
 */
public class JC41TopicEditWithoutSaveChanges {

	TopicPage topicPage;
	PostPage postPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		topicPage = new TopicPage(driver);
		postPage = new PostPage(driver);
		postPage.getEditTopicButton().click();
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void editTopicWithoutSaveChangesTest() {
		String validTitle = StringHelp.getRandomString(10);
		String validMessage = StringHelp.getRandomString(10);
		topicPage.getSubjectField().clear();
		topicPage.getMessageField().clear();
		topicPage.getSubjectField().sendKeys(validTitle);
		topicPage.getMessageField().sendKeys(validMessage);
		topicPage.getBackButtonOnEditForm().click();

		assertNotEquals(topicPage.getTopicSubject().getText(), validTitle);

		assertNotContainsInString(topicPage.getTopicMessage().getText(), validMessage);
	}
}
