package org.jtalks.tests.jcommune;

import java.util.List;

import org.jtalks.tests.jcommune.rserver.SeleniumServer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.StringHelp;

/**
 * This functional test covers Creating topic test case TC-JC1
 * http://jtalks.org/display/jcommune/TC-JC1++Create+topic
 *
 * @author erik
 */
public class CreateTopicTCJC1Test extends SeleniumServer {
	String userName = "tester112";
	String password = "tester112";
	String subject;
	String message;

	@BeforeTest
	public void init() {
		subject = StringHelp.getRandomString(50);
		message = StringHelp.getRandomString(500);
	}

	@Test
	public void testCreateTopic() {
		signIn(userName, password);
		driver.get("http://deploy.jtalks.org/jcommune/branches/1");
		driver.findElement(By.xpath("//a[@href='/jcommune/topics/new?branchId=1']")).click();
		driver.findElement(By.id("subject")).sendKeys(subject);
		driver.findElement(By.id("tbMsg")).sendKeys(message);
		driver.findElement(By.id("post")).click();

		Assert.assertEquals(driver.findElement(By.xpath("//a[@class='heading']")).getText(), subject);
		Assert.assertEquals(driver.findElement(By.xpath("//div[@class='forum_message_cell_text']")).getText(), message);

		driver.get("http://deploy.jtalks.org/jcommune/branches/1");
		List<WebElement> list = driver.findElements(By.xpath("//ul[@class='forum_table']/li"));
		Assert.assertTrue(assertThatTopicPresent(list, subject));

	}


	/**
	 * This method does the authentication
	 *
	 * @param userName
	 * @param password
	 */
	private void signIn(String userName, String password) {
		driver.get("http://deploy.jtalks.org/jcommune/login");
		driver.findElement(By.id("j_username")).sendKeys(userName);
		driver.findElement(By.id("j_password")).sendKeys(password);
		driver.findElement(By.xpath("//input[@type='submit']")).click();
	}


	/**
	 * this method return true when in list presents the desired text
	 *
	 * @param list The list of webelements
	 * @param text the desired text
	 * @return
	 */
	private boolean assertThatTopicPresent(List<WebElement> list, String text) {
		boolean b = false;
		for (WebElement webElement : list) {
			String t = webElement.getText();
			if (t.contains(text)) {
				b = true;
			}
		}
		return b;
	}


}
