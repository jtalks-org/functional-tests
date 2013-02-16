package org.jtalks.tests.jcommune.tests.lowlevel.topic;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotEmptyCollection;
import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;

/**
 * @author masyan
 */
public class JC29SecurityAnonymousToTopic {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		String branch = driver.getCurrentUrl();
		createTopicForTest();
		logOut(appUrl);
		driver.get(branch);
	}

	@Test
	public void securityAnonymousToBranchTest() {

		//view the topics list
		List<WebElement> topics = topicPage.getTopicsList();
		assertionNotEmptyCollection(topics);

		//create topic
		assertionNotExistBySelector(driver, topicPage.newButtonSel);

	}

}
