package org.jtalks.tests.jcommune.tests.signup;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * This functional test covers test case JC11
 *
 * @autor masyan
 * @autor erik
 */
public class JC11UniqueUsername {
	String existUsername;

	@BeforeMethod
	@Parameters({"app-url", "uUsername"})
	public void setupCase(String appUrl, String username) {
		this.existUsername = username;
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test
	public void usernameUniqueValidationTest() {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(this.existUsername);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertExistById(driver, "username.errors");
	}
}
