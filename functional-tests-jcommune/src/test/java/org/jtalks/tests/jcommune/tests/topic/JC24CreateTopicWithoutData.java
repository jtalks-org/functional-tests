package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;

/**
 * Created by IntelliJ IDEA.
 * User: erik
 */
public class JC24CreateTopicWithoutData {

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
	public void createTopicWithoutData() throws InterruptedException {
		topicPage.getNewButton().click();
		topicPage.getPostButton().click();
		assertionExistBySelector(driver, TopicPage.subjectErrorMessageSel);
		assertionExistBySelector(driver, TopicPage.bodyErrorMessageSel);
	}
}
