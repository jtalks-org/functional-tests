package org.jtalks.tests.jcommune.tests.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * User: erik
 * Date: 24.03.12
 * Time: 20:47
 */
public class JC95EditingEmailInUserProfileWithNotValidData {
    ProfilePage profilePage;

    String existEmail;
    String incorrectFormattedMail = "incorrectemailformat";
    String validEmail;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword", "uEmail", "uEmail2"})
    public void setupCase(String appUrl, String username, String password, String uEmail, String uEmail2) {
        driver.get(appUrl);
        validEmail = uEmail;
        existEmail = uEmail2;
        signIn(username, password);
        profilePage = new ProfilePage(driver);
        profilePage.getCurrentUserLink().click();
        profilePage.getEditProfileButton().click();
    }

    @Test
    public void testEditEmail() {
        profilePage.getEmailEditField().clear();
        profilePage.getSaveEditButton().click();
        assertExistById(driver, ProfilePage.errorEmailMessageIdSel);

        profilePage.getEmailEditField().clear();
        profilePage.getEmailEditField().sendKeys(incorrectFormattedMail);
        profilePage.getSaveEditButton().click();
        assertExistById(driver, ProfilePage.errorEmailMessageIdSel);

        profilePage.getEmailEditField().clear();
        profilePage.getEmailEditField().sendKeys(existEmail);
        profilePage.getSaveEditButton().click();
        assertExistById(driver, ProfilePage.errorEmailMessageIdSel);
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        profilePage.getCurrentUserLink().click();
        profilePage.getEditProfileButton().click();
        profilePage.getEmailEditField().clear();
        profilePage.getEmailEditField().sendKeys(validEmail);
        profilePage.getSaveEditButton().click();
        logOut(appUrl);
    }

}
