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
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 */
public class JC56BreadCrumbsToTopic {
	SectionPage sectionPage;
	MainPage mainPage;
	BranchPage branchPage;

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		clickOnRandomBranch();
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
