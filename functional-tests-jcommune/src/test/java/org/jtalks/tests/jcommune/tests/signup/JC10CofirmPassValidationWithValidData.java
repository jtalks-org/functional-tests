package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * This functional test covers test case JC10
 *
 * @autor masyan
 * @autor erik
 */
public class JC10CofirmPassValidationWithValidData {

	@DataProvider(name = "validConfirmPassword")
	public Object[][] validConfirmPassword() {
		String validPassword = StringHelp.getRandomString(10);
		return new Object[][]{
				{validPassword, validPassword}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test(dataProvider = "validConfirmPassword")
	public void confirmPasswordValidationWithValidDataTest(String pass, String confirm) {
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);

		driver.findElement(By.id("passwordConfirm")).clear();
		driver.findElement(By.id("passwordConfirm")).sendKeys(confirm);

		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertNotExistById(driver, "passwordConfirm.errors");
	}


}
