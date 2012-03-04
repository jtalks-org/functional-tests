package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.jtalks.tests.jcommune.pages.SignUpPage;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * This functional test covers test case JC6
 *
 * @autor masyan
 * @autor erik
 */
public class JC6EmailValidationWithValidData {

	SignUpPage signUpPage;

	@DataProvider(name = "validEmail")
	public Object[][] validEmail() {
		String validEmail = StringHelp.getRandomEmail();
		return new Object[][]{
				{validEmail}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}


	@Test(dataProvider = "validEmail")
	public void emailValidationWithValidDataTest(String email) {
		signUpPage.getEmailField().clear();
		signUpPage.getEmailField().sendKeys(email);
		signUpPage.getSubmitButton().click();
		assertNotExistById(driver, signUpPage.emailErrorMessageSel);
	}
}
