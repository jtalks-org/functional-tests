package org.jtalks.tests.jcommune.tests.signin;

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
public class JC21UsernameValidation {

	@DataProvider(name = "notValidUsername")
	public Object[][] notValidUsername() {
		String notValidUsername = "noValidUsername";
		return new Object[][]{
				{notValidUsername},
				{""}
		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		mainPage.getLoginLink().click();
	}

	@Test(dataProvider = "notValidUsername")
	public void usernameValidationTest(String username) {
		signInPage.getUsernameField().sendKeys(username);
		signInPage.getSubmitButton().click();
		assertElementExistsBySelector(driver, signInPage.errorMessageSel);
	}
}
