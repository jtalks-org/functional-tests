package org.jtalks.tests.jcommune.testlink.topic;

import org.testng.Assert;
import org.testng.annotations.*;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * author: erik
 */
public class JC117PaginationForRegisteredUser {
    String topicLink;
    int postsPageSize;

    @BeforeClass
    @Parameters({"app-url", "uUsername", "uPassword", "pageSizeDefaultForRegisteredUser"})
    public void setUp(String appUrl, String username, String password, int pageSizeDefault) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomSection();
        clickOnRandomBranchFromSectionPage();
        createTopicForTest();
        topicLink = driver.getCurrentUrl();
        createPostsForTest(5, 50);
        profilePage.getCurrentUserLink().click();
    }

    @AfterClass
    @Parameters({"app-url", "uUsername"})
    public void back(String appUrl, String username) {
        logOut(appUrl);
    }

    @BeforeMethod
    public void navigateToFirstTopicPage() {
        driver.get(topicLink);
    }

    @Test
    public void postsCountShouldBeFiveOnFirstTopicPage() {
        postsPageSize = postPage.getPostsList().size();
        Assert.assertEquals(postsPageSize, 5);
    }

    @Test
    public void postsCountShouldBeOneOnSecondTopicPage() {
        postPage.getPageLinkButton(2).click();
        postsPageSize = postPage.getPostsList().size();
        Assert.assertEquals(postsPageSize, 1);
    }
}