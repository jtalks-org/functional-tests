package org.jtalks.tests.jcommune.tests.section;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.branchPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.sectionPage;

/**
 * @author masyan
 */
public class JC54ViewBranchesListInSection {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
	}

	@Test
	public void viewBranchesListInSectionTest() {

		CollectionHelp.getRandomWebElementFromCollection(sectionPage.getSectionList()).click();
		assertionNotEmptyCollection(branchPage.getBranchList());
	}
}
