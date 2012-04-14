package org.jtalks.tests.jcommune.tests.topic;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.postPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;

/**
 * @author masyan
 */
public class JC40EditTopicWithNotValidMessage {

	@DataProvider(name = "notValidMessage")
	public Object[][] notValidMessage() {
		String shortMsg = StringHelp.getRandomString(1);
		String startSpMsg = " " + StringHelp.getRandomString(1);
		String endSpMsg = StringHelp.getRandomString(1) + " ";
		String longMsg = StringHelp.getRandomString(20001);
		return new Object[][]{
				{shortMsg},
				{startSpMsg},
				{endSpMsg},
				{longMsg}

		};
	}

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

	@Test(dataProvider = "notValidMessage")
	public void editTopicWithNotValidMessageTest(String msg) {

		topicPage.getMessageField().clear();
		StringHelp.setLongTextValue(driver, topicPage.getMessageField(), msg);
		topicPage.getPostButton().click();

		assertionExistBySelector(driver, topicPage.bodyErrorMessageSel);
	}

}
