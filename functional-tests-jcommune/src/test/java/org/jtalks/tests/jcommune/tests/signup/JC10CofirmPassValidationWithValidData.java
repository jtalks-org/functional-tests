package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.pages.SignUpPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * This functional test covers test case JC10
 *
 * @autor masyan
 * @autor erik
 */
public class JC10CofirmPassValidationWithValidData {

	SignUpPage signUpPage;

	@DataProvider(name = "validConfirmPassword")
	public Object[][] validConfirmPassword() {
		String validPassword = StringHelp.getRandomString(10);
		return new Object[][]{
				{validPassword, validPassword}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "validConfirmPassword")
	public void confirmPasswordValidationWithValidDataTest(String pass, String confirm) throws InterruptedException {
		signUpPage.getPasswordField().clear();
		signUpPage.getPasswordField().sendKeys(pass);

		signUpPage.getPasswordConfirmField().clear();
		signUpPage.getPasswordConfirmField().sendKeys(confirm);

		signUpPage.getSubmitButton().click();
		assertNotExistById(driver, signUpPage.passwordConfirmErrorMessageSel);
	}


}
