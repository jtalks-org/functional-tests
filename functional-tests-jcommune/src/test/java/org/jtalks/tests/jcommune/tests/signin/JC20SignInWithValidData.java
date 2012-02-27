package org.jtalks.tests.jcommune.tests.signin;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;


/**
 * @autor masyan
 * @autor erik
 */
public class JC20SignInWithValidData {
	String username;
	String password;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		this.username = username;
		this.password = password;
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void signInWithValidDataTest() {
		//step 1
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/login']")).click();
		assertExistById(driver, "form");

		//step 2
		driver.findElement(By.id("j_username")).sendKeys(username);
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertEquals(driver.findElement(By.xpath("//a[@class='currentusername']")).getText(), this.username);
	}
}
