package org.jtalks.tests.jcommune.tests.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC3
 *
 * @author masyan
 * @author erik
 */
public class JC3UsernameValidationWithNotValidData {


	@DataProvider(name = "notValidUsername")
	public Object[][] notValidUsername() {
		String longUsername = StringHelp.getRandomString(26);
		String startOfBS = " ";

		return new Object[][]{
				{longUsername},
				{startOfBS}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage.getSignUpButton().click();
	}


	@Test(dataProvider = "notValidUsername")
	public void usernameValidationWithNotValidDataTest(String username) {
		signUpPage.getUsernameField().clear();
		signUpPage.getUsernameField().sendKeys(username);
		signUpPage.getSubmitButton().click();
		assertionExistById(driver, signUpPage.usernameErrorMessageSel);
	}
}
