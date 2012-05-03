package org.jtalks.tests.jcommune.tests.signin;

/**
 * @autor masyan
 */
public class JC108PasswordRecoveryByEmailValidationWithValidData {
	//TODO not visible current user link with HTMLUnit driver
//	@BeforeMethod(alwaysRun = true)
//	@Parameters({"app-url"})
//	public void setupCase(String appUrl) {
//		driver.get(appUrl);
//		mainPage.getLoginLink().click();
//	}
//
//	@AfterMethod(alwaysRun = true)
//	@Parameters({"app-url"})
//	public void destroy(String appUrl) {
//		logOut(appUrl);
//	}
//
//	@Test
//	@Parameters({"app-url", "uUsername4", "uEmail4", "pEmail4"})
//	public void passwordRecoveryByEmailValidationWithValidDataTest(String appUrl, String username, String email, String emailPasswd) {
//		signInPage.getRestorePasswordLink().click();
//		signInPage.getEmailFieldToRestore().sendKeys(email);
//		signInPage.getSendButtonToRestore().click();
//		mailServer.goToMailServer();
//		mailServer.signIn(email, emailPasswd);
//		mailServer.openFirstMessage();
//		//find new password in message
//		String newPasswd = mailServer.getPasswdFromMessage();
//		driver.get(appUrl);
//		signIn(username, newPasswd);
//		assertionExistBySelector(driver, mainPage.currentUsernameLinkSel);
//	}
}
