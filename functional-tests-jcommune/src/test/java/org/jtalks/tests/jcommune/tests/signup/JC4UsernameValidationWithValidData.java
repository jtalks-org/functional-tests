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
 * This functional test covers test case JC4
 *
 * @autor masyan
 */
public class JC4UsernameValidationWithValidData {


	@DataProvider(name = "validUsername")
	public Object[][] validUsername() {
		String username = StringHelp.getRandomString(10);
		String usernameWithBS = StringHelp.getRandomString(2) + " " + StringHelp.getRandomString(4);
		String usernameLong = StringHelp.getRandomString(25);
		String usernameShortByBSStart = " " + StringHelp.getRandomString(2);
		String usernameShortByBSEnd = StringHelp.getRandomString(2) + " ";
		String usernameShortByBSMid = StringHelp.getRandomString(1) + " " + StringHelp.getRandomString(1);

		return new Object[][]{
				{username},
				{usernameWithBS},
				{usernameLong},
				{usernameLong},
				{usernameShortByBSStart},
				{usernameShortByBSEnd},
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test(dataProvider = "validUsername")
	public void usernameValidationWithValidDataTest(String username) {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertNotExistById(driver, "username.errors");
	}
}
