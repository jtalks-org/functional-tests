package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Notifications;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Andrey Ivanov
 * @author Andrey Surzhan
 */
public class TopicNotificationTest {
    public final String NOTIFICATION_BRANCH = "Notification tests";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }


    @Test
    public void creatingTopic_ifSubscribedToBranch_shouldReceiveBranchNotification() throws Exception {
        Topic topic = new Topic().withBranch(NOTIFICATION_BRANCH);

        User user = Users.signUpAndSignIn();
        Branches.subscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.createTopic(topic);

        Users.signIn(user);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationOnTopicNotSentBranchSent(topic, user);
    }

    /**
     * editingTopic tests below are checking notification for subscribedUser not for topicCreator
     * because other registrated User has no permissions to edit other Topic
     */
    @Test
    public void editingTopic_shouldNotReceiveTopicAndBranchNotification() throws Exception {
        Topic topic = new Topic().withBranch(NOTIFICATION_BRANCH);

        User topicCreator = Users.signUpAndSignIn();
        Topics.createTopic(topic);

        User subscribedUser = Users.signUpAndSignIn();
        Topics.subscribe(topic);
        Branches.subscribe(topic.getBranch());

        Users.signIn(topicCreator);
        Integer firstPost = 1;
        Topics.editPost(topic, firstPost); //need to be implemented

        Users.signIn(subscribedUser);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationsOnTopicNotSent(topic, subscribedUser);//need to be implemented
        Notifications.assertNotificationsOnBranchNotSent(topic, subscribedUser);//need to be implemented
    }

    @Test
    public void editingTopic_ifSubscribedToBranchOnly_shouldNotReceiveBranchNotification() throws Exception {
        User topicCreator = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));

        User subscribedUser = Users.signUpAndSignIn();
        Branches.subscribe(topic.getBranch());
        Topics.unsubscribe(topic);

        Users.signIn(topicCreator);
        Integer firstPost = 1;
        Topics.editPost(topic, firstPost); //need to be implemented

        Users.signIn(subscribedUser);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationsOnTopicNotSent(topic, subscribedUser);//need to be implemented
        Notifications.assertNotificationsOnBranchNotSent(topic, subscribedUser);//need to be implemented
    }

    @Test
    public void editingTopic_ifSubscribedToTopicOnly_shouldNotReceiveTopicNotification() throws Exception {
        User topicCreator = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));

        User subscribedUser = Users.signUpAndSignIn();
        Topics.subscribe(topic);
        Branches.unsubscribe(topic.getBranch());

        Users.signIn(topicCreator);
        Integer firstPost = 1;
        Topics.editPost(topic, firstPost); //need to be implemented

        Users.signIn(subscribedUser);
        Notifications.assertNotificationsOnTopicNotSent(topic, subscribedUser);//need to be implemented
        Notifications.assertNotificationsOnBranchNotSent(topic, subscribedUser);//need to be implemented
    }


    @Test
    public void otherUserPostsInTopic_ifSubscribedToBothBranchAndTopic_shouldReceiveTopicNotificationNotBranch() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.subscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.postAnswer(topic, topic.getBranch().getTitle());

        Users.signIn(user);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationOnTopicSentBranchNotSent(topic, user);
    }

    @Test
    public void otherUserPostsInTopic_ifSubscribedToBranchOnly_shouldReceiveBranchNotification() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(topic);
        Branches.subscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.postAnswer(topic, topic.getBranch().getTitle());

        Users.signIn(user);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationOnTopicNotSentBranchSent(topic, user);
    }

    @Test
    public void otherUserPostsInTopic_ifSubscribedToTopicOnly_shouldReceiveTopicNotification() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.unsubscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.postAnswer(topic, topic.getBranch().getTitle());

        Notifications.assertNotificationOnTopicSentBranchNotSent(topic, user);
    }


    @Test
    public void userPostsInOwnTopic_ifSubscribedToTopicOnly_shouldNotReceiveTopicNotification() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.unsubscribe(topic.getBranch());
        Topics.postAnswer(topic, topic.getBranch().getTitle());
        Notifications.assertNotificationsOnTopicNotSent(topic, user);//need to be implemented
    }

    @Test
    public void userPostsInOwnTopic_ifSubscribedToBranchOnly_shouldNotReceiveBranchNotification() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(topic);
        Branches.subscribe(topic.getBranch());
        Topics.postAnswer(topic, topic.getBranch().getTitle());
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationsOnBranchNotSent(topic, user);//need to be implemented
    }


    @Test
    public void deletingTopic_ifSubscribedToBothBranchAndTopic_shouldReceiveTopicNotificationNotBranch() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.subscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.deleteTopic(topic);

        Users.signIn(user);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationOnTopicSentBranchNotSent(topic, user);
    }

    @Test
    public void deletingTopic_ifSubscribedToBranchOnly_shouldReceiveBranchNotification() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(topic);
        Branches.subscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.deleteTopic(topic);

        Users.signIn(user);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationOnTopicNotSentBranchSent(topic, user);
    }

    @Test
    public void deletingTopic_ifSubscribedToTopicOnly_shouldReceiveTopicNotification() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.unsubscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.deleteTopic(topic);
        Notifications.assertNotificationOnTopicSentBranchNotSent(topic, user);
    }

    @Test
    public void deletingOwnTopic_ifSubscribedToBothBranchAndTopic_shouldReceiveTopicNotificationNotBranch() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.unsubscribe(topic.getBranch());

        Topics.deleteTopic(topic);
        Notifications.assertNotificationOnTopicSentBranchNotSent(topic, user);
    }


    @Test
    public void movingTopic_ifSubscribedToBothBranchAndTopic_shouldReceiveTopicNotificationNotBranch() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.subscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.moveTopic(topic, "Classical Mechanics");

        Users.signIn(user);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationOnTopicSentBranchNotSent(topic, user);
    }

    @Test
    public void movingTopic_ifSubscribedToBranchOnly_shouldReceiveBranchNotification() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(topic);
        Branches.subscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.moveTopic(topic, "Classical Mechanics");

        Users.signIn(user);
        Branches.unsubscribeIgnoringFail(topic.getBranch());
        Notifications.assertNotificationOnTopicNotSentBranchSent(topic, user);
    }

    @Test
    public void movingTopic_ifSubscribedToTopicOnly_shouldReceiveTopicNotification() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.unsubscribe(topic.getBranch());

        Users.signUpAndSignIn();
        Topics.moveTopic(topic, "Classical Mechanics");
        Notifications.assertNotificationOnTopicSentBranchNotSent(topic, user);
    }

    @Test
    public void movingOwnTopic_ifSubscribedToBothBranchAndTopic_shouldReceiveTopicNotificationNotBranch() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withBranch(NOTIFICATION_BRANCH));
        Topics.subscribe(topic);
        Branches.unsubscribe(topic.getBranch());

        Topics.moveTopic(topic, "Classical Mechanics");
        Notifications.assertNotificationOnTopicSentBranchNotSent(topic, user);
    }

}