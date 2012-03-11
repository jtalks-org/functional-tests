package org.jtalks.tests.jcommune.tests.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC63ViewProfileFromLastColumnInByUser {

	ProfilePage profilePage;
	TopicPage topicPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		profilePage = new ProfilePage(driver);
		topicPage = new TopicPage(driver);
		topicPage.getBackButton().click();

	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void viewProfileFromLastColumnInByUserTest() {
		profilePage.getProfileLinkFromLastColumn().click();

		assertExistById(driver, profilePage.userDetailsFormSel);
	}
}
