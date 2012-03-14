package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * @autor masyan
 */
public class JC76ViewsAmountToTopicWhenRefreshByUser {
	TopicPage topicPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		topicPage = new TopicPage(driver);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		topicPage.getBackButton().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void viewsAmountToTopicWhenRefreshByUserTest() {
		//amount views for first topic
		int amountBefore = new Integer(CollectionHelp.getFirstWebElementFromCollection(topicPage.getAmountsOfViewTopics()).getText()).intValue();

		CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicsList()).click();

		driver.navigate().refresh();

		topicPage.getBackButton().click();

		int amountAfter = new Integer(CollectionHelp.getFirstWebElementFromCollection(topicPage.getAmountsOfViewTopics()).getText()).intValue();

		assertEquals(amountBefore + 2, amountAfter);
	}
}
