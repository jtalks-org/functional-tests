package org.jtalks.tests.jcommune.tests.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC8
 *
 * @author masyan
 * @author erik
 */
public class JC8PasswordValidationWithValidData {

	@DataProvider(name = "validPassword")
	public Object[][] validPassword() {
		String validPassword = StringHelp.getRandomString(10);
		String validPasswordWithBS = StringHelp.getRandomString(10) + " " + StringHelp.getRandomString(2);
		String validLongPass = StringHelp.getRandomString(50);
		String validShortPass = StringHelp.getRandomString(1);
		return new Object[][]{
				{validPassword},
				{validPasswordWithBS},
				{validLongPass},
				{validShortPass}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "validPassword")
	public void passwordValidationWithValidDataTest(String pass) {
		signUpPage.getPasswordField().clear();
		signUpPage.getPasswordField().sendKeys(pass);
		signUpPage.getSubmitButton().click();
		assertionNotExistById(driver, signUpPage.passwordErrorMessageSel);
	}
}
