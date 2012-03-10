package org.jtalks.tests.jcommune.tests.section;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.SectionPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 */
public class JC54ViewBranchesListInSection {

	SectionPage sectionPage;
	BranchPage branchPage;

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		sectionPage = new SectionPage(driver);
		branchPage = new BranchPage(driver);
	}

	@Test
	public void viewBranchesListInSectionTest() {

		CollectionHelp.getRandomWebElementFromCollection(sectionPage.getSectionList()).click();
		assertNotEmptyCollection(branchPage.getBranchList());
	}
}
