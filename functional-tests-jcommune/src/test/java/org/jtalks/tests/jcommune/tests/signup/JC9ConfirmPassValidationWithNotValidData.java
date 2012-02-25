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
 * This functional test covers test case JC9
 *
 * @autor masyan
 */
public class JC9ConfirmPassValidationWithNotValidData {

	@DataProvider(name = "notValidConfirmPassword")
	public Object[][] notValidConfirmPassword() {
		String validPassword = StringHelp.getRandomString(10);
		String differentCase = validPassword.toUpperCase();
		String empty = "";
		String notEquals = validPassword + "notEquals";
		String notEqualsByBSStart = " " + validPassword;
		String notEqualsByBSEnd = validPassword + " ";
		return new Object[][]{
				{validPassword, differentCase},
				{validPassword, notEquals},
				{validPassword, empty},
				{validPassword, notEqualsByBSStart},
				{validPassword, notEqualsByBSEnd}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}


	@Test(dataProvider = "notValidConfirmPassword")
	public void confirmPasswordValidationForRegiteringFormTest(String pass, String confirm) {
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);

		driver.findElement(By.id("passwordConfirm")).clear();
		driver.findElement(By.id("passwordConfirm")).sendKeys(confirm);

		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertExistById(driver, "passwordConfirm.errors");
	}
}
