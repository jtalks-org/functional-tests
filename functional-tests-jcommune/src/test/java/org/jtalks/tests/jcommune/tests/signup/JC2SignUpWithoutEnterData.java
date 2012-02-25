package org.jtalks.tests.jcommune.tests.signup;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;

/**
 * This functional test covers test case JC2
 *
 * @autor masyan
 */
public class JC2SignUpWithoutEnterData extends JCommuneSeleniumTest {

	@BeforeMethod
	@Parameters({"app-url"})
	private void setupCase(String appUrl) {
		driver.get(appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/user/new']")).click();
	}

	@Test
	private void registrationWithEmptyDataTest() {
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertExistById(driver, "username.errors");
	}
}
