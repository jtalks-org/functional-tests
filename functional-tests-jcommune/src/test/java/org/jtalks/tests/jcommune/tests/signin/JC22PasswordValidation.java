package org.jtalks.tests.jcommune.tests.signin;

import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.SignInPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 * @autor erik
 */
public class JC22PasswordValidation {

	MainPage mainPage;
	SignInPage signInPage;

	@DataProvider(name = "notValidPassword")
	public Object[][] notValidPassword() {
		String notValidPassword = "novalidpassword";
		String notValidPassword2 = "PassWord";
		return new Object[][]{
				{notValidPassword},
				{""},
				{notValidPassword2}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		mainPage = new MainPage(driver);
		signInPage = new SignInPage(driver);
		mainPage.getLoginLink().click();
	}

	@Test(dataProvider = "notValidPassword")
	public void passwordValidationTest(String password) {
		signInPage.getPasswordField().sendKeys(password);
		signInPage.getSubmitButton().click();
		assertExistBySelector(driver, signInPage.errorMessageSel);
	}
}
