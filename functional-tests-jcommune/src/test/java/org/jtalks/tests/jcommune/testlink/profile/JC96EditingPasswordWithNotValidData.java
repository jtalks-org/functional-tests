package org.jtalks.tests.jcommune.testlink.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * author: erik
 * Date: 09.03.12
 * Time: 13:56
 */
public class JC96EditingPasswordWithNotValidData {
	String currentPassword;
	String wrongPassword;
	String newPassword;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {

		currentPassword = password;
		wrongPassword = "wrongpassword";
		newPassword = "new4";

		driver.get(appUrl);
		signIn(username, password);
		profilePage.getCurrentUserLink().click();
		profilePage.getEditProfileButton().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		profilePage.getCurrentUserLink().click();
		profilePage.getEditProfileButton().click();
		profilePage.getCurrentPasswordField().sendKeys(newPassword);
		profilePage.getNewPasswordField().sendKeys(currentPassword);
		profilePage.getConfirmNewPasswordField().sendKeys(currentPassword);
		profilePage.getSaveEditButton().click();
		logOut(appUrl);
	}

	@Test
	public void testEditEmail() {
		profilePage.getNewPasswordField().sendKeys(newPassword);
		profilePage.getConfirmNewPasswordField().sendKeys(newPassword);
		profilePage.getSaveEditButton().click();

		assertionExistById(driver, ProfilePage.errorCurrentUserPasswordMessageIdSel);

		profilePage.getCurrentPasswordField().sendKeys(wrongPassword);
		profilePage.getSaveEditButton().click();

		assertionExistById(driver, ProfilePage.errorCurrentUserPasswordMessageIdSel);

	}

}
