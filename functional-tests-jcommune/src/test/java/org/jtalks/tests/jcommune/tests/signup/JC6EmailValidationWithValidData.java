package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistById;

/**
 * This functional test covers test case JC6
 *
 * @autor masyan
 */
public class JC6EmailValidationWithValidData extends JCommuneSeleniumTest {

	@DataProvider(name = "validEmail")
	public Object[][] validEmail() {
		String validEmail = StringHelp.getRandomEmail();
		return new Object[][]{
				{validEmail}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	private void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}


	@Test(dataProvider = "validEmail")
	private void emailValidationWithValidDataTest(String email) {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertNotExistById(driver, "email.errors");
	}
}
