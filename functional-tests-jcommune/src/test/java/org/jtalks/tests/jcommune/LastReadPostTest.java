package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
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

        User userThatWantsToSeeNewMessages = Users.signUpAndSignIn();

        Topics.assertHasNewMessages(newTopic, userThatWantsToSeeNewMessages);
    }

    @Test (enabled = false)
    public void topicWithAddedPostShouldBeShowedAsNotReadToAnotherUser() throws Exception {
        User userTopicAuthor = Users.signUpAndSignIn();
        Topic newTopic = new Topic();
        newTopic.withTopicStarter(userTopicAuthor);
        Topics.createTopic(newTopic);

        User userThatWantsToSeeNewMessages = Users.signUpAndSignIn();
        Topics.postAnswer(newTopic, newTopic.getBranch().getTitle());

        Topics.assertHasNewMessages(newTopic, userThatWantsToSeeNewMessages);
    }

    @Test
    public void createdTopicShouldBeShowedAsReadToAnotherUserAfterMarkAllAsReadClick() throws Exception {
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());
        mainPage.clickLogout();

        User userThatWantsToSeeNewMessages = Users.signUpAndSignIn();
        Branches.openBranch(newTopic.getBranch().getTitle());
        Branches.clickMarkAllAsRead();

        Topics.assertHasNotNewMessages(newTopic, userThatWantsToSeeNewMessages);
    }

    @Test (enabled = false)
    public void givenTopicContainsUnreadPost_afterThatPostIsRemoved_topicBecomesRead() throws Exception{
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());
        Topics.postAnswer(newTopic, newTopic.getBranch().getTitle());
        Topics.deleteAnswer(newTopic, newTopic.getLastPost());

        User userThatWantsToSeeNewMessages = Users.signUpAndSignIn();

        Topics.assertHasNotNewMessages(newTopic, userThatWantsToSeeNewMessages);
    }

    @Test
    public void createdTopicShouldBeShowedAsReadToUserCreatedIt() throws Exception {
        User userThatWantsToSeeNewMessages = Users.signUpAndSignIn();
        Topic newTopic = new Topic();
        newTopic.withTopicStarter(userThatWantsToSeeNewMessages);
        Topics.createTopic(newTopic);

        Branches.openBranch(newTopic.getBranch().getTitle());
        Branches.clickMarkAllAsRead();

        Topics.assertHasNotNewMessages(newTopic, userThatWantsToSeeNewMessages);
    }

    @Test
    public void createdTopicShouldBeShowedAsReadToAnonymousUser() throws Exception {
        Users.signUpAndSignIn();
        Topic newTopic = Topics.createTopic(new Topic());
        mainPage.clickLogout();

        Branches.openBranch(newTopic.getBranch().getTitle());
        Branches.clickMarkAllAsRead();

        //null should be changed to anonymous user representation
        Topics.assertHasNotNewMessages(newTopic, null);
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

        User userThatWantsToSeeNewMessages = Users.signUpAndSignIn();
        Topics.openTopicInCurrentBranch(1, newTopic.getTitle());

        //Check, do we need to open branch page.
        Branches.openBranch(newTopic.getBranch().getTitle());
        Topics.assertHasNewMessages(newTopic, userThatWantsToSeeNewMessages);
    }

}
