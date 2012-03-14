package org.jtalks.tests.jcommune.tests.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * @author masyan
 */
public class JC77SignatureEditingInProfile {

	ProfilePage profilePage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		profilePage = new ProfilePage(driver);
		profilePage.getProfileLinkFromMainPage().click();
		profilePage.getEditProfileButton().click();
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void signatureEditingInProfileTest() {
		profilePage.getSignatureField().clear();
		String newSign = StringHelp.getRandomString(10);
		profilePage.getSignatureField().sendKeys(newSign);
		profilePage.getSaveEditButton().click();

		assertEquals(profilePage.getSignatureText().getText(), newSign);
	}

}
