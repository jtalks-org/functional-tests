package org.jtalks.tests.jcommune.tests.topic;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotContainsInString;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.postPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;
import static org.testng.Assert.assertNotEquals;

/**
 * @author masyan
 */
public class JC41TopicEditWithoutSaveChanges {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		postPage.getEditTopicButton().click();
	}

	@AfterMethod(alwaysRun = true)
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

		assertionNotContainsInString(topicPage.getTopicMessage().getText(), validMessage);
	}
}
