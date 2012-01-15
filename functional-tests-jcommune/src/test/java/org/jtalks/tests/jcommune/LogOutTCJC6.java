package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * This functional test covers LogOut test case TC-JC6
 * http://jtalks.org/display/jcommune/TC-JC6+Log+Out
 *
 * @autor masyan
 */
public class LogOutTCJC6 extends JCommuneSeleniumTest {

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void logOutTest(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password, appUrl);
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/logout']")).click();
		try {
			driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/logout']"));
			Assert.assertFalse(true);
		}
		catch (NoSuchElementException e) {
		}
	}
}
