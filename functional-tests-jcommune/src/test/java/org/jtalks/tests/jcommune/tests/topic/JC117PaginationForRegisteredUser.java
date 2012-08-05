package org.jtalks.tests.jcommune.tests.topic;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.*;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.profilePage;


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
        if (!profilePage.getPageSizeTableField().getText().equals(Integer.toString(pageSizeDefault))){
            profilePage.getEditProfileButton().click();
            profilePage.selectPageSizeByValue(pageSizeDefault);
            profilePage.getSaveEditButton().click();
            if (!profilePage.getPageSizeTableField().getText().equals(Integer.toString(pageSizeDefault))){
                throw new SkipException("Unable to set page size. Page size for user is " +
                        profilePage.getPageSizeTableField().getText());
            }

        };
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
    public void postsCountShouldBeFiveOnFirstTopicPage() {
        postsPageSize = postPage.getPostsList().size();
        Assert.assertEquals(postsPageSize, 5);
    }

    @Test
    public  void postsCountShouldBeOneOnSecondTopicPage() {
    postPage.getPageLinkButton(2).click();
    postsPageSize = postPage.getPostsList().size();
    Assert.assertEquals(postsPageSize, 1);
    }
}