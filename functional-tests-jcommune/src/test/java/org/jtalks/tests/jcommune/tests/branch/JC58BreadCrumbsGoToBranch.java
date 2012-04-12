package org.jtalks.tests.jcommune.tests.branch;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomTopic;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC58BreadCrumbsGoToBranch {
	BranchPage branchPage;
	TopicPage topicPage;

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
		clickOnRandomTopic();
		branchPage = new BranchPage(driver);
		topicPage = new TopicPage(driver);
	}

	@Test
	public void BreadCrumbsGoToBranchTest() {

		branchPage.getBreadCrumbsBranchLink().click();

		assertNotEmptyCollection(topicPage.getTopicsList());
	}
}
