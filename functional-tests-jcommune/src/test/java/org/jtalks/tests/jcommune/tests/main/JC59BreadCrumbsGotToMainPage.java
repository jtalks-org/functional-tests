package org.jtalks.tests.jcommune.tests.main;

import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.SectionPage;
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
public class JC59BreadCrumbsGotToMainPage {
	MainPage mainPage;
	SectionPage sectionPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		clickOnRandomBranch();
		clickOnRandomTopic();
		mainPage = new MainPage(driver);
		sectionPage = new SectionPage(driver);
	}

	@Test
	public void BreadCrumbsGoToBranchTest() {

		mainPage.getBreadCrumbsForumLink().click();

		assertNotEmptyCollection(sectionPage.getSectionList());
	}
}
