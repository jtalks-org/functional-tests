package org.jtalks.tests.jcommune.tests.profile;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * @author masyan
 */
public class JC44ViewProfileFromTopic {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
        createTopicForTest();
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test
    public void viewSenderProfileFromPMTest() {

        CollectionHelp.getFirstWebElementFromCollection(postPage.getAuthorsOfPostsList()).click();
        assertionExistById(driver, profilePage.userDetailsFormSel);
    }
}
