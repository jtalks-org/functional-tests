package org.jtalks.tests.jcommune.tests.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * author: erik
 * Date: 09.03.12
 * Time: 13:56
 */
public class JC46EditingPasswordWithValidData {

    ProfilePage profilePage;
    String currentPassword;
    String newPassword;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {

        currentPassword = password;
        newPassword = "new4";

        driver.get(appUrl);
        signIn(username, password);
        profilePage = new ProfilePage(driver);
        profilePage.getCurrentUserLink().click();
        profilePage.getEditProfileButton().click();
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        profilePage.getCurrentUserLink().click();
        profilePage.getEditProfileButton().click();
        profilePage.getCurrentPasswordField().sendKeys(newPassword);
        profilePage.getNewPasswordField().sendKeys(currentPassword);
        profilePage.getConfirmNewPasswordField().sendKeys(currentPassword);
        profilePage.getSaveEditButton().click();
        logOut(appUrl);
    }

    @Test
    public void testEditEmail() {
		//TODO uncomment after X servers will be installed
//        profilePage.getNewPasswordField().sendKeys(newPassword);
//        profilePage.getConfirmNewPasswordField().sendKeys(newPassword);
//        profilePage.getCurrentPasswordField().sendKeys(currentPassword);
//        profilePage.getSaveEditButton().click();
//
//        assertNotExistById(driver, ProfilePage.errorCurrentUserPasswordMessageIdSel);
    }


}
