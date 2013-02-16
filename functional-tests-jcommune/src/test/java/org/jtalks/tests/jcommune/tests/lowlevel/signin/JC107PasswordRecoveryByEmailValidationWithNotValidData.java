package org.jtalks.tests.jcommune.tests.lowlevel.signin;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signInPage;

/**
 * @autor masyan
 */
public class JC107PasswordRecoveryByEmailValidationWithNotValidData {
	@DataProvider(name = "notValidEmailToRecovery")
	public Object[][] notValidEmail() {
		String notValidEmail = "novalid@email";
		String notExistEmail = "prefix" + StringHelp.getRandomEmail();
		return new Object[][]{
				{notValidEmail},
				{notExistEmail}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		mainPage.getLoginLink().click();
	}

	@Test(dataProvider = "notValidEmailToRecovery")
	public void passwordRecoveryByEmailValidationWithNotValidDataTest(String email) {
		signInPage.getRestorePasswordLink().click();
		signInPage.getEmailFieldToRestore().sendKeys(email);
		signInPage.getSendButtonToRestore().click();
		assertionExistById(driver, signInPage.notValidEmailErrorMessageSel);
	}
}
