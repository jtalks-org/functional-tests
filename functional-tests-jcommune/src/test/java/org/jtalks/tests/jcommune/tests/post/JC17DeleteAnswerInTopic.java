package org.jtalks.tests.jcommune.tests.post;


import org.jtalks.tests.jcommune.pages.PostPage;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.testng.Assert.*;

/**
 * @autor masyan
 * @autor erik
 */
public class JC17DeleteAnswerInTopic {

	String answer = StringHelp.getRandomString(20);

	PostPage postPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		createAnswerForTest(this.answer);
		postPage = new PostPage(driver);
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void deleteAnswerInTopicTest() {
		//step 1
		postPage.getDeleteButtonNearLastPost().click();
		postPage.getDeleteConfirmOkButton().click();

		//step2
		String lastMessageText = postPage.getLastPostMessage().getText();

		assertNotEquals(lastMessageText, this.answer);

	}
}
