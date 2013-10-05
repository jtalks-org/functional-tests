package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Notifications;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Andrey Ivanov
 */
public class RemoveTopicAndSubscribersTest
{
    public void oneUserIsSubscribeForBranchAndTopicAndOtherUserDeleteTopicInBranch() throws ValidationException {

        User user = new User();
        Set<User> subscribers = new HashSet();
        subscribers.add(user);

        Branch branch = new Branch().withTitle("Test title").withSubscribers(subscribers);
        Topic topic = new Topic("Topic title", "First topic content").withBranch(branch).withSubscribers(subscribers);

        User deleteTopicUser = Users.signUp();
        Users.signIn(deleteTopicUser);

        Topics.deleteByUser(topic, deleteTopicUser);
        Notifications.sendFor(topic, user);
        Notifications.notSendFor(branch, user);
    }

    public void oneUserIsSubscribeForBranchOnlyAndOtherUserDeleteTopicInBranch() throws ValidationException {

        User user = new User();
        Set<User> subscribers = new HashSet();
        subscribers.add(user);

        Branch branch = new Branch().withTitle("Test title").withSubscribers(subscribers);
        Topic topic = new Topic("Topic title", "First topic content").withBranch(branch);

        User deleteTopicUser = Users.signUp();
        Users.signIn(deleteTopicUser);

        Topics.deleteByUser(topic, deleteTopicUser);
        Notifications.sendFor(branch, user);
    }

    public void oneUserIsSubscribeForTopicOnlyAndOtherUserDeleteTopic() throws ValidationException {

        User user = new User();
        Set<User> subscribers = new HashSet();
        subscribers.add(user);

        Topic topic =
                new Topic("Topic title", "First topic content").withBranch("Test branch").withSubscribers(subscribers);

        User deleteTopicUser = Users.signUp();
        Users.signIn(deleteTopicUser);

        Topics.deleteByUser(topic, deleteTopicUser);
        Notifications.sendFor(topic, user);
    }

    public void userIsSubscribeForTopicAndRemoveTopicByYourself()
            throws ValidationException, PermissionsDeniedException {

        User user = Users.signUp();
        Users.signIn(user);

        Topic topic = new Topic("Topic title", "First topic content").withBranch("Test branch").withTopicStarter(user);
        Topics.createTopic(topic);

        Topics.deleteByUser(topic, user);
        Notifications.notSendFor(topic, user);
    }

}
