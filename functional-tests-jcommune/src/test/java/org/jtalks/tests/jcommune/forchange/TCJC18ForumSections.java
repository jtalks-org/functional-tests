package org.jtalks.tests.jcommune.forchange;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import java.util.ArrayList;
import java.util.List;

/**
 * This functional test covers Forum's sections test case TC-JC8
 * http://jtalks.org/display/jcommune/TC-JC18+Forum%27s+sections
 *
 * @autor masyan
 */
public class TCJC18ForumSections extends JCommuneSeleniumTest {
	List<WebElement> sections;
	String urlBranchForTesting;

	@Test(priority = 1)
	@Parameters({"app-url"})
	public void checkBranchesListTest(String appUrl) {
		driver.get(appUrl);

		sections = driver.findElements(By.xpath("//a[@class='forum_header_link']"));
		List<WebElement> branches = driver.findElements(By.xpath("//a[@class='forum_link']"));

		//all sections should contains branches
		if (branches.size() == 0 || sections.size() == 0 || sections.size() > branches.size()) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 2)
	public void checkBrancheListInSectionTest() {
		WebElement usingSection = CollectionHelp.getRandomWebElementFromCollection(sections);
		String usingSectionTitle = usingSection.getText();
		usingSection.click();
		List<WebElement> branches = driver.findElements(By.xpath("//a[@class='forum_link']"));
		urlBranchForTesting = StringHelp.getUrlWithoutHost(CollectionHelp.getRandomWebElementFromCollection(branches).getAttribute("href"), contextPath);
		String currentSectionTitle = driver.findElement(By.xpath("//a[@class='forum_header_link']")).getText();
		//after click we should see branches list for select section
		Assert.assertEquals(currentSectionTitle, usingSectionTitle);

		if (branches.size() == 0) {
			Assert.assertFalse(true);
		}
	}

	@Test(priority = 3)
	@Parameters("app-url")
	public void checkTopicsListInBranchTest(String appUrl) {
		//click to branch which choosen in previous test
		driver.findElement(By.xpath("//a[@href='" + urlBranchForTesting + "']")).click();
		//topic list in choosen branch
		List<WebElement> topics = driver.findElements(By.xpath("//a[@class='forum_link']"));
		ArrayList<String> topicsHref = new ArrayList();
		for (WebElement topic : topics) {
			topicsHref.add(StringHelp.getUrlWithoutHost(topic.getAttribute("href"), contextPath));
		}
		driver.get(appUrl);
		//go to main page and click shoosen branch
		driver.findElement(By.xpath("//a[@href='" + urlBranchForTesting + "']")).click();
		List<WebElement> topicsOfBranchFromMainPage = driver.findElements(By.xpath("//a[@class='forum_link']"));
		int counter = 0;
		//check topics lists
		for (String topic : topicsHref) {
			for (WebElement topicFromMain : topicsOfBranchFromMainPage) {
				if (topic.equals(StringHelp.getUrlWithoutHost(topicFromMain.getAttribute("href"), contextPath))) {
					counter++;
					continue;
				}
			}
		}
		//if topics list not equals, then generate error
		if (topicsHref.size() != topicsOfBranchFromMainPage.size() || topicsHref.size() != counter) {
			Assert.assertFalse(true);
		}
	}
}
