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
 * This functional test covers test case JC12
 *
 * @autor masyan
 */
public class JC12UniqueEmail {

	String existEmail;

	@BeforeMethod
	@Parameters({"app-url", "uEmail"})
	public void setupCase(String appUrl, String email) {
		this.existEmail = email;
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test
	public void emailUniqueValidationTest() {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(this.existEmail);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertExistById(driver, "email.errors");
	}


}
