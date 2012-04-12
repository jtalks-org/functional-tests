package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
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
 * Created by IntelliJ IDEA.
 * User: masyan
 * Date: 13.03.12
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class JC73ViewsAmountToTopicWhenClickByAnonymous {
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
		String url = driver.getCurrentUrl();
		logOut(appUrl);
		driver.get(url);
	}

	@Test
	public void viewsAmountToTopicWhenClickByAnonymousTest() {

		//amount views for first topic
		int amountBefore = new Integer(CollectionHelp.getFirstWebElementFromCollection(topicPage.getAmountsOfViewTopics()).getText()).intValue();

		CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicsList()).click();

		topicPage.getBackButton().click();

		int amountAfter = new Integer(CollectionHelp.getFirstWebElementFromCollection(topicPage.getAmountsOfViewTopics()).getText()).intValue();

		assertEquals(amountBefore + 1, amountAfter);

	}
}
