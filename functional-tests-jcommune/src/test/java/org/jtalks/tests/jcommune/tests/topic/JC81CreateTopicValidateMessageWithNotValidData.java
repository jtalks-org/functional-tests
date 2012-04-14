package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;
import static org.testng.Assert.assertEquals;

/**
 * @author erik
 */
public class JC81CreateTopicValidateMessageWithNotValidData {

	@DataProvider(name = "notValidTopic")
	public Object[][] validTopic() {
		String newTopic = "new topic";
		String shortMessage = "m";
		String longTopicMessage = StringHelp.getRandomString(20001);
		return new Object[][]{
				{newTopic, shortMessage, longTopicMessage}
		};
	}

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);

	}

	@Test(dataProvider = "notValidTopic")
	public void testValidateMassage(String newTopic, String shortMessage, String longTopicMessage) {
		topicPage.getNewButton().click();
		topicPage.getSubjectField().sendKeys(newTopic);
		topicPage.getMessageField().sendKeys(shortMessage);
		topicPage.getPostButton().click();

		assertionExistBySelector(driver, TopicPage.bodyErrorMessageSel);

		topicPage.getMessageField().clear();
		StringHelp.setLongTextValue(driver, topicPage.getMessageField(), longTopicMessage);
		topicPage.getPostButton().click();

		assertionExistBySelector(driver, TopicPage.bodyErrorMessageSel);
		assertEquals(topicPage.getMessageField().getText().trim(), longTopicMessage);

	}
}
