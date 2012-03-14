package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
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
public class JC37EditTopicWithValidTitle {

	TopicPage topicPage;
	PostPage postPage;

	@BeforeMethod(alwaysRun = true)
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

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void editTopicWithValidTitleTest() {
		String newTitle = StringHelp.getRandomString(10);
		topicPage.getSubjectField().clear();
		topicPage.getSubjectField().sendKeys(newTitle);
		topicPage.getPostButton().click();

		assertEquals(topicPage.getTopicSubject().getText(), newTitle);
	}
}
