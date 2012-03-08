package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;


/**
 * @autor masyan
 */
public class JC38EditTopicWithNotValidTitle {

	TopicPage topicPage;
	PostPage postPage;

	@DataProvider(name = "notValidTitle")
	public Object[][] notValidTitle() {
		String shortTitle = StringHelp.getRandomString(4);
		String startSpTitle = " " + StringHelp.getRandomString(4);
		String endSpTitle = StringHelp.getRandomString(4) + " ";
		return new Object[][]{
				{shortTitle},
				{startSpTitle},
				{endSpTitle}

		};
	}

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

	@Test(dataProvider = "notValidTitle")
	public void editTopicWithNotValidTitleTest(String title) {
		topicPage.getSubjectField().clear();
		topicPage.getSubjectField().sendKeys(title);
		topicPage.getPostButton().click();

		assertExistBySelector(driver, topicPage.subjectErrorMessageSel);
	}

}
