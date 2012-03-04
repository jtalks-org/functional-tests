package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.jtalks.tests.jcommune.pages.SignUpPage;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * This functional test covers test case JC12
 *
 * @autor masyan
 * @autor erik
 */
public class JC12UniqueEmail {

	String existEmail;

	SignUpPage signUpPage;

	@BeforeMethod
	@Parameters({"app-url", "uEmail"})
	public void setupCase(String appUrl, String email) {
		this.existEmail = email;
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}

	@Test
	public void emailUniqueValidationTest() {
		signUpPage.getEmailField().clear();
		signUpPage.getEmailField().sendKeys(this.existEmail);
		signUpPage.getSubmitButton().click();
		assertExistById(driver, signUpPage.emailErrorMessageSel);
	}


}
