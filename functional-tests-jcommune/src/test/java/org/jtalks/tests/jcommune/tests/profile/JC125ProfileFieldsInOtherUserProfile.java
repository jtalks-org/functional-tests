package org.jtalks.tests.jcommune.tests.profile;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC125ProfileFieldsInOtherUserProfile {
	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
	public void setupCase(String appUrl, String username, String password, String username2, String password2) {
		driver.get(appUrl);
		signIn(username2, password2);
		clickOnRandomBranch();
		createTopicForTest();
		logOut(appUrl);
		signIn(username, password);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	@Parameters({"uUsername2"})
	public void viewProfileFromCurrentUsernameLinkTest(String usernameSecond) {
		CollectionHelp.findWebElementByValueInCollection(profilePage.getPmLinksFromLastColumn(), usernameSecond).click();

		assertElementExistsBySelector(driver, profilePage.firstNameTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.lastNameTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.locationTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.lastLoginTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.registrationDateTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.postCountDateTableFieldSel);
		assertElementExistsBySelector(driver, profilePage.signatureTableFieldSel);

		assertionNotExistBySelector(driver, profilePage.emailTableFieldSel);
		assertionNotExistBySelector(driver, profilePage.languageTableFieldSel);
		assertionNotExistBySelector(driver, profilePage.pageSizeTableFieldSel);
	}
}
