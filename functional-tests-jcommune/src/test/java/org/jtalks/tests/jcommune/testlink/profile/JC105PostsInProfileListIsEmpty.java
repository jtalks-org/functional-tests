package org.jtalks.tests.jcommune.testlink.profile;


import org.jtalks.tests.jcommune.pages.ProfilePage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * author: erik
 */
public class JC105PostsInProfileListIsEmpty {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"uUsername3", "uPassword3", "app-url"})
    public void setUpCase(String userName, String password, String appUrl) {
        driver.get(appUrl);
        signIn(userName, password);
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }


    @Test
    public void postListTest() {
        mainPage.getProfileLink().click();
        profilePage.getPostListButton().click();

        assertElementExistsBySelector(driver, ProfilePage.emptyMessageInPosListSel);

    }


}
