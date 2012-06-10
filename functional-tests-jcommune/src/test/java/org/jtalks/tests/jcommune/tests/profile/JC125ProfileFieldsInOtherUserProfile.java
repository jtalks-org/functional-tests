package org.jtalks.tests.jcommune.tests.profile;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC125ProfileFieldsInOtherUserProfile {
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
	@Parameters({"uUsername2"})
	public void ViewProfileFromCurrentUsernameLinkTest(String usernameSecond) {
		driver.get(driver.getCurrentUrl() + "/users/" + usernameSecond);

		assertionExistBySelector(driver, profilePage.firstNameTableFieldSel);
		assertionExistBySelector(driver, profilePage.lastNameTableFieldSel);
		assertionExistBySelector(driver, profilePage.locationTableFieldSel);
		assertionExistBySelector(driver, profilePage.lastLoginTableFieldSel);
		assertionExistBySelector(driver, profilePage.registrationDateTableFieldSel);
		assertionExistBySelector(driver, profilePage.postCountDateTableFieldSel);
		assertionExistBySelector(driver, profilePage.signatureTableFieldSel);

		assertionNotExistBySelector(driver, profilePage.emailTableFieldSel);
		assertionNotExistBySelector(driver, profilePage.languageTableFieldSel);
		assertionNotExistBySelector(driver, profilePage.pageSizeTableFieldSel);
	}
}
