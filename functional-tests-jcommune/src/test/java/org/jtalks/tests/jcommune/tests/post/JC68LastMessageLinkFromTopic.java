package org.jtalks.tests.jcommune.tests.post;

import org.jtalks.tests.jcommune.pages.BranchPage;
import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistElementOnViewPresent;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC68LastMessageLinkFromTopic {
	TopicPage topicPage;
	BranchPage branchPage;
	PostPage postPage;
	String message;


	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		topicPage = new TopicPage(driver);
		postPage = new PostPage(driver);
		branchPage = new BranchPage(driver);

		signIn(username, password);
		CollectionHelp.getFirstWebElementFromCollection(branchPage.getBranchList()).click();
		String url = driver.getCurrentUrl();
		createTopicForTest();

		message = topicPage.getTopicMessage().getText();
		driver.get(url);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void lastMessageLinkFromBranchTest() {
		CollectionHelp.getFirstWebElementFromCollection(postPage.getLastPostLinksFromTopic()).click();

		assertExistElementOnViewPresent(postPage.getPostsMessages(), message);
	}
}
