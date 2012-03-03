package org.jtalks.tests.jcommune.tests.signin;

import org.jtalks.tests.jcommune.pages.LogInPage;
import org.jtalks.tests.jcommune.pages.MainPage;
import org.openqa.selenium.By;
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
public class JC22PasswordValidation {

    MainPage mainPage;
    LogInPage logInPage;

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

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
        mainPage = new MainPage(driver);
        logInPage = new LogInPage(driver);
        mainPage.getLoginLink().click();
	}

	@Test(dataProvider = "notValidPassword")
	public void passwordValidationTest(String password) {
		logInPage.getPasswordField().sendKeys(password);
		logInPage.getSubmitButton().click();
		assertExistBySelector(driver, "//span[@class='error']");
	}
}
