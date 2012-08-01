package org.jtalks.tests.jcommune.tests.topic;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * author: erik
 */
public class JC115PaginationForUnregisteredUser {
    String topicUrl;

    @BeforeMethod
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setUp(String appUrl, String userName, String password) {
        driver.get(appUrl);
        signIn(userName, password);
        clickOnRandomSection();
        clickOnRandomBranchFromSectionPage();
        createTopicForTest();
        topicUrl = driver.getCurrentUrl();

        createPostsForTest(50, 10);
        logOut(appUrl);

    }

    @Test
    public void testPagination() {
        driver.get(topicUrl);
        Assert.assertEquals(postPage.getPostsList().size(), 50);

        CollectionHelp.getWebElementFromCollectionByIndex(postPage.getPagesButtons(), 2).click();

        Assert.assertTrue(postPage.getPostsList().size() > 0);
    }
}
