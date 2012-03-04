package org.jtalks.tests.jcommune.tests.signin;

import org.jtalks.tests.jcommune.pages.SignInPage;
import org.jtalks.tests.jcommune.pages.MainPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.getApplicationContextPath;

/**
 * @autor masyan
 * @autor erik
 */
public class JC21UsernameValidation {
	MainPage mainPage;
	SignInPage signInPage;

	@DataProvider(name = "notValidUsername")
	public Object[][] notValidUsername() {
		String notValidUsername = "noValidUsername";
		return new Object[][]{
				{notValidUsername},
				{""}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		mainPage = new MainPage(driver);
		signInPage = new SignInPage(driver);
		mainPage.getLoginLink().click();
	}

	@Test(dataProvider = "notValidUsername")
	public void usernameValidationTest(String username) {
		signInPage.getUsernameField().sendKeys(username);
		signInPage.getSubmitButton().click();
		assertExistBySelector(driver, signInPage.errorMessageSel);
	}
}
