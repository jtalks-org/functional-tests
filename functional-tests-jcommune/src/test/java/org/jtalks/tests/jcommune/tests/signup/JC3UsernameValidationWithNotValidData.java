package org.jtalks.tests.jcommune.tests.signup;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * This functional test covers test case JC3
 *
 * @autor masyan
 */
public class JC3UsernameValidationWithNotValidData {

	@DataProvider(name = "notValidUsername")
	public Object[][] notValidUsername() {
		String shortUsername = StringHelp.getRandomString(1);
		String longUsername = StringHelp.getRandomString(26);
		String startOfBS = " " + StringHelp.getRandomString(1);
		String endOfBS = StringHelp.getRandomString(1) + " ";
		String onlyBS = "  ";

		return new Object[][]{
				{shortUsername},
				{longUsername},
				{startOfBS},
				{endOfBS},
				{onlyBS},
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}


	@Test(dataProvider = "notValidUsername")
	public void usernameValidationWithNotValidDataTest(String username) {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertExistById(driver, "username.errors");
	}
}
