package org.jtalks.tests.jcommune.tests.signin;

import org.jtalks.tests.jcommune.pages.LogInPage;
import org.jtalks.tests.jcommune.pages.MainPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;


/**
 * @autor masyan
 * @autor erik
 */
public class JC20SignInWithValidData {
	String username;
	String password;
    LogInPage logInPage;
    MainPage mainPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
        logInPage = new LogInPage();
        logInPage.init(driver);
        mainPage = new MainPage();
        mainPage.init(driver);

		this.username = username;
		this.password = password;
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void signInWithValidDataTest() {
		//step 1
        mainPage.getLoginLink().click();
		assertExistById(driver, "form");

		//step 2
        logInPage.getUsernameField().sendKeys(username);
        logInPage.getPasswordField().sendKeys(password);
        logInPage.getSubmitButton().click();
		Assert.assertEquals(driver.findElement(By.xpath("//a[@class='currentusername']")).getText(), this.username);
	}
}
