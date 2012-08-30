package org.jtalks.tests.jcommune.tests.topic;

import org.testng.Assert;
import org.testng.annotations.*;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * author: erik
 */
public class JC120PaginationTopicsRegisteredUser {
    String branchLink;

    @BeforeClass
    @Parameters({"app-url", "uUsername", "uPassword", "pageSizeDefaultForRegisteredUser"})
    public void setUp(String appUrl, String username, String password, int pageSizeDefault) {
        driver.get(appUrl);
        signIn(username, password);
        clickOnRandomBranch();

        branchLink = driver.getCurrentUrl();

        driver.get(branchLink);
    }

    @AfterClass
    @Parameters({"app-url"})
    public void logout(String appUrl) {
        logOut(appUrl);
    }

    @BeforeMethod
    public void navigateToTopicsPage() {
        driver.get(branchLink);
    }

    @Test
    @Parameters({"pageSizeDefaultForRegisteredUser"})
    public void topicsCountShouldBeFiveOnFirstPage(int pageSizeDefault) {
        Assert.assertEquals(topicPage.getTopicsList().size(), pageSizeDefault);
    }

    @Test
    public void paginationButtonsShouldBePresentOnFirstPage() {
        Assert.assertNotEquals(topicPage.getTopicsButtons().size(), 0);
    }

    @Test
    public void paginationButtonsShouldBePresentOnSecondPage() {
        topicPage.getButtonTopicsPageLink(2).click();
        Assert.assertNotEquals(topicPage.getTopicsButtons().size(), 0);
    }

    @Test
    @Parameters({"pageSizeDefaultForRegisteredUser"})
    public void topicsCountShouldBeFiveOnSecondPage(int pageSizeDefault) {
        topicPage.getButtonTopicsPageLink(2).click();
        Assert.assertEquals(topicPage.getTopicsList().size(), pageSizeDefault);
    }
}
