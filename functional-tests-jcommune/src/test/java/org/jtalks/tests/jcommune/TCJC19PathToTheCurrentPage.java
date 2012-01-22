package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.Parameters;
import utils.CollectionHelp;

import java.util.List;


/**
 * This functional test covers Path to the current page test case TC-JC19
 * http://jtalks.org/display/jcommune/TC-JC19+Path+to+the+current+page
 *
 * @autor masyan
 */
public class TCJC19PathToTheCurrentPage extends JCommuneSeleniumTest {
	String firstSectionTitle;
	String firstSectionUrl;
	String firstBranchTitle;
	String firstBranchUrl;
	List<WebElement> sections;

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void checkIconLinkToMainPageTest(String appUrl, String username, String password) {
		driver.get(appUrl);
		//sign in, because we need English locale
		signIn(username, password, appUrl);
		Assert.assertNotNull(driver.findElement(By.xpath("//a[@href='" + contextPath + "']/img")));
	}

	@Test(priority = 2)
	public void checkNavigationLinksOnBranchPageTest() {
		firstSectionTitle = driver.findElement(By.xpath("//a[@class='forum_header_link']")).getText();
		firstSectionUrl = driver.findElement(By.xpath("//a[@class='forum_header_link']")).getAttribute("href");
		//the first finded branch is located in the first section
		firstBranchTitle = driver.findElement(By.xpath("//a[@class='forum_link']")).getText();
		firstBranchUrl = driver.findElement(By.xpath("//a[@class='forum_link']")).getAttribute("href");
		driver.findElement(By.xpath("//a[@class='forum_link']")).click();
		Assert.assertNotNull(driver.findElement(By.xpath("//a[@class='forums_list' and contains(text(),'Forum')]")));
		Assert.assertNotNull(driver.findElement(By.xpath("//a[@class='forums_list' and contains(text(),'" + firstSectionTitle + "')]")));
	}

	@Test(priority = 3)
	public void checkNavigationLinksOnTopicsPageTest() {
		driver.findElement(By.xpath("//a[@class='forum_link']")).click();
		driver.findElement(By.xpath("//a[@class='forums_list' and contains(text(),'Forum')]"));
		Assert.assertNotNull(driver.findElement(By.xpath("//a[@class='forums_list' and contains(text(),'" + firstSectionTitle + "')]")));
		Assert.assertNotNull(driver.findElement(By.xpath("//a[@class='forums_list' and contains(text(),'" + firstBranchTitle + "')]")));
	}

	@Test(priority = 4)
	public void checkSectionLinkInPathTest() {
		driver.findElement(By.xpath("//a[@class='forums_list' and contains(text(),'" + firstSectionTitle + "')]")).click();
		Assert.assertEquals(driver.getCurrentUrl(), firstSectionUrl);
	}

	@Test(priority = 5)
	public void checkBranchLinkInPathTest() {
		driver.navigate().back();
		driver.findElement(By.xpath("//a[@class='forums_list' and contains(text(),'" + firstBranchTitle + "')]")).click();
		Assert.assertEquals(driver.getCurrentUrl(), firstBranchUrl);
	}

	@Test(priority = 6)
	public void checkForumLinkInPathTest() {
		driver.findElement(By.xpath("//a[@class='forums_list' and contains(text(),'Forum')]")).click();
		sections = driver.findElements(By.xpath("//a[@class='forum_header_link']"));
		if (sections.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 7)
	public void checkSectionLinkOnMainPageTest() {
		WebElement section = CollectionHelp.getRandomWebElementFromCollection(sections);
		String sectionUrl = section.getAttribute("href");
		section.click();
		Assert.assertEquals(driver.getCurrentUrl(), sectionUrl);
	}

	@Test(priority = 8)
	public void checkIconLinkToMainPageTest() {
		driver.findElement(By.xpath("//a[@href='" + contextPath + "']/img")).click();
		sections = driver.findElements(By.xpath("//a[@class='forum_header_link']"));
		if (sections.size() == 0) {
			Assert.assertFalse(true);
		}
	}


}
