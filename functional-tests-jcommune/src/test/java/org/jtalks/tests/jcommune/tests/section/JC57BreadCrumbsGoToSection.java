package org.jtalks.tests.jcommune.tests.section;

import org.jtalks.tests.jcommune.pages.BranchPage;
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
public class JC57BreadCrumbsGoToSection {
	SectionPage sectionPage;
	BranchPage branchPage;

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		clickOnRandomBranch();
		clickOnRandomTopic();
		sectionPage = new SectionPage(driver);
		branchPage = new BranchPage(driver);
	}

	@Test
	public void BreadCrumbsGoToSectionTest() {

		sectionPage.getBreadCrumbsSectionLink().click();

		assertNotEmptyCollection(branchPage.getBranchList());
	}
}
