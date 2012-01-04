package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

/**
 * This functional test covers SignUp test case TC-JC5
 * http://http://jtalks.org/display/jcommune/TC-JC5+Sign+Up
 *
 * @author masyan
 */
public class SignUpTCJC5 extends JCommuneSeleniumTest {
	String acceptedUsername;
	String acceptedEmail;
	String acceptedPassword;
	boolean elementExists;

	@Test(priority = 1)
	@Parameters({"app-url"})
	public void clickSignUpLinkTest(String appURL) {
		driver.get(appURL);
		driver.findElement(By.xpath("//a[@href='/jcommune/user/new']")).click();
	}

	@Test(priority = 2)
	public void checkErrorMessageWithEmptyUsernameTest() {

		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("username.errors")));
	}

	@Test(priority = 3)
	public void checkErrorMessageWithShortUsernameTest() {
		driver.findElement(By.id("username")).sendKeys(StringHelp.getRandomString(2));
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("username.errors")));
	}

	@Test(priority = 4)
	public void checkErrorMessageWithLongUsernameTest() {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(StringHelp.getRandomString(21));
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("username.errors")));
	}

	@Test(priority = 5)
	public void checkErrorMessageWithValidUsernameTest() {
		acceptedUsername = StringHelp.getRandomString(10);
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("username")).sendKeys(acceptedUsername);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		try {
			driver.findElement(By.id("username.errors"));
			elementExists = true;
		}
		catch (NoSuchElementException e) {
			elementExists = false;
		}
	}

	@Test(priority = 6)
	public void checkErrorMessageWithNotValidEmailTest() {
		// check error message with empty email
		Assert.assertNotNull(driver.findElement(By.id("email.errors")));
		driver.findElement(By.id("email")).sendKeys(StringHelp.getRandomString(6));
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("email.errors")));
	}

	@Test(priority = 7)
	public void checkErrorMessageWithValidEmailTest() {
		acceptedEmail = StringHelp.getRandomEmail();
		Assert.assertNotNull(driver.findElement(By.id("email.errors")));
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(acceptedEmail);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		try {
			driver.findElement(By.id("email.errors"));
			elementExists = true;
		}
		catch (NoSuchElementException e) {
			elementExists = false;
		}
	}

	@Test(priority = 8)
	public void checkErrorMessageWithShortPasswordTest() {
		// check error message with empty password
		Assert.assertNotNull(driver.findElement(By.id("password.errors")));
		driver.findElement(By.id("password")).sendKeys(StringHelp.getRandomString(2));
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("password.errors")));
	}

	@Test(priority = 9)
	public void checkErrorMessageWithLongPasswordTest() {
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(StringHelp.getRandomString(21));
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("password.errors")));
	}

	@Test(priority = 10)
	public void checkErrorMessageWithValidPasswordTest() {
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("password")).sendKeys(StringHelp.getRandomString(6));
		try {
			driver.findElement(By.id("password.errors"));
			elementExists = true;
		}
		catch (NoSuchElementException e) {
			elementExists = false;
		}
	}

	@Test(priority = 11)
	public void checkErrorMessageWithNotEqualsConfirmPasswordTest() {
		driver.findElement(By.id("passwordConfirm")).sendKeys(StringHelp.getRandomString(7));
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("passwordConfirm.errors")));
	}

	@Test(priority = 12)
	public void checkErrorMessageWithNotEqualsCasePasswordsTest() {
		acceptedPassword = StringHelp.getRandomString(6);
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("passwordConfirm")).clear();
		driver.findElement(By.id("password")).sendKeys(acceptedPassword);
		driver.findElement(By.id("passwordConfirm")).sendKeys(acceptedPassword.toUpperCase());
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("passwordConfirm.errors")));

		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("passwordConfirm")).clear();
		driver.findElement(By.id("password")).sendKeys(acceptedPassword.toUpperCase());
		driver.findElement(By.id("passwordConfirm")).sendKeys(acceptedPassword);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("passwordConfirm.errors")));
	}

	@Test(priority = 13)
	@Parameters({"app-url"})
	public void checkSignUpTest(String appURL) {
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("passwordConfirm")).clear();
		driver.findElement(By.id("password")).sendKeys(acceptedPassword);
		driver.findElement(By.id("passwordConfirm")).sendKeys(acceptedPassword);
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		Assert.assertEquals(driver.getCurrentUrl(), appURL);
	}

	@Test(priority = 14)
	public void checkSignUpSameUserTest() {
		driver.findElement(By.xpath("//a[@href='/jcommune/user/new']")).click();
		driver.findElement(By.id("username")).sendKeys(acceptedUsername);
		driver.findElement(By.id("email")).sendKeys(acceptedEmail);
		driver.findElement(By.id("password")).sendKeys(acceptedPassword);
		driver.findElement(By.id("passwordConfirm")).sendKeys(acceptedPassword);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("username.errors")));
	}

	@Test(priority = 15)
	@Parameters({"app-url"})
	public void checkSignUpAnotherUserTest(String appURL) {
		driver.findElement(By.id("username")).clear();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("password")).clear();
		driver.findElement(By.id("passwordConfirm")).clear();
		driver.findElement(By.id("username")).sendKeys(acceptedUsername + "2");
		driver.findElement(By.id("email")).sendKeys(StringHelp.getRandomEmail());
		driver.findElement(By.id("password")).sendKeys(acceptedPassword + "2");
		driver.findElement(By.id("passwordConfirm")).sendKeys(acceptedPassword + "2");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		Assert.assertEquals(driver.getCurrentUrl(), appURL);
	}
}