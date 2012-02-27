package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * This functional test covers test case JC5
 *
 * @autor masyan
 * @autor erik
 */
public class JC5EmailValidationWithNotValidData {

	@DataProvider(name = "notValidEmail")
	public Object[][] notValidEmail() {
		String notValidEmail = StringHelp.getRandomString(6);
		String emptyEmail = "";
		return new Object[][]{
				{notValidEmail}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test(dataProvider = "notValidEmail")
	public void emailValidationWithNotValidDataTest(String email) {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertExistById(driver, "email.errors");
	}
}
