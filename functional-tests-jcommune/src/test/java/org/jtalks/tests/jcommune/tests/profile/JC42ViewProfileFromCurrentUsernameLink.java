package org.jtalks.tests.jcommune.tests.profile;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
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

		assertElementExistsBySelector(driver, profilePage.usernameTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.firstNameTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.lastNameTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.emailTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.languageTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.pageSizeTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.signatureTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.locationTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.lastLoginTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.registrationDateTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.postCountDateTableFieldSel);
	}
}
