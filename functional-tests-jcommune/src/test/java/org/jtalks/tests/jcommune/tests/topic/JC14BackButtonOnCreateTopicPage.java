package org.jtalks.tests.jcommune.tests.topic;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotExistElementOnViewPresent;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;
import static org.testng.Assert.assertTrue;

/**
 * @author masyan
 * @author erik
 */
public class JC14BackButtonOnCreateTopicPage {
	String subject = StringHelp.getRandomString(20);
	String message = StringHelp.getRandomString(20);

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void clickBackButtonOnCreateTopicPageTest() {
		//first step
		topicPage.getNewButton().click();
		assertTrue(topicPage.getSubjectField().isDisplayed());
		assertTrue(topicPage.getMessageField().isDisplayed());

		//second step
		topicPage.getSubjectField().sendKeys(subject);
		topicPage.getMessageField().sendKeys(message);
		topicPage.getBackButtonOnEditForm().click();

		assertionNotExistElementOnViewPresent(topicPage.getTopicsList(), subject);
	}
}
