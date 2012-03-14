package org.jtalks.tests.jcommune.tests.post;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC27AnswerValidation {

	PostPage postPage;

	@DataProvider(name = "notValidAnswer")
	public Object[][] notValidAnswer() {
		String shortString = StringHelp.getRandomString(1);
		String longString = StringHelp.getRandomString(20001);
		String spaceBegin = " " + StringHelp.getRandomString(1);
		String spaceEnd = StringHelp.getRandomString(1) + " ";

		return new Object[][]{
				{shortString},
				{longString},
				{spaceBegin},
				{spaceEnd}
		};
	}

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		postPage = new PostPage(driver);
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}


	@Test(dataProvider = "notValidAnswer")
	public void answerValidationTest(String msg) {
		//first step
		postPage.getNewButton().click();

		//second step
		//used setLongTextValue because data contains very long string
		StringHelp.setLongTextValue(driver, postPage.getMessageField(), msg);
		postPage.getPostButton().click();
		assertExistById(driver, postPage.postErrorMessageSel);
	}
}
