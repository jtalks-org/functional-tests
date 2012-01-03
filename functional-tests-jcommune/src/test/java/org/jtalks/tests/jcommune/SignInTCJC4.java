package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * This functional test covers Creating topic test case TC-JC4
 * http://jtalks.org/display/jcommune/TC-JC4+Sign+In
 * @author erik
 *
 */
public class SignInTCJC4 extends JCommuneSeleniumTest {

	@Test
	@Parameters({ "app-url", "uUsername", "uPassword" })
	public void signInTest(String appURL, String username, String password) {
		String wrongUser = "wrongUser";
		String wrongPassword = "wrongPassword";

		driver.get(appURL);
		signIn("", "", appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());

		driver.get(appURL);
		signIn(username, "", appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());

		driver.get(appURL);
		signIn(username, wrongPassword, appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());

		driver.get(appURL);
		signIn(username, password, appURL);
		Assert.assertEquals(driver.getCurrentUrl(),
				"http://deploy.jtalks.org/jcommune/");
		Assert.assertTrue(driver
				.findElement(By.xpath("//a[@class='currentusername']"))
				.getText().contains(username));
		logOut();

		driver.get(appURL);
		signIn(wrongUser, password, appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());

		driver.get(appURL);
		signIn("TestSel", password, appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());

		driver.get(appURL);
		signIn(username, "TestSel", appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());

	}

}
