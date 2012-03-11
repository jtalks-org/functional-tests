package org.jtalks.tests.jcommune.tests.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.jtalks.tests.jcommune.pages.SignInPage;
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
public class JC64ViewProfileFromLastColumnByAnonymous {
	ProfilePage profilePage;
	SignInPage signInPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		String url = driver.getCurrentUrl();
		createTopicForTest();
		logOut(appUrl);
		driver.get(url);
		profilePage = new ProfilePage(driver);
		signInPage = new SignInPage(driver);
	}


	@Test
	public void viewProfileFromLastColumnByAnonymousTest() {
		profilePage.getProfileLinkFromLastColumn().click();

		assertExistById(driver, signInPage.signInFormSel);
	}
}
