package org.jtalks.tests.jcommune.tests.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.validCaptchaValue;
import static org.testng.Assert.assertEquals;

/**
 * This functional test covers test case JC1
 *
 * @author masyan
 * @author erik
 */
public class JC1CheckingValidRegistration {
	String appUrl;

	@DataProvider(name = "validRegistration")
	public Object[][] validRegistration() {
		String validUsername = StringHelp.getRandomString(10);
		String validEmail = StringHelp.getRandomEmail();
		String validPassword = StringHelp.getRandomString(10);
		return new Object[][]{
				{validUsername, validEmail, validPassword, validPassword}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		this.appUrl = appUrl;
		driver.get(appUrl);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "validRegistration")
	public void validRegistrationTest(String username, String email, String pass, String confirmPass) {
		signUpPage.getUsernameField().sendKeys(username);
		signUpPage.getEmailField().sendKeys(email);
		signUpPage.getPasswordField().sendKeys(pass);
		signUpPage.getPasswordConfirmField().sendKeys(confirmPass);
		signUpPage.getCaptchaField().sendKeys(validCaptchaValue);
		signUpPage.getSubmitButton().click();
		assertEquals(driver.getCurrentUrl(), appUrl);
	}
}