package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.pages.SignUpPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * This functional test covers test case JC5
 *
 * @autor masyan
 * @autor erik
 */
public class JC5EmailValidationWithNotValidData {

	SignUpPage signUpPage;

	@DataProvider(name = "notValidEmail")
	public Object[][] notValidEmail() {
		String notValidEmail = StringHelp.getRandomString(6);
		String emptyEmail = "";
		return new Object[][]{
				{notValidEmail}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "notValidEmail")
	public void emailValidationWithNotValidDataTest(String email) {
		signUpPage.getEmailField().clear();
		signUpPage.getEmailField().sendKeys(email);
		signUpPage.getSubmitButton().click();
		assertExistById(driver, signUpPage.emailErrorMessageSel);
	}
}
