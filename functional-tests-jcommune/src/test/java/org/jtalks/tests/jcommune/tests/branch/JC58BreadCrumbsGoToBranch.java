package org.jtalks.tests.jcommune.tests.branch;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomTopic;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 */
public class JC58BreadCrumbsGoToBranch {
	BranchPage branchPage;
	TopicPage topicPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		clickOnRandomBranch();
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
