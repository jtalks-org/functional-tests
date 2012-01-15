package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

/**
 * This functional test covers Editing user profile behaviour test case TC-JC14
 * http://jtalks.org/display/jcommune/TC-JC14+Editing+user+profile+behaviour
 *
 * @autor masyan
 */
public class TCJC14editingUserProfileBehaviour extends JCommuneSeleniumTest {

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword", "uEmail"})
	public void checkViewProfileEditFormWithEmailTest(String appUrl, String username, String password, String email) {
		driver.get(appUrl);
		signIn(username, password, appUrl);
		driver.findElement(By.xpath("//a[@class='currentusername']")).click();
		driver.findElement(By.xpath("//a[@href='/" + getApplicationContextPath() + "/users/edit']")).click();
		Assert.assertNotNull(driver.findElement(By.id("editProfileForm")));
		Assert.assertEquals(driver.findElement(By.id("email")).getAttribute("value"), email);
	}

	@Test(priority = 2)
	@Parameters({"uEmail"})
	public void checkEmailWithoutSaveChangesTest(String email) {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.xpath("//div[@class='form_controls']/a[contains(@href,'" + getApplicationContextPath() + "/users/')]")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + email + "']")).getText());
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/users/edit']")).click();
		Assert.assertEquals(driver.findElement(By.id("email")).getAttribute("value"), email);
	}

	@Test(priority = 3)
	@Parameters({"uEmail"})
	public void checkEmailChangesWithoutSaveTest(String email) {
		String randomEmail = StringHelp.getRandomEmail();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(randomEmail);
		driver.findElement(By.xpath("//div[@class='form_controls']/a[contains(@href,'" + getApplicationContextPath() + "/users/')]")).click();

		try {
			driver.findElement(By.xpath("//span[text()='" + randomEmail + "']"));
			//if exist then generate error
		}
		catch (NoSuchElementException e) {
		}
		Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + email + "']")));
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/users/edit']")).click();
		Assert.assertEquals(driver.findElement(By.id("email")).getAttribute("value"), email);
	}

	@Test(priority = 4)
	public void checkEmailChangesSaveTest() {
		String randomEmail = StringHelp.getRandomEmail();
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(randomEmail);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + randomEmail + "']")));
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/users/edit']")).click();
		Assert.assertEquals(driver.findElement(By.id("email")).getAttribute("value"), randomEmail);
	}

	@AfterClass(alwaysRun = true)
	@Parameters({"uEmail"})
	public void revertChanges(String email) {
		driver.findElement(By.id("email")).clear();
		driver.findElement(By.id("email")).sendKeys(email);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//span[text()='" + email + "']")));
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/users/edit']")).click();
		Assert.assertEquals(driver.findElement(By.id("email")).getAttribute("value"), email);
	}


}
