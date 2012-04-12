package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.SectionPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomTopic;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC56BreadCrumbsToTopic {
	SectionPage sectionPage;
	MainPage mainPage;
	BranchPage branchPage;

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
		sectionPage = new SectionPage(driver);
		branchPage = new BranchPage(driver);
		mainPage = new MainPage(driver);
	}

	@Test
	public void BreadCrumbsToTopicTest() {
		clickOnRandomTopic();
		//check breadcrumbs
		assertExistBySelector(driver, mainPage.breadCrumbsForumLinkSel);
		assertExistBySelector(driver, sectionPage.breadCrumbsSectionLinkSel);
		assertExistBySelector(driver, branchPage.breadCrumbsBranchLinkSel);
	}
}
