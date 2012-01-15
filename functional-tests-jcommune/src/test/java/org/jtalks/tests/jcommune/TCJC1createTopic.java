package org.jtalks.tests.jcommune;

import java.util.List;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import utils.CollectionHelp;
import utils.StringHelp;

/**
 * This functional test covers Creating topic test case TC-JC1
 * http://jtalks.org/display/jcommune/TC-JC1++Create+topic
 *
 * @author erik
 */
public class TCJC1createTopic extends JCommuneSeleniumTest {
	List<WebElement> webElementsList;
	String branchId;
	String subject;
	String message;

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void clickOnRandomSectionTest(String appURL, String username,
										 String password) {
		driver.get(appURL);
		signIn(username, password, appURL);

		webElementsList = driver.findElements(By
				.xpath("//a[@class='forum_header_link']"));
		if (webElementsList.size() == 0) {
			throw new NoSuchElementException("Sections not found");
		}

		CollectionHelp.getRandomWebElementFromCollection(webElementsList)
				.click();

	}

	@Test(priority = 2)
	public void clickOnRandomBranchTest() {
		webElementsList = driver.findElements(By
				.xpath("//a[@class='forum_link']"));
		if (webElementsList.size() == 0) {
			throw new NoSuchElementException("Branches not found");
		}
		CollectionHelp.getRandomWebElementFromCollection(webElementsList)
				.click();
	}

	@Test(priority = 3)
	public void clickOnNewTopicButtonTest() {
		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/topics/new')]"))
				.click();
		Assert.assertTrue(driver.findElement(By.id("subject")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.id("tbMsg")).isDisplayed());
	}

	@Test(priority = 4)
	public void createNewTopicTest() {
		subject = StringHelp.getRandomString(50);
		message = StringHelp.getRandomString(500);
		driver.findElement(By.id("subject")).sendKeys(subject);
		StringHelp.setLongTextValue(driver, driver.findElement(By.id("tbMsg")),
				message);
		driver.findElement(By.id("post")).click();

		Assert.assertEquals(
				driver.findElement(By.xpath("//a[@class='heading']")).getText(),
				subject);
		Assert.assertEquals(
				driver.findElement(
						By.xpath("//div[@class='forum_message_cell_text']"))
						.getText(), message);
	}

	@Test(priority = 5)
	@Parameters({"app-url"})
	public void clickOnBackButtonTest(String appURL) {

		driver.findElement(
				By.xpath("//a[contains(@href, '" + getApplicationContextPath() + "/branches')]")).click();

		List<WebElement> list = driver
				.findElements(By
						.xpath("//ul[@class='forum_table']/li//a[@class='forum_link']"));
		Assert.assertTrue(assertThatTopicPresent(list, subject));
	}

	/**
	 * this method return true when in list presents the desired text
	 *
	 * @param list The list of webelements
	 * @param text the desired text
	 * @return
	 */
	private boolean assertThatTopicPresent(List<WebElement> list, String text) {
		for (WebElement webElement : list) {
			String t = webElement.getText();
			if (t.equals(text)) {
				return true;
			}
		}
		return false;
	}

}
