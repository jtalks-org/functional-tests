package org.jtalks.tests.jcommune.testlink.branch;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.branchPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @author masyan
 */
public class JC28SecurityAnonymousToBranch {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
	}

	@Test
	public void securityAnonymousToBranchTest() {
		//view the branches list
		assertionNotEmptyCollection(branchPage.getBranchList());
	}
}
