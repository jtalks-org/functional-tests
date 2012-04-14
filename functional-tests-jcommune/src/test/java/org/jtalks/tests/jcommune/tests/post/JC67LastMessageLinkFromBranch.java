package org.jtalks.tests.jcommune.tests.post;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistElementOnViewPresent;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.branchPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.postPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;

/**
 * @author masyan
 */
public class JC67LastMessageLinkFromBranch {
	String message;


	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);

		signIn(username, password);
		CollectionHelp.getFirstWebElementFromCollection(branchPage.getBranchList()).click();
		createTopicForTest();

		message = topicPage.getTopicMessage().getText();
		driver.get(appUrl);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void lastMessageLinkFromBranchTest() {
		CollectionHelp.getFirstWebElementFromCollection(postPage.getLastPostLinksFromBranch()).click();

		assertionExistElementOnViewPresent(postPage.getPostsMessages(), message);
	}
}
