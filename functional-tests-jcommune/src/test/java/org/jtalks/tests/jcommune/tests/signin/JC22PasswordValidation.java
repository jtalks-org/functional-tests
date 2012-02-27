package org.jtalks.tests.jcommune.tests.signin;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.getApplicationContextPath;

/**
 * @autor masyan
 * @autor erik
 */
public class JC22PasswordValidation {

	@DataProvider(name = "notValidPassword")
	public Object[][] notValidPassword() {
		String notValidPassword = "novalidpassword";
		String notValidPassword2 = "PassWord";
		return new Object[][]{
				{notValidPassword},
				{""},
				{notValidPassword2}
		};
	}

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/login']")).click();
	}

	@Test(dataProvider = "notValidPassword")
	public void passwordValidationTest(String password) {
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		assertExistBySelector(driver, "//span[@class='error']");
	}
}
