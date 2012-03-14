package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * author: erik
 * Date: 08.03.12
 * Time: 11:43
 */
public class JC25CreatingTopicValidateTitle {
	TopicPage topicPage;
	String shortTitle = "mssg";
	String validTitle = "new topic";

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		topicPage = new TopicPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void testValidateTitle() {
		topicPage.getNewButton().click();
		topicPage.getSubjectField().sendKeys(shortTitle);
		topicPage.getPostButton().click();

		assertExistBySelector(driver, TopicPage.subjectErrorMessageSel);

		topicPage.getSubjectField().clear();
		topicPage.getSubjectField().sendKeys(validTitle);
		topicPage.getPostButton().click();

		assertNotExistBySelector(driver, TopicPage.subjectErrorMessageSel);


	}


}
