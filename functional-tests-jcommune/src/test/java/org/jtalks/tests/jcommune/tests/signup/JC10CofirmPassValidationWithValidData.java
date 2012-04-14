package org.jtalks.tests.jcommune.tests.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC10
 *
 * @author masyan
 * @author erik
 */
public class JC10CofirmPassValidationWithValidData {

	@DataProvider(name = "validConfirmPassword")
	public Object[][] validConfirmPassword() {
		String validPassword = StringHelp.getRandomString(10);
		return new Object[][]{
				{validPassword, validPassword}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "validConfirmPassword")
	public void confirmPasswordValidationWithValidDataTest(String pass, String confirm) throws InterruptedException {
		signUpPage.getPasswordField().clear();
		signUpPage.getPasswordField().sendKeys(pass);

		signUpPage.getPasswordConfirmField().clear();
		signUpPage.getPasswordConfirmField().sendKeys(confirm);

		signUpPage.getSubmitButton().click();
		assertionNotExistById(driver, signUpPage.passwordConfirmErrorMessageSel);
	}


}
