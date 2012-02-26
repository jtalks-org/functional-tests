package org.jtalks.tests.jcommune.tests.topic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import java.util.List;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;

/**
 * @autor masyan
 */
public class JC14BackButtonOnCreateTopicPage {

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
	public void clickBackButtonOnCreateTopicPageTest() {
		//first step
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]"))
				.click();
		Assert.assertTrue(driver.findElement(By.id("subject")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("tbMsg")).isDisplayed());

		//second step
		driver.findElement(By.id("subject")).sendKeys(subject);
		driver.findElement(By.id("tbMsg")).sendKeys(message);
		driver.findElement(By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/branches')]")).click();

		List<WebElement> list = driver.findElements(By.xpath("//ul[@class='forum_table']/li//a[@class='forum_link']"));
		assertNotExistElementOnViewPresent(list, subject);
	}
}
