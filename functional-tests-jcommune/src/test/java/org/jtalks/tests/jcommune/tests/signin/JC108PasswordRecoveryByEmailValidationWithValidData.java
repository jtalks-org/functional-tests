package org.jtalks.tests.jcommune.tests.signin;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mailServer;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signInPage;

/**
 * @autor masyan
 */
public class JC108PasswordRecoveryByEmailValidationWithValidData {
	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		mainPage.getLoginLink().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	@Parameters({"app-url", "uUsername4", "uEmail4", "pEmail4"})
	public void passwordRecoveryByEmailValidationWithValidDataTest(String appUrl, String username, String email, String emailPasswd) {
		signInPage.getRestorePasswordLink().click();
		signInPage.getEmailFieldToRestore().sendKeys(email);
		signInPage.getSendButtonToRestore().click();
		mailServer.goToMailServer();
		mailServer.signIn(email, emailPasswd);
		mailServer.openFirstMessage();
		//find new password in message
		String newPasswd = mailServer.getPasswdFromMessage();
		driver.get(appUrl);
		signIn(username, newPasswd);
		assertionExistBySelector(driver, mainPage.currentUsernameLinkSel);
	}
}
