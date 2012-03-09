package org.jtalks.tests.jcommune.tests.post;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistElementOnViewPresent;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createAnswerForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC49PermanentPostLinkForDeletedPost {
	PostPage postPage;
	String post;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		post = StringHelp.getRandomString(10);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		createAnswerForTest(post);
		postPage = new PostPage(driver);
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void permanentPostLinkForDeletedPostTest() {

		postPage.getLinkButtonNearLastPost().click();

		//get url
		String url = postPage.getPermanentUrlToPost().getText();

		//deletePost
		postPage.getDeleteButtonNearLastPost().click();
		postPage.getDeleteConfirmOkButton().click();

		driver.get(url);

		assertNotExistElementOnViewPresent(postPage.getPostsMessages(), post);
	}
}
