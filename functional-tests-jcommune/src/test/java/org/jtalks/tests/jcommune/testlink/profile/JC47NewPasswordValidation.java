package org.jtalks.tests.jcommune.testlink.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionExistById;
import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;


/**
 * @author: erik
 * Date: 09.03.12
 * Time: 15:32
 */
public class JC47NewPasswordValidation {

	String currentPassword;
	String tooLongPassword;
	String newPassword;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {

		currentPassword = password;
		tooLongPassword = StringHelp.getRandomString(51);
		newPassword = "1";

		driver.get(appUrl);
		signIn(username, password);
		profilePage.getCurrentUserLink().click();
		profilePage.getEditProfileButton().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void newPasswordValidationTest() {
		profilePage.getNewPasswordField().clear();
		profilePage.getNewPasswordField().sendKeys(tooLongPassword);
		profilePage.getSaveEditButton().click();
		assertionExistById(driver, ProfilePage.errorNewUserPasswordMessageIdSel);
		assertionExistById(driver, ProfilePage.errorNewUserPasswordConfirmMessageIdSel);

		profilePage.getNewPasswordField().clear();
		profilePage.getNewPasswordField().sendKeys("NewPass");
		profilePage.getConfirmNewPasswordField().sendKeys("newPass");
		profilePage.getSaveEditButton().click();
		assertionExistById(driver, ProfilePage.errorNewUserPasswordConfirmMessageIdSel);


		profilePage.getNewPasswordField().clear();
		profilePage.getConfirmNewPasswordField().clear();
		profilePage.getNewPasswordField().sendKeys(newPassword);
		profilePage.getConfirmNewPasswordField().sendKeys(newPassword);
		profilePage.getSaveEditButton().click();
		assertionNotExistById(driver, ProfilePage.errorNewUserPasswordConfirmMessageIdSel);


	}

}
