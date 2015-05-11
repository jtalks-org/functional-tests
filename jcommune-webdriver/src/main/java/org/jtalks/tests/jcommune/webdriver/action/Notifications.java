package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.mail.pochta.PochtaMail;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

/**
 * @author pancheshenko andrey
 */
public class Notifications {

    public static void assertTopicNotificationSent(User user) {
        if (!isNotificationReceived("/posts", user.getEmail()))
            throw new AssertionError("Topic notification was not received. Assertion failed");
    }

    public static void assertTopicNotificationNotSent(User user) {
        if (isNotificationReceived("/posts", user.getEmail()))
            throw new AssertionError("Topic notification received. Assertion failed");
    }

    public static void assertBranchNotificationSent(User user) {
        if (!isNotificationReceived("/branches", user.getEmail()))
            throw new AssertionError("Branch notification was not received. Assertion failed");
    }

    public static void assertBranchNotificationNotSent(User user) {
        if (isNotificationReceived("/branches", user.getEmail()))
            throw new AssertionError("Branch notification received. Assertion failed");
    }

    /**
     * Following method checks if the mail for the current @email is received.
     * Standard timeout (PochtaMail.MAILTRAP_NOTIFY_TIMEOUT_SECS = 20) is used
     *
     * @param stringToFindInBody - is the part of the link inside the message body that differs two types of notifications
     * @param email - user's email that should receive the notification
     *
     */
    private static boolean isNotificationReceived(String stringToFindInBody, String email) {
        PochtaMail mailtrapMail = new PochtaMail();
        return mailtrapMail.findNotificationMail(stringToFindInBody, email);
    }

    /**
     * Following method checks if the mail for the current @email is received.
     * No timeout is used. This method should be used when the standard maximum timeout
     * (PochtaMail.MAILTRAP_NOTIFY_TIMEOUT_SECS = 20) or less (in positive case) is used.
     * The logic is that we don't need to wait standard timeout twice if we check
     * Topic notification mail and Branch notification mail right after that.
     *
     * @param stringToFindInBody - is the part of the link inside the message body that differs two types of notifications
     * @param email - user's email that should receive the notification
     *
     */
    private static boolean isNotificationReceivedNoTimeout(String stringToFindInBody, String email) {
        PochtaMail mailtrapMail = new PochtaMail();
        return mailtrapMail.findNotificationMailNoTimeout(stringToFindInBody, email);
    }
}
