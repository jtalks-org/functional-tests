package org.jtalks.tests.jcommune;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.testng.annotations.Test;
import utils.StringHelp;

/**
 * This functional test covers Seciruty test case TC-JC13
 * http://jtalks.org/display/jcommune/TC-JC13+Editing+user+profile+validation
 *
 * @autor masyan
 */
public class EditingUserProfileValidationTCJC13 extends JCommuneSeleniumTest {

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void checkViewProfileEditFormTest(String appUrl, String username, String password) {
		driver = new FirefoxDriver();
		driver.get(appUrl);
		signIn(username, password, appUrl);
		driver.findElement(By.xpath("//a[@class='currentusername']")).click();
		driver.findElement(By.xpath("//a[@href='/jcommune/users/edit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("editProfileForm")));
	}

	@Test(priority = 2)
	public void checkErrorMessageWithEmptyEmailTest() {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("email.errors")));
	}

	@Test(priority = 3)
	public void checkErrorMessageWithNotValidEmailTest() {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(StringHelp.getRandomString(8));
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("email.errors")));
	}

	@Test(priority = 4)
	@Parameters({"uEmail2"})
	public void checkErrorMessageWithExistEmailTest(String existsEmail) {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(existsEmail);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("email.errors")));
	}

	@Test(priority = 6)
	@Parameters({"uEmail"})
	public void checkErrorMessageWithValidEmailTest(String validEmail) {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(validEmail);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		//check view profile page
		driver.findElement(By.xpath("//a[@href='/jcommune/users/edit']")).click();
	}

	@Test(priority = 7)
	public void checkErrorMessageWithShortNewPasswordTest() {
		driver.findElement(By.id("newUserPassword")).sendKeys(StringHelp.getRandomString(3));
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("newUserPassword.errors")));
		Assert.assertNotNull(driver.findElement(By.id("newUserPasswordConfirm.errors")));
	}

	@Test(priority = 8)
	public void checkErrorMessageWithLongNewPasswordTest() {
		driver.findElement(By.id("newUserPassword")).clear();
		driver.findElement(By.id("newUserPassword")).sendKeys(StringHelp.getRandomString(21));
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("newUserPassword.errors")));
		Assert.assertNotNull(driver.findElement(By.id("newUserPasswordConfirm.errors")));
	}

	@Test(priority = 9)
	public void checkErrorMessageWithEqualsPasswordsButDifferenCaseTest() {
		String validPassword = StringHelp.getRandomString(8);
		driver.findElement(By.id("newUserPassword")).clear();
		driver.findElement(By.id("newUserPassword")).sendKeys(validPassword);
		driver.findElement(By.id("newUserPasswordConfirm")).sendKeys(validPassword.toUpperCase());
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("newUserPasswordConfirm.errors")));
	}

	@Test(priority = 10)
	public void checkErrorMessageWithValidNewAndConfirmPasswordsTest() {
		String validNewPass = StringHelp.getRandomString(4);
		driver.findElement(By.id("newUserPassword")).clear();
		driver.findElement(By.id("newUserPasswordConfirm")).clear();
		driver.findElement(By.id("newUserPassword")).sendKeys(validNewPass);
		driver.findElement(By.id("newUserPasswordConfirm")).sendKeys(validNewPass);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		try {
			driver.findElement(By.id("newUserPassword.errors"));
			driver.findElement(By.id("newUserPasswordConfirm.errors"));
			//if elements exist then generate error
			Assert.assertFalse(true);
		}
		catch (NoSuchElementException e) {
		}
		Assert.assertNotNull(driver.findElement(By.id("currentUserPassword.errors")));
	}


	@Test(priority = 11)
	@Parameters({"uPassword"})
	public void checkErrorMessageWithWrongCurrentPasswordTest(String truePass) {
		driver.findElement(By.id("currentUserPassword")).sendKeys(truePass + "W");
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("currentUserPassword.errors")));
	}

	@Test(priority = 12)
	@Parameters({"uPassword"})
	public void checkChangePasswordTest(String truePass) {
		driver.findElement(By.id("currentUserPassword")).clear();
		driver.findElement(By.id("newUserPassword")).clear();
		driver.findElement(By.id("newUserPasswordConfirm")).clear();
		//set same password, because user should't change password
		driver.findElement(By.id("currentUserPassword")).sendKeys(truePass);
		driver.findElement(By.id("newUserPassword")).sendKeys(truePass);
		driver.findElement(By.id("newUserPasswordConfirm")).sendKeys(truePass);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//a[@href='/jcommune/users/edit']")));
	}

}
