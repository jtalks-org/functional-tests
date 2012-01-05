package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * This functional test covers Creating topic test case TC-JC4
 * http://jtalks.org/display/jcommune/TC-JC4+Sign+In
 * 
 * @author erik
 */
public class SignInTCJC4 extends JCommuneSeleniumTest {
	String wrongUser = "wrongUser";
	String wrongPassword = "wrongPassword";

	@Test(priority = 1)
	@Parameters({ "app-url" })
	public void signInWithEmptyDataTest(String appURL) {
		driver.get(appURL);
		signIn("", "", appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());
	}

	@Test(priority = 2)
	@Parameters({ "app-url", "uUsername" })
	public void signInWithoutPasswordTest(String appURL, String username) {
		driver.get(appURL);
		signIn(username, "", appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());
	}

	@Test(priority = 3)
	@Parameters({ "app-url", "uUsername" })
	public void signInWithWrongPasswordTest(String appURL, String username) {
		driver.get(appURL);
		signIn(username, wrongPassword, appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());
	}

	@Test(priority = 4)
	@Parameters({ "app-url", "uUsername", "uPassword" })
	public void signInWithCorrectDataTest(String appURL, String username,
			String password) {
		driver.get(appURL);
		signIn(username, password, appURL);
		Assert.assertEquals(driver.getCurrentUrl(),
				appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//a[@class='currentusername']"))
				.getText().equals(username));
		logOut(appURL);
	}

	@Test(priority = 5)
	@Parameters({ "app-url", "uPassword" })
	public void signInWithWrongUserTest(String appURL, String password) {
		driver.get(appURL);
		signIn(wrongUser, password, appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());
	}

	@Test(priority = 6)
	@Parameters({ "app-url", "uUsername", "uPassword" })
	public void signInUsernameInWrongRegisterTest(String appURL, String username,
			String password) {
		driver.get(appURL);
		signIn(username.toUpperCase(), password, appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());
	}

	@Test(priority = 7)
	@Parameters({ "app-url", "uUsername", "uPassword" })
	public void signInPasswordInWrongRegisterTest(String appURL, String username,
			String password) {
		driver.get(appURL);
		signIn(username, password.toUpperCase(), appURL);
		Assert.assertTrue(driver
				.findElement(By.xpath("//span[@class='error']")).isDisplayed());

	}

}
