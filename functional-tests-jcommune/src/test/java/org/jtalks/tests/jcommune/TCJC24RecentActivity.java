package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import utils.CollectionHelp;
import utils.StringHelp;

import java.util.List;

/**
 * This functional test covers Recent activity test case TC-JC24
 * http://jtalks.org/display/jcommune/TC-JC24+Recent+activity
 *
 * @autor masyan
 */
public class TCJC24RecentActivity extends JCommuneSeleniumTest {

	List<WebElement> topics;

	@BeforeClass
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void createTestData(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password, appUrl);
		driver.findElement(By.className("forum_link")).click();
		driver.findElement(By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]")).click();
		driver.findElement(By.id("subject")).sendKeys(StringHelp.getRandomString(10));
		driver.findElement(By.id("tbMsg")).sendKeys(StringHelp.getRandomString(10));
		driver.findElement(By.id("post")).click();
		logOut(appUrl);
	}

	@Test(priority = 1)
	public void recentActivityButtonClickTest() {
		driver.findElement(By.xpath("//a[@href='" + getApplicationContextPath() + "/topics/recent']")).click();
		topics = driver.findElements(By.className("forum_link"));
		if (topics.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 2)
	public void clickTopicFromRecentActivityListTest() {
		WebElement choosenTopic = CollectionHelp.getRandomWebElementFromCollection(topics);
		String titleTopic = choosenTopic.getText();
		choosenTopic.click();
		Assert.assertEquals(driver.findElement(By.xpath("//a[@class='heading' and @href='#']")).getText(), titleTopic);
	}

	@Test(priority = 3)
	public void clickButtonBackTest() {
		driver.findElement(By.xpath("//a[contains(@class,'button') and contains(text(),'Back')]")).click();
		topics = driver.findElements(By.className("forum_link"));
		if (topics.size() == 0) {
			Assert.assertFalse(true);
		}
	}

}
