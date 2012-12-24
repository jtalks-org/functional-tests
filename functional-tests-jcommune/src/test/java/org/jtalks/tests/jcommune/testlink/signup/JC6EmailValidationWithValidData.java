package org.jtalks.tests.jcommune.testlink.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC6
 *
 * @author masyan
 * @author erik
 */
public class JC6EmailValidationWithValidData {

	@DataProvider(name = "validEmail")
	public Object[][] validEmail() {
		String validEmail = StringHelp.getRandomEmail();
		return new Object[][]{
				{validEmail}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage.getSignUpButton().click();
	}


	@Test(dataProvider = "validEmail")
	public void emailValidationWithValidDataTest(String email) {
		signUpPage.getEmailField().clear();
		signUpPage.getEmailField().sendKeys(email);
		signUpPage.getSubmitButton().click();
		assertionNotExistById(driver, signUpPage.emailErrorMessageSel);
	}
}
