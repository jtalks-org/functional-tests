package org.jtalks.tests.jcommune.tests.lowlevel.signin;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signInPage;

/**
 * @author masyan
 * @author erik
 */
public class JC22PasswordValidation {

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
		mainPage.getLoginLink().click();
	}

	@Test(dataProvider = "notValidPassword")
	public void passwordValidationTest(String password) {
		signInPage.getPasswordField().sendKeys(password);
		signInPage.getSubmitButton().click();
		assertElementExistsBySelector(driver, signInPage.errorMessageSel);
	}
}
