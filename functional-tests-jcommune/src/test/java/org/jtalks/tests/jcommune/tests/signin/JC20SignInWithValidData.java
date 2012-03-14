package org.jtalks.tests.jcommune.tests.signin;

import org.jtalks.tests.jcommune.pages.MainPage;
import org.jtalks.tests.jcommune.pages.SignInPage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;


/**
 * @autor masyan
 * @autor erik
 */
public class JC20SignInWithValidData {
	String username;
	String password;
	SignInPage sigInPage;
	MainPage mainPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		sigInPage = new SignInPage(driver);
		mainPage = new MainPage(driver);

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
		assertExistById(driver, sigInPage.signInFormSel);

		//step 2
		sigInPage.getUsernameField().sendKeys(username);
		sigInPage.getPasswordField().sendKeys(password);
		sigInPage.getSubmitButton().click();
		Assert.assertEquals(mainPage.getCurrentUsernameLink().getText(), this.username);
	}
}
