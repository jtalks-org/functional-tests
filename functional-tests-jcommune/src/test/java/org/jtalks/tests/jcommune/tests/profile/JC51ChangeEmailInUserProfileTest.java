package org.jtalks.tests.jcommune.tests.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * author: erik
 * Date: 09.03.12
 * Time: 20:24
 */
public class JC51ChangeEmailInUserProfileTest {

	ProfilePage profilePage;
	String uniqEmail;
	String currentEmail;


	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uEmail"})
	public void setupCase(String appUrl, String username, String password, String uEmail) {
		driver.get(appUrl);
		uniqEmail = StringHelp.getRandomEmail();
		currentEmail = uEmail;
		signIn(username, password);
		profilePage = new ProfilePage(driver);
		profilePage.getCurrentUserLink().click();
		profilePage.getEditProfileButton().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		profilePage.getCurrentUserLink().click();
		profilePage.getEditProfileButton().click();
		profilePage.getEmailEditField().clear();
		profilePage.getEmailEditField().sendKeys(currentEmail);
		profilePage.getSaveEditButton().click();
		logOut(appUrl);
	}

	@Test
	public void testChangingEmail() {
		profilePage.getEmailEditField().clear();
		profilePage.getEmailEditField().sendKeys(uniqEmail);
		profilePage.getSaveEditButton().click();
		Assert.assertEquals(profilePage.getEmail().getText(), uniqEmail);
	}
}
