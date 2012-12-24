package org.jtalks.tests.jcommune.testlink.profile;

/**
 * @author: erik
 * Date: 09.03.12
 * Time: 20:24
 */
public class JC51ChangeEmailInUserProfileTest {

//	String uniqEmail;
//	String currentEmail;
//
//
//	@BeforeMethod(alwaysRun = true)
//	@Parameters({"app-url", "uUsername", "uPassword", "uEmail"})
//	public void setupCase(String appUrl, String username, String password, String uEmail) {
//		driver.get(appUrl);
//		uniqEmail = StringHelp.getRandomEmail();
//		currentEmail = uEmail;
//		signIn(username, password);
//		profilePage.getCurrentUserLink().click();
//		profilePage.getEditProfileButton().click();
//	}
//
//	@AfterMethod(alwaysRun = true)
//	@Parameters({"app-url"})
//	public void destroy(String appUrl) {
//		profilePage.getCurrentUserLink().click();
//		profilePage.getEditProfileButton().click();
//		profilePage.getEmailEditField().clear();
//		profilePage.getEmailEditField().sendKeys(currentEmail);
//		profilePage.getSaveEditButton().click();
//		logOut(appUrl);
//	}
//
//	@Test
//	public void testChangingEmail() {
//		profilePage.getEmailEditField().clear();
//		profilePage.getEmailEditField().sendKeys(uniqEmail);
//		profilePage.getSaveEditButton().click();
//		assertion.assertEquals(profilePage.getEmail().getText(), uniqEmail);
//	}
}
