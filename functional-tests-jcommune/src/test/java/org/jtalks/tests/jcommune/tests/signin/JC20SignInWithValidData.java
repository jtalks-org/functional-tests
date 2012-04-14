package org.jtalks.tests.jcommune.tests.signin;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signInPage;
import static org.testng.Assert.assertEquals;

/**
 * @author masyan
 * @author erik
 */
public class JC20SignInWithValidData {
	String username;
	String password;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);

		this.username = username;
		this.password = password;
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void signInWithValidDataTest() {
		//step 1
		mainPage.getLoginLink().click();
		assertionExistById(driver, signInPage.signInFormSel);

		//step 2
		signInPage.getUsernameField().sendKeys(username);
		signInPage.getPasswordField().sendKeys(password);
		signInPage.getSubmitButton().click();
		assertEquals(mainPage.getCurrentUsernameLink().getText(), this.username);
	}
}
