package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;

/**
 * @author erik
 */
public class JC25CreatingTopicValidateTitle {
	String shortTitle = "mssg";
	String validTitle = "new topic";

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
	public void testValidateTitle() {
		topicPage.getNewButton().click();
		topicPage.getSubjectField().sendKeys(shortTitle);
		topicPage.getPostButton().click();

		assertionExistBySelector(driver, TopicPage.subjectErrorMessageSel);

		topicPage.getSubjectField().clear();
		topicPage.getSubjectField().sendKeys(validTitle);
		topicPage.getPostButton().click();

		assertionNotExistBySelector(driver, TopicPage.subjectErrorMessageSel);


	}


}
