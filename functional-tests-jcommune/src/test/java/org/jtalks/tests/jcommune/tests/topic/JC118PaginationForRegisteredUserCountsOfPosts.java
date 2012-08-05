package org.jtalks.tests.jcommune.tests.topic;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * author: erik
 */
public class JC118PaginationForRegisteredUserCountsOfPosts {
	String topicLink;

	@BeforeClass
	@Parameters({"app-url", "uUsername", "uPassword", "pageSizeDefaultForRegisteredUser"})
	public void setUp(String appUrl, String username, String password, int pageSizeDefault) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomSection();
		clickOnRandomBranchFromSectionPage();
		createTopicForTest();
		topicLink = driver.getCurrentUrl();
		createPostsForTest(10, 50);
        profilePage.getCurrentUserLink().click();
        if (!profilePage.getPageSizeTableField().getText().equals(Integer.toString(pageSizeDefault))){
            profilePage.getEditProfileButton().click();
            profilePage.selectPageSizeByValue(pageSizeDefault);
            profilePage.getSaveEditButton().click();
            if (!profilePage.getPageSizeTableField().getText().equals(Integer.toString(pageSizeDefault))){
                throw new SkipException("Unable to set page size. Page size for user is " +
                        profilePage.getPageSizeTableField().getText());
            }

        }
	}

	@AfterClass
	@Parameters({"app-url", "uUsername"})
	public void back(String appUrl, String username) {
		logOut(appUrl);
	}

    @BeforeMethod
    public void navigateToFirstTopicPage(){
        driver.get(topicLink);
    }

    @Test
    @Parameters({"pageSizeDefaultForRegisteredUser"})
	public void postsCountShouldBeFiveOnFirstTopicPage(int pageSizeDefault) {
        int postsPageSize = postPage.getPostsList().size();
        Assert.assertEquals(postsPageSize, pageSizeDefault,
                "Post page contains count of posts that not equals " + pageSizeDefault
        );
    }

    @Test
    public void postsCountShouldBeTenOnSecondTopicPage() {
        //Step 1: set 10 page size
        profilePage.getCurrentUserLink().click();
        profilePage.getEditProfileButton().click();
        profilePage.selectPageSizeByValue(10);
        profilePage.getSaveEditButton().click();
        //Step 2: check posts count on the 1 topic page
        driver.get(topicLink);
        Assert.assertEquals(postPage.getPostsList().size(), 10);
        postPage.getPageLinkButton(2).click();
        //Step 3: check posts count on the 1 topic page
        Assert.assertEquals(postPage.getPostsList().size(), 1);


    }

}
