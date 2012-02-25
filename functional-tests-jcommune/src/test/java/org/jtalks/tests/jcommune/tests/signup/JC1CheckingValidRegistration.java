package org.jtalks.tests.jcommune.tests.signup;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.testng.Assert.assertEquals;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.getApplicationContextPath;

/**
 * This functional test covers test case JC1
 *
 * @autor masyan
 */
public class JC1CheckingValidRegistration {
	String appUrl;

	@DataProvider(name = "validRegistration")
	public Object[][] validRegistration() {
		String validUsername = StringHelp.getRandomString(10);
		String validEmail = StringHelp.getRandomEmail();
		String validPassword = StringHelp.getRandomString(10);
		return new Object[][]{
				{validUsername, validEmail, validPassword, validPassword}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		this.appUrl = appUrl;
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test(dataProvider = "validRegistration")
	public void validRegistrationTest(String username, String email, String pass, String confirmPass) {
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.id("passwordConfirm")).sendKeys(confirmPass);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals(driver.getCurrentUrl(), appUrl);
	}
}