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
 * This functional test covers test case JC4
 *
 * @autor masyan
 * @autor erik
 */
public class JC4UsernameValidationWithValidData {

	SignUpPage signUpPage;

	@DataProvider(name = "validUsername")
	public Object[][] validUsername() {
		String username = StringHelp.getRandomString(10);
		String usernameWithBS = StringHelp.getRandomString(2) + " " + StringHelp.getRandomString(4);
		String usernameLong = StringHelp.getRandomString(25);
		String usernameShortByBSStart = " " + StringHelp.getRandomString(2);
		String usernameShortByBSEnd = StringHelp.getRandomString(2) + " ";
		String usernameShortByBSMid = StringHelp.getRandomString(1) + " " + StringHelp.getRandomString(1);

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
		signUpPage = new SignUpPage(driver);
		signUpPage.getSignUpButton().click();
	}

	@Test(dataProvider = "validUsername")
	public void usernameValidationWithValidDataTest(String username) {
		signUpPage.getUsernameField().clear();
		signUpPage.getUsernameField().sendKeys(username);
		signUpPage.getSubmitButton().click();
		assertNotExistById(driver, signUpPage.usernameErrorMessageSel);
	}
}
