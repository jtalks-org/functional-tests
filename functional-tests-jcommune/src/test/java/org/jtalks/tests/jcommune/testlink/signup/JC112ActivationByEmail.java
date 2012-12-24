package org.jtalks.tests.jcommune.testlink.signup;

/**
 * @autor masyan
 */
public class JC112ActivationByEmail {
	//TODO not visible current user link with HTMLUnit driver
//	String password;
//	String username;
//	String email;
//
//	@BeforeMethod
//	@Parameters({"app-url"})
//	public void setupCase(String appUrl) {
//		driver.get(appUrl);
//		signUpPage.getSignUpButton().click();
//		this.username = StringHelp.getRandomString(8);
//		this.password = StringHelp.getRandomString(9);
//		this.email = StringHelp.getRandomEmail();
//		signUpPage.getUsernameField().sendKeys(this.username);
//		signUpPage.getEmailField().sendKeys(this.email);
//		signUpPage.getPasswordField().sendKeys(this.password);
//		signUpPage.getPasswordConfirmField().sendKeys(this.password);
//		signUpPage.getSubmitButton().click();
//		signUpPage.getOkButtonOnInfoWindow().click();
//	}
//
//	@AfterMethod
//	@Parameters({"app-url"})
//	public void destroy(String appUrl) {
//		logOut(appUrl);
//	}
//
//	@Test
//	@Parameters({"app-url", "publicemail", "publicpass"})
//	public void showingRegisteredUsersWhoBrowsingTopic(String appUrl, String publicEmail, String publicPass) {
//		mailServer.goToMailServer();
//		mailServer.signIn(publicEmail, publicPass);
//		mailServer.openFirstMessage();
//		String activeLink = mailServer.getFirstLinkInMessageText().getText();
//
//		driver.get(activeLink);
//		signIn(this.username, this.password);
//
//		assertEquals(mainPage.getCurrentUsernameLink().getText(), this.username);
//	}
}
