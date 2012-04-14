package org.jtalks.tests.jcommune.tests.topic;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;

/**
 * @author masyan
 */
public class JC32SecurityRegisteredUserToTopic {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		String branch = driver.getCurrentUrl();
		createTopicForTest();
		driver.get(branch);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);

	}

	@Test
	public void securityRegisteredUserToBranchTest() {

		//view the topics list
		List<WebElement> topics = topicPage.getTopicsList();
		assertionNotEmptyCollection(topics);

		//create topic
		assertionExistBySelector(driver, topicPage.newButtonSel);

	}
}
