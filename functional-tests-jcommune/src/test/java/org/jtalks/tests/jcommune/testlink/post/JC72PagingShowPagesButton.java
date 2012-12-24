package org.jtalks.tests.jcommune.testlink.post;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * @author masyan
 */
public class JC72PagingShowPagesButton {

    @BeforeMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();
        createTopicForTest();
        createAnswerForTest(StringHelp.getRandomString(10));
        createAnswerForTest(StringHelp.getRandomString(10));
        createAnswerForTest(StringHelp.getRandomString(10));
        createAnswerForTest(StringHelp.getRandomString(10));
        createAnswerForTest(StringHelp.getRandomString(10));
        createAnswerForTest(StringHelp.getRandomString(10));
        postPage.getShowAllButton().click();

    }

    @AfterMethod(alwaysRun = true, enabled = false)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test(enabled = false)
    public void pagingShowAllButtonTest() {
        postPage.getShowPagesButton().click();
        assertionNotEmptyCollection(postPage.getPagesButtons());
    }
}
