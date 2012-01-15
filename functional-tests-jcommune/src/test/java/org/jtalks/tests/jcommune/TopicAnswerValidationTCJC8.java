package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * This functional test covers Topic Answer Validation test case TC-JC8
 * http://jtalks.org/display/jcommune/TC-JC8+Topic+answer+validation
 *
 * @autor masyan
 */
public class TopicAnswerValidationTCJC8 extends JCommuneSeleniumTest {
	ArrayList<String> brancheNames = new ArrayList();
	List<WebElement> topics;

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void checkBranchesListTest(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password, appUrl);
		List<WebElement> branches = driver.findElements(By.xpath("//a[@class='forum_link']"));
		//we get names, because when we get new page and back to page with Branches list we cant't use WebElements which found
		for (WebElement branch : branches) {
			brancheNames.add(branch.getText());
		}
		if (branches.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 2)
	public void checkTopicsListInBranchTest() {
		WebElement branchEl;
		for (String branchName : brancheNames) {
			branchEl = driver.findElement(By.linkText(branchName));
			branchEl.click();
			topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
			if (topics.size() > 0) {
				return;
			}
			driver.navigate().back();
		}
		//if we not found branch with topic
		Assert.assertFalse(true);
	}

	@Test(priority = 3)
	public void checkButtonAnswerInTopicTest() {
		CollectionHelp.getRandomWebElementFromCollection(topics).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts/new')]")));
	}

	@Test(priority = 4)
	public void clickButtonAnswerTest() {
		driver.findElement(By.xpath("//a[contains(@href,'" + getApplicationContextPath() + "/posts/new')]")).click();
		Assert.assertNotNull(driver.findElement(By.id("postDto")));
	}

	@Test(priority = 5)
	public void checkErrorMessageWithEmptyDataTest() {
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.id("bodyText.errors")));
	}

	@Test(priority = 6)
	public void checkErrorMessageWithShortDataTest() {
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(4));
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.id("bodyText.errors")));
	}

	@Test(priority = 7)
	public void checkErrorMessageWithLongDataTest() {
		driver.findElement(By.id("tbMsg")).clear();
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")), StringHelp.getRandomString(20001));
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.id("bodyText.errors")));
	}

	@Test(priority = 8)
	public void checkErrorMessageWithBackspacesDataTest() {
		driver.findElement(By.id("tbMsg")).clear();
		// 5 backspaces
		driver.findElement(By.id("tbMsg")).sendKeys("     ");
		driver.findElement(By.id("post")).click();
		Assert.assertNotNull(driver.findElement(By.id("bodyText.errors")));
	}
}
