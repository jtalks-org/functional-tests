package org.jtalks.tests.jcommune.tests.signup;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mailServer;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;
import static org.testng.Assert.assertEquals;


/**
 * @autor masyan
 */
public class JC112ActivationByEmail {

	String password;
	String username;
	String email;

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		signUpPage.getSignUpButton().click();
		this.username = StringHelp.getRandomString(8);
		this.password = StringHelp.getRandomString(9);
		this.email = StringHelp.getRandomEmail();
		signUpPage.getUsernameField().sendKeys(this.username);
		signUpPage.getEmailField().sendKeys(this.email);
		signUpPage.getPasswordField().sendKeys(this.password);
		signUpPage.getPasswordConfirmField().sendKeys(this.password);
		signUpPage.getSubmitButton().click();
		signUpPage.getOkButtonOnInfoWindow().click();
	}

	@AfterMethod
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	@Parameters({"app-url", "publicemail", "publicpass"})
	public void showingRegisteredUsersWhoBrowsingTopic(String appUrl, String publicEmail, String publicPass) {
		mailServer.goToMailServer();
		mailServer.signIn(publicEmail, publicPass);
		mailServer.openFirstMessage();
		mailServer.getFirstLinkInMessageText().click();

		driver.get(appUrl);
		signIn(this.username, this.password);

		assertEquals(mainPage.getCurrentUsernameLink().getText(), this.username);
	}
}
