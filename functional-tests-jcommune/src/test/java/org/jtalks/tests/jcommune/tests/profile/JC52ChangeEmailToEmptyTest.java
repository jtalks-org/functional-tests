package org.jtalks.tests.jcommune.tests.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * author: erik
 * Date: 09.03.12
 * Time: 20:34
 */
public class JC52ChangeEmailToEmptyTest {
	ProfilePage profilePage;
	String currentEmail;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		profilePage = new ProfilePage(driver);
		profilePage.getCurrentUserLink().click();
		currentEmail = profilePage.getEmail().getText();
		profilePage.getEditProfileButton().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void testEmptyEmail() {
		//TODO uncomment after X servers will be installed
//		profilePage.getEmailEditField().clear();
//		profilePage.getBackButton().click();
//		assertEquals(profilePage.getEmail().getText(), currentEmail);
	}
}
