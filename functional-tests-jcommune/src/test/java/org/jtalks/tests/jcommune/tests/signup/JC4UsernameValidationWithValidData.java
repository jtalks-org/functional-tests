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
 * This functional test covers test case JC4
 *
 * @author masyan
 * @author erik
 */
public class JC4UsernameValidationWithValidData {


	@DataProvider(name = "validUsername")
	public Object[][] validUsername() {
		String username = StringHelp.getRandomString(10);
		String usernameWithBS = StringHelp.getRandomString(2) + " " + StringHelp.getRandomString(4);
		String usernameLong = StringHelp.getRandomString(25);
		String usernameShortByBSStart = " " + StringHelp.getRandomString(1);
		String usernameShortByBSEnd = StringHelp.getRandomString(1) + " ";

		return new Object[][]{
				{username},
				{usernameWithBS},
				{usernameLong},
				{usernameLong},
				{usernameShortByBSStart},
				{usernameShortByBSEnd},
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "validUsername")
	public void usernameValidationWithValidDataTest(String username) {
		signUpPage.getUsernameField().clear();
		signUpPage.getUsernameField().sendKeys(username);
		signUpPage.getSubmitButton().click();
		assertionNotExistById(driver, signUpPage.usernameErrorMessageSel);
	}
}
