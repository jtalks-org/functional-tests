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
 * This functional test covers test case JC3
 *
 * @autor masyan
 * @autor erik
 */
public class JC3UsernameValidationWithNotValidData {

	SignUpPage signUpPage;

	@DataProvider(name = "notValidUsername")
	public Object[][] notValidUsername() {
		String shortUsername = StringHelp.getRandomString(1);
		String longUsername = StringHelp.getRandomString(26);
		String startOfBS = " " + StringHelp.getRandomString(1);
		String endOfBS = StringHelp.getRandomString(1) + " ";
		String onlyBS = "  ";

		return new Object[][]{
				{shortUsername},
				{longUsername},
				{startOfBS},
				{endOfBS},
				{onlyBS},
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}


	@Test(dataProvider = "notValidUsername")
	public void usernameValidationWithNotValidDataTest(String username) {
		signUpPage.getUsernameField().clear();
		signUpPage.getUsernameField().sendKeys(username);
		signUpPage.getSubmitButton().click();
		assertExistById(driver, signUpPage.usernameErrorMessageSel);
	}
}
