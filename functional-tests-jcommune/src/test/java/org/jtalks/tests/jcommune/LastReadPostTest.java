package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author Andrei Alikov
 */
public class LastReadPostTest {

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void createdTopicShouldBeShowedAsNotReadToAnotherUser() throws Exception {
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());
        mainPage.clickLogout();

        Users.signUpAndSignIn();
        Topic topicWithNewMessages = Topics.findTopic(newTopic.getBranch().getTitle(), newTopic.getTitle());

        assertTrue(topicWithNewMessages.hasNewMessages());
    }

    @Test
    public void createdTopicShouldBeShowedAsReadToAnotherUserAfterMarkAllAsReadClick() throws Exception {
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());
        mainPage.clickLogout();

        Users.signUpAndSignIn();
        Branches.openBranch(newTopic.getBranch().getTitle());
        Branches.clickMarkAllAsRead();
        Topic topicWithNewMessages = Topics.findTopic(newTopic.getBranch().getTitle(), newTopic.getTitle());

        assertFalse(topicWithNewMessages.hasNewMessages());
    }

    @Test
    public void createdTopicShouldBeShowedAsReadToUserCreatedIt() throws Exception {
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());

        Branches.openBranch(newTopic.getBranch().getTitle());
        Branches.clickMarkAllAsRead();
        Topic topicWithNewMessages = Topics.findTopic(newTopic.getBranch().getTitle(), newTopic.getTitle());

        assertFalse(topicWithNewMessages.hasNewMessages());
    }

    @Test
    public void createdTopicShouldBeShowedAsReadToAnonymousUser() throws Exception {
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());
        mainPage.clickLogout();

        Branches.openBranch(newTopic.getBranch().getTitle());
        Branches.clickMarkAllAsRead();
        Topic topicWithNewMessages = Topics.findTopic(newTopic.getBranch().getTitle(), newTopic.getTitle());

        assertFalse(topicWithNewMessages.hasNewMessages());
    }

    @Test
    public void createdTopicWithTwoPagesShouldBeShowedAsNotReadToAnotherUserAfterReadingFirstPage() throws Exception {
        final int postsCountOnPage = 15;
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());

        Branches.openBranch(newTopic.getBranch().getTitle());
        for (int i = 0; i < 2 * postsCountOnPage; ++i) {
            Topics.postAnswer(newTopic, newTopic.getBranch().getTitle());
        }

        mainPage.clickLogout();

        Users.signUpAndSignIn();
        Topics.openTopicInCurrentBranch(1, newTopic.getTitle());

        Branches.openBranch(newTopic.getBranch().getTitle());
        Topic topicWithNewMessages = Topics.findTopic(newTopic.getBranch().getTitle(), newTopic.getTitle());
        assertTrue(topicWithNewMessages.hasNewMessages());
    }

}
