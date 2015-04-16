package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;

import javax.jws.soap.SOAPBinding;

public class Notifications {

    public static void assertNotificationOnBranchSent(Branch branch, User user){
        throw new UnsupportedOperationException("To be implemented");
    }

    public static void assertNotificationsOnBranchNotSent(Branch branch, User user) {
        throw new UnsupportedOperationException();
    }

    public static void assertNotificationOnTopicSent(Topic topic, User user){
        throw new UnsupportedOperationException();
    }

    public static void assertNotificationsOnTopicNotSent(Topic topic, User user) {
        throw new UnsupportedOperationException();
    }

    public static void assertNotificationOnTopicSentBranchNotSent(Topic topic, User user) {
        throw new UnsupportedOperationException();
    }

    public static void assertNotificationOnTopicNotSentBranchSent(Topic topic, User user) {
        throw new UnsupportedOperationException();
    }

    public static void assertNotificationsNotSent(Topic topic, User user){
        throw new UnsupportedOperationException("To be implemented");
    }





}
