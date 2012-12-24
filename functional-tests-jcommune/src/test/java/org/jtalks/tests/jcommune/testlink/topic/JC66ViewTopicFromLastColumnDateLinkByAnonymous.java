package org.jtalks.tests.jcommune.testlink.topic;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;
import static org.testng.Assert.assertEquals;

/**
 * @author masyan
 */
public class JC66ViewTopicFromLastColumnDateLinkByAnonymous {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		String url = driver.getCurrentUrl();
		createTopicForTest();
		logOut(appUrl);
		driver.get(url);
	}

	@Test
	public void viewTopicFromLastColumnDateLinkByAnonymousTest() {

		String titleTopic = CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicsList()).getText();
		CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicLinksFromDateInLastColumn()).click();

		assertEquals(topicPage.getTopicSubject().getText(), titleTopic);

	}
}
