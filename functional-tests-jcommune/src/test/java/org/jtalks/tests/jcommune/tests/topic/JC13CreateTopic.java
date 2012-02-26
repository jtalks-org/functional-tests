package org.jtalks.tests.jcommune.tests.topic;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;

/**
 * @autor masyan
 */
public class JC13CreateTopic {

	String subject = StringHelp.getRandomString(20);
	String message = StringHelp.getRandomString(20);

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void createTopicTest() {
		//first step
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]"))
				.click();
		Assert.assertTrue(driver.findElement(By.id("subject")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("tbMsg")).isDisplayed());

		//second step
		driver.findElement(By.id("subject")).sendKeys(subject);
		driver.findElement(By.id("tbMsg")).sendKeys(message);
		driver.findElement(By.id("post")).click();
		Assert.assertEquals(
				driver.findElement(By.xpath("//a[contains(@class,'heading')]")).getText(),
				subject);
		System.out.println(driver.findElement(
				By.xpath("//div[contains(@class, 'forum_message_cell_text')]"))
				.getText());
		assertContainsInString(driver.findElement(
				By.xpath("//div[contains(@class, 'forum_message_cell_text')]"))
				.getText(), message);
	}

}
