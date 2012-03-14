package org.jtalks.tests.jcommune.tests.branch;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 */
public class JC28SecurityAnonymousToBranch {

	BranchPage branchPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		branchPage = new BranchPage(driver);
	}

	@Test
	public void securityAnonymousToBranchTest() {
		//view the branches list
		assertNotEmptyCollection(branchPage.getBranchList());
	}
}
