package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.pages.SignUpPage;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * This functional test covers test case JC11
 *
 * @autor masyan
 * @autor erik
 */
public class JC11UniqueUsername {
	String existUsername;

	SignUpPage signUpPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername"})
	public void setupCase(String appUrl, String username) {
		this.existUsername = username;
		driver.get(appUrl);
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}

	@Test
	public void usernameUniqueValidationTest() {
		signUpPage.getUsernameField().clear();
		signUpPage.getUsernameField().sendKeys(this.existUsername);
		signUpPage.getSubmitButton().click();
		assertExistById(driver, signUpPage.usernameErrorMessageSel);
	}
}
