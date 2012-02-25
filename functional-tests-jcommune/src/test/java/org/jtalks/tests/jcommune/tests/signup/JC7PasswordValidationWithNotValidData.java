package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;

/**
 * This functional test covers test case JC7
 *
 * @autor masyan
 */
public class JC7PasswordValidationWithNotValidData extends JCommuneSeleniumTest {

	@DataProvider(name = "notValidPassword")
	public Object[][] notValidPassword() {
		String shortPassword = StringHelp.getRandomString(2);
		String longPassword = StringHelp.getRandomString(21);
		String emptyPassword = "";

		return new Object[][]{
				{shortPassword},
				{longPassword},
				{emptyPassword}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	private void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test(dataProvider = "notValidPassword")
	private void passwordValidationWithNotValidDataTest(String pass) {
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertExistById(driver, "password.errors");
	}
}
