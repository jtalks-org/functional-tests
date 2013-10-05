package org.jtalks.tests.jcommune;

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
    @Test
    public void deletingTopic_ifSubscribedToBranchAsWell_shouldReceiveTopicNotification() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic());
        Topics.subscribe(topic, user);
        Branches.subscribe(topic.getBranch(), user);

        Topics.deleteByUser(topic, Users.signUpAndSignIn());
        Notifications.assertTopicNotificationSent(topic, user);
    }

    @Test
    public void deletingTopic_ifSubscribedToBranchAsWell_shouldNotReceiveBranchNotification() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic());
        Topics.subscribe(topic, user);
        Branches.subscribe(topic.getBranch(), user);

        Topics.deleteByUser(topic, Users.signUpAndSignIn());
        Notifications.assertBranchNotificationNotSentTo(topic.getBranch(), user);
    }

    @Test
    public void deletingTopic_ifSubscribedToBranch_shouldReceiveBranchNotification() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic());
        Branches.subscribe(topic.getBranch(), user);

        Topics.deleteByUser(topic, Users.signUpAndSignIn());
        Notifications.assertBranchNotificationNotSentTo(topic.getBranch(), user);
    }

    @Test
    public void deletingTopic_ifSubscribedToTopicOnly_shouldReceiveTopicNotification() throws Exception {
        User user = Users.signUpAndSignIn();

        Topic topic = Topics.createTopic(new Topic());
        Topics.subscribe(topic, user);

        Topics.deleteByUser(topic, Users.signUpAndSignIn());
        Notifications.assertTopicNotificationSent(topic, user);
    }

    @Test
    public void deletingTopic_ifSubscribedUserDeletesTopic_shouldNotReceiveTopicNotification() throws Exception {
        User user = Users.signUpAndSignIn();
        Topic topic = Topics.createTopic(new Topic().withTopicStarter(user));

        Topics.deleteByUser(topic, user);
        Notifications.assertTopicNotificationNotSent(topic, user);
    }

}
