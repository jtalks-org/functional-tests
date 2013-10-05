package org.jtalks.tests.jcommune;

import com.google.common.collect.Sets;
import org.jtalks.tests.jcommune.webdriver.action.Notifications;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;

import java.util.Set;

/**
 * @author Andrey Ivanov
 */
public class RemoveTopicAndSubscribersTest
{
    public void oneUserIsSubscribeForBranchAndTopicAndOtherUserDeleteTopicInBranch() throws ValidationException {

        User user = new User();
        Set<User> subscribers = Sets.newHashSet(user);

        Branch branch = new Branch().withSubscribers(subscribers);
        Topic topic = new Topic().withBranch(branch).withSubscribers(subscribers);

        User deleteTopicUser = Users.signUp();
        Users.signIn(deleteTopicUser);

        Topics.deleteByUser(topic, deleteTopicUser);
        Notifications.sendFor(topic, user);
        Notifications.assertBranchNotificationNotSentTo(branch, user);
    }

    public void oneUserIsSubscribeForBranchOnlyAndOtherUserDeleteTopicInBranch() throws ValidationException {

        User user = new User();
        Set<User> subscribers = Sets.newHashSet(user);

        Branch branch = new Branch().withSubscribers(subscribers);
        Topic topic = new Topic().withBranch(branch);

        User deleteTopicUser = Users.signUp();
        Users.signIn(deleteTopicUser);

        Topics.deleteByUser(topic, deleteTopicUser);
        Notifications.sendFor(branch, user);
    }

    public void oneUserIsSubscribeForTopicOnlyAndOtherUserDeleteTopic() throws ValidationException {

        User user = new User();
        Set<User> subscribers = Sets.newHashSet(user);

        Topic topic = new Topic().withBranch("Test branch").withSubscribers(subscribers);

        User deleteTopicUser = Users.signUp();
        Users.signIn(deleteTopicUser);

        Topics.deleteByUser(topic, deleteTopicUser);
        Notifications.sendFor(topic, user);
    }

    public void userIsSubscribeForTopicAndRemoveTopicByYourself()
            throws ValidationException, PermissionsDeniedException {

        User user = Users.signUp();
        Users.signIn(user);

        Topic topic = new Topic().withBranch("Test branch").withTopicStarter(user);
        Topics.createTopic(topic);

        Topics.deleteByUser(topic, user);
        Notifications.notSendFor(topic, user);
    }

}
