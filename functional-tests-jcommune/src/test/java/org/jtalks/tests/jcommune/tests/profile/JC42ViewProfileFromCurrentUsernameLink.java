package org.jtalks.tests.jcommune.tests.profile;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @author masyan
 */
public class JC42ViewProfileFromCurrentUsernameLink {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void ViewProfileFromCurrentUsernameLinkTest() {
		profilePage.getCurrentUserLink().click();

		assertionExistBySelector(driver, profilePage.usernameTableFieldSel);
		assertionExistBySelector(driver, profilePage.firstNameTableFieldSel);
		assertionExistBySelector(driver, profilePage.lastNameTableFieldSel);
		assertionExistBySelector(driver, profilePage.emailTableFieldSel);
		assertionExistBySelector(driver, profilePage.languageTableFieldSel);
		assertionExistBySelector(driver, profilePage.pageSizeTableFieldSel);
		assertionExistBySelector(driver, profilePage.signatureTableFieldSel);
		assertionExistBySelector(driver, profilePage.locationTableFieldSel);
		assertionExistBySelector(driver, profilePage.lastLoginTableFieldSel);
		assertionExistBySelector(driver, profilePage.registrationDateTableFieldSel);
		assertionExistBySelector(driver, profilePage.postCountDateTableFieldSel);
	}
}
