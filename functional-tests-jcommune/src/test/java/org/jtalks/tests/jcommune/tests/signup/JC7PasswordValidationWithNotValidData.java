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
 * This functional test covers test case JC7
 *
 * @autor masyan
 * @autor erik
 */
public class JC7PasswordValidationWithNotValidData {

	SignUpPage signUpPage;

	@DataProvider(name = "notValidPassword")
	public Object[][] notValidPassword() {
		String shortPassword = StringHelp.getRandomString(2);
		String longPassword = StringHelp.getRandomString(21);
		String emptyPassword = "";

		return new Object[][]{
				{shortPassword},
				{longPassword},
				{emptyPassword}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "notValidPassword")
	public void passwordValidationWithNotValidDataTest(String pass) {
		signUpPage.getPasswordField().clear();
		signUpPage.getPasswordField().sendKeys(pass);
		signUpPage.getSubmitButton().click();
		assertExistById(driver, signUpPage.passwordErrorMessageSel);
	}
}
