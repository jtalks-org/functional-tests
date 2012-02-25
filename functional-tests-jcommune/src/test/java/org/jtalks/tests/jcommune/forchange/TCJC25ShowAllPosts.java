package org.jtalks.tests.jcommune.forchange;


import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;

/**
 * This functional test covers Show all posts test case TC-JC25
 * http://jtalks.org/pages/viewpage.action?pageId=10879082
 *
 * @autor masyan
 */
public class TCJC25ShowAllPosts extends JCommuneSeleniumTest {

	List<WebElement> topics;
	String testTopicTitle;

	@BeforeClass
	@Parameters({"app-url", "uUsername", "uPassword"})
	private void createTestData(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password, appUrl);
		driver.findElement(By.className("forum_link")).click();
		driver.findElement(By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]")).click();
		testTopicTitle = StringHelp.getRandomString(10);
		driver.findElement(By.id("subject")).sendKeys(testTopicTitle);
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(10));
		driver.findElement(By.id("post")).click();
	}

	;

	//for paging need to create more than 5 answers (in profile user have 5 elemnts for page)
	private void createAnswersForPaging() {
		for (int i = 0; i < 5; i++) {
			driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts/new')]")).click();
			driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(10));
			driver.findElement(By.id("post")).click();
		}
	}

	;

	@Test(priority = 1)
	public void showAllButtonExistWithoutAnswersTest() {
		try {
			driver.findElement(By.xpath("//a[contains(@href,'?pagingEnabled=false')]"));
			//if element exist then generate error
			Assert.assertFalse(true);
		}
		catch (NoSuchElementException ex) {
		}

	}

	@Test(priority = 2)
	public void showAllButtonExistWithAnswersTest() {
		createAnswersForPaging();
		List<WebElement> pages = driver.findElements(By.className("page"));
		if (pages.size() < 2) {
			Assert.assertFalse(true);
		}
		try {
			driver.findElement(By.xpath("//a[contains(@href,'?pagingEnabled=false')]")).click();
		}
		catch (NoSuchElementException ex) {
			//if element not exist then generate error
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 3)
	public void showAllButtonClickTest() {
		List<WebElement> pages = driver.findElements(By.className("page"));
		if (pages.size() > 0) {
			Assert.assertFalse(true);
		}
		try {
			driver.findElement(By.xpath("//a[contains(@href,'?pagingEnabled=true')]")).click();
		}
		catch (NoSuchElementException ex) {
			//if element not exist then generate error
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 4)
	public void showPagesButtonClickTest() {
		List<WebElement> pages = driver.findElements(By.className("page"));
		if (pages.size() < 2) {
			Assert.assertFalse(true);
		}
		try {
			driver.findElement(By.xpath("//a[contains(@href,'?pagingEnabled=false')]"));
		}
		catch (NoSuchElementException ex) {
			//if element not exist then generate error
			Assert.assertFalse(true);
		}
	}
}
