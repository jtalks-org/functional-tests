package org.jtalks.tests.jcommune.forchange;

import java.util.List;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.CollectionHelp;
import utils.StringHelp;

/**
 * This functional test covers Last message column case TC-JC23
 * http://jtalks.org/display/jcommune/TC-JC23+Last+message+column scenario 1
 *
 * @author erik
 */
public class TCJC23_1LastMessageColumn extends JCommuneSeleniumTest {

//	/*to implement this test case
//		 * at first we need to create topic
//		 * and add posts there by different users
//		 * */
//
//	List<WebElement> webElementsList;
//	String branchUrl;
//	String topicUrl;
//	String topicUrl2;
//
//	@BeforeClass
//	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
//	public void prepareTestingDataTest(String appURL, String username, String password, String username2, String password2) {
//		driver.get(appURL);
//		signIn(username, password, appURL);
//		//create first topic
//		// choose and click on random section
//		webElementsList = driver.findElements(By
//				.xpath("//a[@class='forum_header_link']"));
//		CollectionHelp.getRandomWebElementFromCollection(webElementsList)
//				.click();
//		// choose and click on random branch
//		webElementsList = driver.findElements(By
//				.xpath("//a[@class='forum_link']"));
//		CollectionHelp.getRandomWebElementFromCollection(webElementsList)
//				.click();
//		//save branch url
//		branchUrl = driver.getCurrentUrl();
//		//create new topic
//		createNewTopic();
//		topicUrl = driver.getCurrentUrl();
//
//		//add post to this topic
//		addPost();
//
//		//logout
//		logOut(appURL);
//
//		//creating second topic
//		//sign in by another user
//		signIn(username2, password2, appURL);
//
//		//add post to first topic
//		driver.get(topicUrl);
//		addPost();
//
//		//create seccond topic
//		driver.get(branchUrl);
//		createNewTopic();
//		topicUrl2 = driver.getCurrentUrl();
//
//		//add post to second topic
//		addPost();
//
//		//logout
//		logOut(appURL);
//
//		//sign in by first user
//		signIn(username, password, appURL);
//
//		//add post to second topic
//		driver.get(topicUrl2);
//		addPost();
//		driver.get(branchUrl);
//
//		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='forum_row'][1]//a[@class='last_message_user']")).getText().equals(username));
//	}
//
//	@Test(priority = 1)
//	public void onBranchLoginedUserTest() {
//		driver.get(branchUrl);
//		driver.findElement(By.xpath("//li[@class='forum_row'][1]//a[@class='last_message_user']")).click();
//		Assert.assertTrue(driver.findElement(By.xpath("//ul[@id='stylized']")).isDisplayed());
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[1]/label")).getText(), "Username");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[2]/label")).getText(), "First name");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[3]/label")).getText(), "Last name");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[4]/label")).getText(), "Email");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[5]/label")).getText(), "Language");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[6]/label")).getText(), "Location");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[7]/label")).getText(), "Number of topics on page");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[8]/label")).getText(), "Last login");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[9]/label")).getText(), "Registration date");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[10]/label")).getText(), "Avatar");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[11]/label")).getText(), "Post count");
//	}
//
//	@Test(priority = 2)
//	public void onBranchNotLoginedUserTest() {
//		driver.get(branchUrl);
//		driver.findElement(By.xpath("//li[@class='forum_row'][2]//a[@class='last_message_user']")).click();
//		Assert.assertTrue(driver.findElement(By.xpath("//ul[@id='stylized']")).isDisplayed());
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[1]/label")).getText(), "Username");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[2]/label")).getText(), "First name");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[3]/label")).getText(), "Last name");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[4]/label")).getText(), "Last login");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[5]/label")).getText(), "Registration date");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[6]/label")).getText(), "Avatar");
//		Assert.assertEquals(driver.findElement(By.xpath("//ul[@id='stylized']/li[7]/label")).getText(), "Post count");
//	}
//
//	@Test(priority = 3)
//	public void onBranchclickOnBackButtonTest() {
//		driver.navigate().back();
//		Assert.assertEquals(driver.getCurrentUrl(), branchUrl);
//	}
//
//	@Test(priority = 4)
//	public void onBranchclickOnDateButtonTest() {
//		driver.findElement(By.xpath("//li[@class='forum_row'][1]//div[@class='forum_last_message']/a[1]")).click();
//		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='forum_row']")).isDisplayed());
//	}
//
//	@Test(priority = 5)
//	public void onBranchclickOnBackButton2Test() {
//		driver.navigate().back();
//		Assert.assertEquals(driver.getCurrentUrl(), branchUrl);
//	}
//
//
//	/**
//	 * adds post with random generated string 250 chars length
//	 */
//	private void addPost() {
//		driver.findElement(
//				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/posts/new')]"))
//				.click();
//		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
//				StringHelp.getRandomString(250));
//		driver.findElement(By.id("post")).click();
//	}
//
//	/**
//	 * creates new topic in branch with random generated subject 50 chars length, and
//	 * with random generated message 250 chars length
//	 */
//	private void createNewTopic() {
//		driver.findElement(
//				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]"))
//				.click();
//		StringHelp.setLongTextValue(driver, driver.findElement(By.id("subject")), StringHelp.getRandomString(50));
//		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")), StringHelp.getRandomString(250));
//		driver.findElement(By.id("post")).click();
//	}

}
