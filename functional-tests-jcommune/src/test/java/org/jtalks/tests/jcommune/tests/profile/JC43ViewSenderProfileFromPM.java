package org.jtalks.tests.jcommune.tests.profile;


import org.jtalks.tests.jcommune.pages.PMPage;
import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.sendPrivateMessageForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC43ViewSenderProfileFromPM {
	ProfilePage profilePage;
	PMPage pmPage;


	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
	public void setupCase(String appUrl, String username, String password, String username2, String password2) {
		driver.get(appUrl);
		signIn(username2, password2);
		sendPrivateMessageForTest(username, StringHelp.getRandomString(10), StringHelp.getRandomString(10));
		logOut(appUrl);
		signIn(username, password);
		profilePage = new ProfilePage(driver);
		pmPage = new PMPage(driver);
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void viewSenderProfileFromPMTest() {
		pmPage.getPmInboxLink().click();
		profilePage.getProfileLinkFromPMInpoxPage().click();

		assertExistById(driver, profilePage.userDetailsFormSel);
	}
}
