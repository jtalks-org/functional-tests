package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertContainsInString;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;


/**
 * @autor masyan
 */
public class JC39EditTopicWithValidMessage {

	TopicPage topicPage;
	PostPage postPage;

	@DataProvider(name = "validMessage")
	public Object[][] validMessage() {
		String validMessage = StringHelp.getRandomString(10);
		String maxMessage = StringHelp.getRandomString(20000);

		return new Object[][]{
				{validMessage},
				{maxMessage}
		};
	}

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

	@Test(dataProvider = "validMessage")
	public void editTopicWithValidMessageTest(String msg) {
		topicPage.getMessageField().clear();
		StringHelp.setLongTextValue(driver, topicPage.getMessageField(), msg);
		topicPage.getPostButton().click();

		assertContainsInString(topicPage.getTopicMessage().getText(), msg);

		assertContainsInString(topicPage.getTopicMessage().getText(), "edited");
	}
}
