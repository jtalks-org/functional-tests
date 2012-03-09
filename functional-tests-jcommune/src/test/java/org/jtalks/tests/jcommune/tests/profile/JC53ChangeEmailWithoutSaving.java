package org.jtalks.tests.jcommune.tests.profile;

import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * author: erik
 * Date: 09.03.12
 * Time: 20:54
 */
public class JC53ChangeEmailWithoutSaving {
    ProfilePage profilePage;
    String currentEmail;
    String uniqEmail;

    @BeforeMethod
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        profilePage = new ProfilePage(driver);
        profilePage.getCurrentUserLink().click();
        uniqEmail = StringHelp.getRandomEmail();
        currentEmail = profilePage.getEmail().getText();
        profilePage.getEditProfileButton().click();
    }

    @AfterMethod
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test
    public void testEmptyEmail() {
        profilePage.getEmailEditField().clear();
        profilePage.getEmailEditField().sendKeys(uniqEmail);
        profilePage.getBackButton().click();
        Assert.assertEquals(profilePage.getEmail().getText(), currentEmail);
    }
}
