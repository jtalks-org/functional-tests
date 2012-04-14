package org.jtalks.tests.jcommune.tests.post;

import org.openqa.selenium.By;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.postPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;
import static org.testng.Assert.assertEquals;

/**
 * @author masyan
 */
public class JC78SignatureViewInPost {
	String signature;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);

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
