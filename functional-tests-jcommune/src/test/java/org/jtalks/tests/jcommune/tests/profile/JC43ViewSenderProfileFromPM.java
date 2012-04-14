package org.jtalks.tests.jcommune.tests.profile;


import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.pmPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.sendPrivateMessageForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @author masyan
 */
public class JC43ViewSenderProfileFromPM {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
	public void setupCase(String appUrl, String username, String password, String username2, String password2) {
		driver.get(appUrl);
		signIn(username2, password2);
		sendPrivateMessageForTest(username, StringHelp.getRandomString(10), StringHelp.getRandomString(10));
		logOut(appUrl);
		signIn(username, password);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void viewSenderProfileFromPMTest() {
		pmPage.getPmInboxLink().click();
		profilePage.getProfileLinkFromPMInpoxPage().click();

		assertionExistById(driver, profilePage.userDetailsFormSel);
	}
}
