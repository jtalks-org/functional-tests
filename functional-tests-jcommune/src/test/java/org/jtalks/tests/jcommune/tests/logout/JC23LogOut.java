package org.jtalks.tests.jcommune.tests.logout;

import org.openqa.selenium.By;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.getApplicationContextPath;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;

/**
 * @autor masyan
 */
public class JC23LogOut {

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
	}

	@Test
	public void logOutTest() {
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/logout']")).click();

		assertNotExistBySelector(driver, "//a[@href='" + getApplicationContextPath() + "/logout']");
		assertNotExistBySelector(driver, "//a[@class='currentusername']");

	}

}
