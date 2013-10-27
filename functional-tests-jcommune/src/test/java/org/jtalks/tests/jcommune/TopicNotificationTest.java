package org.jtalks.tests.jcommune;

import net.thucydides.core.annotations.Steps;
import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Notifications;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.testng.annotations.Test;

/**
 * @author Andrey Ivanov
 */
public class TopicNotificationTest {
    @Steps
    private Users users;
    @Steps
    private Topics topics;

    @Test
    public void deletingTopic_ifSubscribedToBranchAsWell_shouldReceiveTopicNotification() throws Exception {
        User user = users.signUpAndSignIn();

        Topic topic = topics.createTopic(new Topic());
        topics.subscribe(topic, user);
        Branches.subscribe(topic.getBranch(), user);

        topics.deleteByUser(topic, users.signUpAndSignIn());
        Notifications.assertTopicNotificationSent(topic, user);
    }

    @Test
    public void deletingTopic_ifSubscribedToBranchAsWell_shouldNotReceiveBranchNotification() throws Exception {
        User user = users.signUpAndSignIn();

        Topic topic = topics.createTopic(new Topic());
        topics.subscribe(topic, user);
        Branches.subscribe(topic.getBranch(), user);

        topics.deleteByUser(topic, users.signUpAndSignIn());
        Notifications.assertBranchNotificationNotSentTo(topic.getBranch(), user);
    }

    @Test
    public void deletingTopic_ifSubscribedToBranch_shouldReceiveBranchNotification() throws Exception {
        User user = users.signUpAndSignIn();

        Topic topic = topics.createTopic(new Topic());
        Branches.subscribe(topic.getBranch(), user);

        topics.deleteByUser(topic, users.signUpAndSignIn());
        Notifications.assertBranchNotificationNotSentTo(topic.getBranch(), user);
    }

    @Test
    public void deletingTopic_ifSubscribedToTopicOnly_shouldReceiveTopicNotification() throws Exception {
        User user = users.signUpAndSignIn();

        Topic topic = topics.createTopic(new Topic());
        topics.subscribe(topic, user);

        topics.deleteByUser(topic, users.signUpAndSignIn());
        Notifications.assertTopicNotificationSent(topic, user);
    }

    @Test
    public void deletingTopic_ifSubscribedUserDeletesTopic_shouldNotReceiveTopicNotification() throws Exception {
        User user = users.signUpAndSignIn();
        Topic topic = topics.createTopic(new Topic().withTopicStarter(user));

        topics.deleteByUser(topic, user);
        Notifications.assertTopicNotificationNotSent(topic, user);
    }

    @Test
    public void movingTopic_ifSubscribedToBranchAsWell_shouldReceiveTopicNotification() throws Exception {
        User user = users.signUpAndSignIn();

        Topic topic = topics.createTopic(new Topic());
        topics.subscribe(topic, user);
        Branches.subscribe(topic.getBranch(), user);

        topics.moveByUser(topic, users.signUpAndSignIn());
        Notifications.assertTopicNotificationSent(topic, user);
    }

    @Test
    public void movingTopic_ifSubscribedToBranchAsWell_shouldNotReceiveBranchNotification() throws Exception {
        User user = users.signUpAndSignIn();

        Topic topic = topics.createTopic(new Topic());
        topics.subscribe(topic, user);
        Branches.subscribe(topic.getBranch(), user);

        topics.moveByUser(topic, users.signUpAndSignIn());
        Notifications.assertBranchNotificationNotSentTo(topic.getBranch(), user);
    }

    @Test
    public void movingTopic_ifSubscribedToBranch_shouldReceiveBranchNotification() throws Exception {
        User user = users.signUpAndSignIn();

        Topic topic = topics.createTopic(new Topic());
        Branches.subscribe(topic.getBranch(), user);

        topics.moveByUser(topic, users.signUpAndSignIn());
        Notifications.assertBranchNotificationNotSentTo(topic.getBranch(), user);
    }

    @Test
    public void movingTopic_ifSubscribedToTopicOnly_shouldReceiveTopicNotification() throws Exception {
        User user = users.signUpAndSignIn();

        Topic topic = topics.createTopic(new Topic());
        topics.subscribe(topic, user);

        topics.moveByUser(topic, users.signUpAndSignIn());
        Notifications.assertTopicNotificationSent(topic, user);
    }

    @Test
    public void movingTopic_ifSubscribedUserMoveTopic_shouldNotReceiveTopicNotification() throws Exception {
        User user = users.signUpAndSignIn();
        Topic topic = topics.createTopic(new Topic().withTopicStarter(user));

        topics.moveByUser(topic, user);
        Notifications.assertTopicNotificationNotSent(topic, user);
    }
}
