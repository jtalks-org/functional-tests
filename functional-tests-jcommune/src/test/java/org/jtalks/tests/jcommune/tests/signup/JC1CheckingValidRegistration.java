package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.pages.SignUpPage;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.testng.Assert.assertEquals;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.getApplicationContextPath;

/**
 * This functional test covers test case JC1
 *
 * @autor masyan
 * @autor erik
 */
public class JC1CheckingValidRegistration {
	String appUrl;

	SignUpPage signUpPage;

	@DataProvider(name = "validRegistration")
	public Object[][] validRegistration() {
		String validUsername = StringHelp.getRandomString(10);
		String validEmail = StringHelp.getRandomEmail();
		String validPassword = StringHelp.getRandomString(10);
		return new Object[][]{
				{validUsername, validEmail, validPassword, validPassword}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		this.appUrl = appUrl;
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "validRegistration")
	public void validRegistrationTest(String username, String email, String pass, String confirmPass) {
		signUpPage.getUsernameField().sendKeys(username);
		signUpPage.getEmailField().sendKeys(email);
		signUpPage.getPasswordField().sendKeys(pass);
		signUpPage.getPasswordConfirmField().sendKeys(confirmPass);
		signUpPage.getSubmitButton().click();
		assertEquals(driver.getCurrentUrl(), appUrl);
	}
}