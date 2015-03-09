package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.mail.pochta.PochtaMail;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

/**
 * @author pancheshenko andrey
 */
public class Notifications {

    private static boolean topicResult;
    private static boolean branchResult;

    public static void assertNotificationOnTopicSentBranchNotSent(Topic topic, User user) {
        if (!checkCurrentNotificationsShortCycle(user)) {
            checkCurrentNotifications(user);
        }
        if (!topicResult || branchResult) {
            throw new AssertionError("Assertion failed"); // todo info what actually failed
        }
    }

    public static void assertNotificationOnTopicNotSentBranchSent(Topic topic, User user) {
        if (!checkCurrentNotificationsShortCycle(user)) {
            checkCurrentNotifications(user);
        }
        if (topicResult || !branchResult) {
            throw new AssertionError("Assertion failed");
        }
    }

    public static void assertNotificationsNotSent(Topic topic, User user) {
        if (!checkCurrentNotificationsShortCycle(user)) {
            checkCurrentNotifications(user);
        }
        if (topicResult || branchResult) {
            throw new AssertionError("Assertion failed");
        }
    }

    private static boolean checkCurrentNotificationsShortCycle(User user) {
        info("Short cycle notifications check start.");
        info("Asserting whether topic notification was received.");
        topicResult = isNotificationReceivedNoTimeout("/posts", user.getEmail());
        info("Topic notification mail was " + (topicResult ? "received" : "not sent"));

        info("Asserting whether branch notification was received...");
        branchResult = isNotificationReceivedNoTimeout("/branches", user.getEmail());
        info("Branch notification mail was " + (branchResult ? "received" : "not sent"));
        return (topicResult || branchResult);
    }

    private static void checkCurrentNotifications(User user) {
        info("Asserting whether topic notification was received...");
        topicResult = isNotificationReceived("/posts", user.getEmail());
        info("Topic notification mail was " + (topicResult ? "received" : "not sent"));

        info("Asserting whether branch notification was received...");
        branchResult = isNotificationReceivedNoTimeout("/branches", user.getEmail());
        info("Branch notification mail was " + (branchResult ? "received" : "not sent"));
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
