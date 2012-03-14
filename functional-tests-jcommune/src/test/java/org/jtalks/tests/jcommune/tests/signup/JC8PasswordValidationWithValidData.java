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
 * This functional test covers test case JC8
 *
 * @autor masyan
 * @autor erik
 */
public class JC8PasswordValidationWithValidData {

	SignUpPage signUpPage;

	@DataProvider(name = "validPassword")
	public Object[][] validPassword() {
		String validPassword = StringHelp.getRandomString(10);
		String validPasswordWithBS = StringHelp.getRandomString(10) + " " + StringHelp.getRandomString(2);
		String validLongPass = StringHelp.getRandomString(20);
		String validPasswordWithBSInMiddle = StringHelp.getRandomString(2) + " " + StringHelp.getRandomString(1);
		return new Object[][]{
				{validPassword},
				{validPasswordWithBS},
				{validLongPass},
				{validPasswordWithBSInMiddle}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "validPassword")
	public void passwordValidationWithValidDataTest(String pass) {
		signUpPage.getPasswordField().clear();
		signUpPage.getPasswordField().sendKeys(pass);
		signUpPage.getSubmitButton().click();
		assertNotExistById(driver, signUpPage.passwordErrorMessageSel);
	}
}
