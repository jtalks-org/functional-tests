package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;

/**
 * This functional test covers test case JC12
 *
 * @autor masyan
 */
public class JC12UniqueEmail extends JCommuneSeleniumTest {

	String existEmail;

	@BeforeMethod
	@Parameters({"app-url", "uEmail"})
	private void setupCase(String appUrl, String email) {
		this.existEmail = email;
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test
	private void emailUniqueValidationTest() {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(this.existEmail);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertExistById(driver, "email.errors");
	}


}
