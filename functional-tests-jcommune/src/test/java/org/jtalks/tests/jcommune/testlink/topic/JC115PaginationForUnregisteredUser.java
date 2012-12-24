package org.jtalks.tests.jcommune.testlink.topic;

import org.testng.Assert;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * author: erik
 */
public class JC115PaginationForUnregisteredUser {
    String topicUrl;

    @BeforeClass
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
        driver.get(topicUrl);
    }

    @Test
    @Parameters({"pageSizeForUnregisteredUser"})
         public void postsCountShouldBeFiftyOnFirstTopicPage(int postsCount) {
        Assert.assertEquals(postPage.getPostsList().size(), postsCount);
    }

    @Test
    public void postsCountShouldBeOneOnSecondTopicPage() {
        postPage.getPageLinkButton(2).click();
        Assert.assertEquals(postPage.getPostsList().size(), 1);
    }
}
