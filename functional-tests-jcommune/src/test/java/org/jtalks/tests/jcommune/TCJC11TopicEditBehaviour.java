package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import java.util.List;

/**
 * This functional test covers Topic edit behaviour  test case TC-JC11
 * http://jtalks.org/display/jcommune/TC-JC11+Topic+edit+behaviour
 *
 * @autor masyan
 */
public class TCJC11TopicEditBehaviour extends JCommuneSeleniumTest {
	List<WebElement> branches;
	String changedTitle;
	String changedText;
	String updated;

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void checkBranchesListTest(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password, appUrl);
		branches = driver.findElements(By.xpath("//a[@class='forum_link']"));
		if (branches.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 2)
	public void createTopicForCheckValidationTest() {
		CollectionHelp.getRandomWebElementFromCollection(branches).click();
		driver.findElement(By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]")).click();
		driver.findElement(By.id("subject")).sendKeys(StringHelp.getRandomString(10));
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(20));
		driver.findElement(By.id("post")).click();
	}

	@Test(priority = 3)
	public void checkEditTopicWithoutSaveTest() {
		changedTitle = StringHelp.getRandomString(10);
		changedText = StringHelp.getRandomString(10);
		driver.findElement(By.xpath("//a[contains(@href, 'edit')]")).click();
		driver.findElement(By.xpath("//input[@id='subject']")).clear();
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(changedTitle);
		driver.findElement(By.id("tbMsg")).clear();
		driver.findElement(By.id("tbMsg")).sendKeys(changedText);
		driver.findElement(By.id("back")).click();
		//if equals, then title chages, it is error
		Assert.assertFalse(changedTitle.equals(driver.findElement(By.xpath("//a[@class='heading' and @href='#']")).getText()));
		//if equals, then text chages, it is error
		Assert.assertFalse(changedText.equals(driver.findElement(By.xpath("//div[@class='forum_message_cell_text']")).getText()));
	}

	@Test(priority = 4)
	public void checkEditTopicWithSaveTest() {
		driver.findElement(By.xpath("//a[contains(@href, 'edit')]")).click();
		driver.findElement(By.xpath("//input[@id='subject']")).clear();
		driver.findElement(By.xpath("//input[@id='subject']")).sendKeys(changedTitle);
		driver.findElement(By.id("tbMsg")).clear();
		driver.findElement(By.id("tbMsg")).sendKeys(changedText);
		driver.findElement(By.id("post")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//a[@class='heading' and @href='#']")).getText().equals(changedTitle));
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='forum_message_cell_text']")).getText().contains(changedText));
		//check update text in post
		Assert.assertTrue(StringHelp.checkUpdatedPost(driver.findElement(By.xpath("//div[@class='forum_message_cell_text']")).getText()));
	}


}
