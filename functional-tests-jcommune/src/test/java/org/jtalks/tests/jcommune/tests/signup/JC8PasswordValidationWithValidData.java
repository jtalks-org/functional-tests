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
 * This functional test covers test case JC8
 *
 * @autor masyan
 */
public class JC8PasswordValidationWithValidData {

	@DataProvider(name = "validPassword")
	public Object[][] validPassword() {
		String validPassword = StringHelp.getRandomString(10);
		String validPasswordWithBS = StringHelp.getRandomString(10) + " " + StringHelp.getRandomString(2);
		String validLongPass = StringHelp.getRandomString(20);
		String validPasswordWithBSInMiddle = StringHelp.getRandomString(2) + " " + StringHelp.getRandomString(1);
		return new Object[][]{
				{validPassword},
				{validPasswordWithBS},
				{validLongPass},
				{validPasswordWithBSInMiddle}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test(dataProvider = "validPassword")
	public void passwordValidationWithValidDataTest(String pass) {
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(pass);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertNotExistById(driver, "password.errors");
	}
}
