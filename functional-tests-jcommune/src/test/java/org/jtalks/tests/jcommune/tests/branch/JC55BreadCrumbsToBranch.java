package org.jtalks.tests.jcommune.tests.branch;

import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.SectionPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 */
public class JC55BreadCrumbsToBranch {

	SectionPage sectionPage;
	MainPage mainPage;

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		sectionPage = new SectionPage(driver);
		mainPage = new MainPage(driver);
	}

	@Test
	public void BreadCrumbsToBranchTest() {

		clickOnRandomBranch();

		//check breadcrumbs
		assertExistBySelector(driver, mainPage.breadCrumbsForumLinkSel);
		assertExistBySelector(driver, sectionPage.breadCrumbsSectionLinkSel);

	}
}
