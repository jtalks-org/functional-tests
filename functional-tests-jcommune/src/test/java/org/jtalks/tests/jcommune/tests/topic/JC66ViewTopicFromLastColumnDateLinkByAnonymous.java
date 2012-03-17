package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import java.io.IOException;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * @autor masyan
 */
public class JC66ViewTopicFromLastColumnDateLinkByAnonymous {
	TopicPage topicPage;


	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) throws IOException {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		String url = driver.getCurrentUrl();
		createTopicForTest();
		logOut(appUrl);
		driver.get(url);
		topicPage = new TopicPage(driver);
	}

	@Test
	public void viewTopicFromLastColumnDateLinkByAnonymousTest() {
		String titleTopic = CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicsList()).getText();
		CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicLinksFromDateInLastColumn()).click();

		assertEquals(topicPage.getTopicSubject().getText(), titleTopic);
	}
}
