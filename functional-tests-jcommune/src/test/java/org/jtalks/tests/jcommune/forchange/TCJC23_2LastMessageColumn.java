package org.jtalks.tests.jcommune.forchange;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.StringHelp;

/**
 * This functional test covers Last message column case TC-JC23
 * http://jtalks.org/display/jcommune/TC-JC23+Last+message+column scenario 2
 *
 * @author erik
 */

public class TCJC23_2LastMessageColumn extends JCommuneSeleniumTest {
//	/*
//	 * to implement this test case at first we need to create 2 topic in
//	 * different branches
//	 */
//
//	@BeforeClass
//	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2",
//			"uPassword2"})
//	public void createTopicsTest(String appURL, String username,
//								 String password, String username2, String password2) {
//		driver.get(appURL);
//		signIn(username, password, appURL);
//
//		driver.findElement(
//				By.xpath("//ul[@class='forum_table'][1]/li[@class='forum_row'][1]/div[@class='forum_info']/h4/a[@class='forum_link']"))
//				.click();
//		createNewTopic();
//
//		logOut(appURL);
//		signIn(username2, password2, appURL);
//		driver.findElement(
//				By.xpath("//ul[@class='forum_table'][1]/li[@class='forum_row'][2]/div[@class='forum_info']/h4/a[@class='forum_link']"))
//				.click();
//		createNewTopic();
//		logOut(appURL);
//
//	}
//
//	@Test(priority = 1)
//	@Parameters({"app-url", "uUsername", "uPassword"})
//	public void loginneUserdOnMainPageTest(String appURL, String username,
//										   String password) {
//		signIn(username, password, appURL);
//		driver.findElement(
//				By.xpath("//ul[@class='forum_table'][1]/li[@class='forum_row'][1]/div[@class='forum_last_message']/a[1]"))
//				.click();
//		Assert.assertTrue(driver.findElement(By.xpath("//ul[@id='stylized']"))
//				.isDisplayed());
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[1]/label"))
//						.getText(), "Username");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[2]/label"))
//						.getText(), "First name");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[3]/label"))
//						.getText(), "Last name");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[4]/label"))
//						.getText(), "Email");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[5]/label"))
//						.getText(), "Language");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[6]/label"))
//						.getText(), "Location");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[7]/label"))
//						.getText(), "Number of topics on page");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[8]/label"))
//						.getText(), "Last login");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[9]/label"))
//						.getText(), "Registration date");
//		Assert.assertEquals(
//				driver.findElement(
//						By.xpath("//ul[@id='stylized']/li[10]/label"))
//						.getText(), "Avatar");
//		Assert.assertEquals(
//				driver.findElement(
//						By.xpath("//ul[@id='stylized']/li[11]/label"))
//						.getText(), "Post count");
//	}
//
//	@Test(priority = 2)
//	@Parameters({"app-url"})
//	public void unloginnedUserOnMainPageTest(String appURL) {
//		driver.get(appURL);
//		driver.findElement(
//				By.xpath("//ul[@class='forum_table'][1]/li[@class='forum_row'][2]/div[@class='forum_last_message']/a[1]"))
//				.click();
//		Assert.assertTrue(driver.findElement(By.xpath("//ul[@id='stylized']"))
//				.isDisplayed());
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[1]/label"))
//						.getText(), "Username");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[2]/label"))
//						.getText(), "First name");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[3]/label"))
//						.getText(), "Last name");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[4]/label"))
//						.getText(), "Last login");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[5]/label"))
//						.getText(), "Registration date");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[6]/label"))
//						.getText(), "Avatar");
//		Assert.assertEquals(
//				driver.findElement(By.xpath("//ul[@id='stylized']/li[7]/label"))
//						.getText(), "Post count");
//	}
//
//	@Test(priority = 3)
//	@Parameters({"app-url"})
//	public void clickOnBackButtonOnMainPageTest(String appURL) {
//		driver.navigate().back();
//		Assert.assertEquals(driver.getCurrentUrl(), appURL);
//	}
//
//	@Test(priority = 4)
//	public void clickOnDateButtonOnMainPageTest() {
//		driver.findElement(
//				By.xpath("//li[@class='forum_row'][1]//div[@class='forum_last_message']/a[1]"))
//				.click();
//		Assert.assertTrue(driver.findElement(
//				By.xpath("//li[@class='forum_row']")).isDisplayed());
//	}
//
//	@Test(priority = 5)
//	@Parameters({"app-url"})
//	public void clickOnBackButtonOnMainPage2Test(String appURL) {
//		driver.navigate().back();
//		Assert.assertEquals(driver.getCurrentUrl(), appURL);
//	}
//
//	/**
//	 * creates new topic in branch with random generated subject 50 chars
//	 * length, and with random generated message 250 chars length
//	 */
//	private void createNewTopic() {
//		driver.findElement(
//				By.xpath("//a[contains(@href, '" + getApplicationContextPath()
//						+ "/topics/new')]")).click();
//		StringHelp.setLongTextValue(driver,
//				driver.findElement(By.id("subject")),
//				StringHelp.getRandomString(50));
//		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
//				StringHelp.getRandomString(250));
//		driver.findElement(By.id("post")).click();
//	}

}
