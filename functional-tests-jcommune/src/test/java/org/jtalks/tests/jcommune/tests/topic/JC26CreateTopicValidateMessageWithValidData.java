package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;


/**
 * author: erik
 * Date: 08.03.12
 * Time: 12:06
 */
public class JC26CreateTopicValidateMessageWithValidData {
	TopicPage topicPage;

	@DataProvider(name = "validTopic")
	public Object[][] validTopic() {
		String validTitle = "new topic";
		String validTopicMessage = StringHelp.getRandomString(20000);
		return new Object[][]{
				{validTitle, validTopicMessage}
		};
	}

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		topicPage = new TopicPage(driver);

	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test(dataProvider = "validTopic")
	public void testValidateMassage(String validTitle, String validTopicMessage) {
		topicPage.getNewButton().click();
		topicPage.getSubjectField().sendKeys(validTitle);
		topicPage.getPostButton().click();

		assertNotExistBySelector(driver, TopicPage.subjectErrorMessageSel);
		assertExistBySelector(driver, TopicPage.bodyErrorMessageSel);

		topicPage.getMessageField().clear();
		StringHelp.setLongTextValue(driver, topicPage.getMessageField(), validTopicMessage);
		topicPage.getPostButton().click();

		Assert.assertEquals(topicPage.getTopicSubject().getText(), validTitle);
		Assert.assertEquals(topicPage.getTopicMessage().getText(), validTopicMessage);
	}

}
