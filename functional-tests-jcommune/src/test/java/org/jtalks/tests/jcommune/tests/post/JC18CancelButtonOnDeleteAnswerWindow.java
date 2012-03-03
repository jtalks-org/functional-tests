package org.jtalks.tests.jcommune.tests.post;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.testng.Assert.assertEquals;

/**
 * @autor masyan
 * @autor erik
 */
public class JC18CancelButtonOnDeleteAnswerWindow {

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
		postPage = new PostPage();
		postPage.init(driver);
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void cancelButtonOnDeleteAnswerWindowTest() {
		//step 1
		postPage.getDeleteButtonNearLastPost().click();
		postPage.getDeleteConfirmCancelButton().click();
		//step2
		String lastMessageText = postPage.getLastPostMessage().getText();

		assertEquals(lastMessageText, this.answer);

	}
}
