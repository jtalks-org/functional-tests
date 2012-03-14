package org.jtalks.tests.jcommune.tests.post;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: masyan
 * Date: 14.03.12
 * Time: 10:40
 * To change this template use File | Settings | File Templates.
 */
public class JC78SignatureViewInPost {
	ProfilePage profilePage;
	TopicPage topicPage;
	PostPage postPage;
	String signature;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		profilePage = new ProfilePage(driver);
		topicPage = new TopicPage(driver);
		postPage = new PostPage(driver);

		//set signature
		profilePage.getProfileLinkFromMainPage().click();
		profilePage.getEditProfileButton().click();
		profilePage.getSignatureField().clear();
		signature = StringHelp.getRandomString(10);
		profilePage.getSignatureField().sendKeys(signature);
		profilePage.getSaveEditButton().click();

		//create topic(post)
		driver.get(appUrl);
		clickOnRandomBranch();
		createTopicForTest();
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void signatureEditingInProfileTest() {
		//topic(post) message created current user. Find signature
		String signatureInPost = topicPage.getTopicMessage().findElement(By.xpath(postPage.signatureTextSel)).getText();

		assertEquals(signatureInPost, signature);
	}
}
