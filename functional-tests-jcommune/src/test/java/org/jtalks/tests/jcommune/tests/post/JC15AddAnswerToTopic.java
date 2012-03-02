package org.jtalks.tests.jcommune.tests.post;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.testng.Assert.*;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * @autor masyan
 * @autor erik
 */
public class JC15AddAnswerToTopic {

	String answer = StringHelp.getRandomString(20);
	PostPage postPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		postPage = new PostPage();
		postPage.init(driver);
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void addAnswerToTopicTest() {
		//step 1
		postPage.getNewButton().click();
		assertTrue(postPage.getMessageField().isDisplayed());

		//step 2
		StringHelp.setLongTextValue(driver, postPage.getMessageField(), answer);
		postPage.getPostButton().click();
		assertEquals(postPage.getLastPostMessage().getText(), answer);
	}


}
