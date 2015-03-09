package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Notifications;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReview;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReviewComment;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Dmitry Yakushev
 */
public class CodeReviewNotificationTest {

    public final String NOTIFICATION_BRANCH = "Notification tests";
    public final String BRANCH_NAME_TO_MOVE_TOPIC_IN = "Notification tests branch 2";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void autosubscribe_codeReviewStarterAutomaticallySubscribes() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));

        Topics.assertIsSubscribed(codeReview);
    }

    @Test
    public void autosubscribe_codeReviewCommentatorAutomaticallySubscribes() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();

        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));

        User codeReviewCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Topics.assertIsSubscribed(codeReview);
    }

    @Test
    public void createCodeReview_userSubscribedToBranch_receivesBranchNotificationOnly() throws Exception {
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User branchSubscriber = Users.signUpAndSignIn();
        Branches.subscribe(codeReview.getBranch());

        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);

        Users.logOutAndSignIn(branchSubscriber);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationOnTopicNotSentBranchSent(codeReview, branchSubscriber);
    }

    @Test
    public void createCodeReview_userNotSubscribedToBranch_doNotReceiveNotifications() throws Exception {
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User testUser = Users.signUpAndSignIn();

        User codeReviewStarter = Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);

        Notifications.assertNotificationsNotSent(codeReview, testUser);
    }

    @Test
    public void createCodeReview_userSubscribedToBranch_doNotReceiveNotificationsIfCreatedCodeReviewHimself() throws Exception {
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User branchSubscriber = Users.signUpAndSignIn();
        Branches.subscribe(codeReview.getBranch());
        Topics.createCodeReview(codeReview);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsNotSent(codeReview, branchSubscriber);
    }

    @Test
    public void addCommentToCodeReview_userSubscribedToCodeReviewOnly_receivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertNotificationOnTopicSentBranchNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void addCommentToCodeReview_userSubscribedToBranchOnly_doNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void addCommentToCodeReview_userSubscribedBothToBranchAndCodeReview_receivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationOnTopicSentBranchNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void addCommentToCodeReview_userSubscribedToCodeReview_doNotReceiveNotificationsAboutHisOwnComments() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void addCommentToCodeReview_unsubscribedUser_doNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_userSubscribedToCodeReviewOnly_receivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Notifications.assertNotificationOnTopicSentBranchNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_userSubscribedToBranchOnly_receivesBranchNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationOnTopicNotSentBranchSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_userSubscribedBothToBranchAndCodeReview_receivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationOnTopicSentBranchNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_userSubscribedToCodeReview_doNotReceiveNotificationIfHeDeletedCodeReview() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.deleteTopic(codeReview);

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_userSubscribedToBranch_doNotReceiveNotificationIfHeDeletedCodeReview() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Branches.subscribe(codeReview.getBranch());
        Topics.unsubscribe(codeReview);

        Topics.deleteTopic(codeReview);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_unsubscribedUser_doNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_userSubscribedToCodeReviewOnly_receivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Notifications.assertNotificationOnTopicSentBranchNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_userSubscribedToBranchOnly_doNotReceiveNotification() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        Branch oldBranch = codeReview.getBranch();

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(oldBranch);

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_userSubscribedBothToBranchAndCodeReview_receivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());
        Branch oldBranch = codeReview.getBranch();

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(oldBranch);

        Notifications.assertNotificationOnTopicSentBranchNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_userSubscribedToCodeReview_doNotReceiveNotificationIfHeMovedCodeReview() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_unsubscribedUser_doNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Branch oldBranch = codeReview.getBranch();
        Topics.unsubscribe(codeReview);

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void editCodeReviewComment_userSubscribedToCodeReviewOnly_doNotReceiveNotification() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User codeReviewCommentEditor = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.editCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void editCodeReviewComment_userSubscribedToBranchOnly_doNotReceiveNotification() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        User codeReviewCommentEditor = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.editCodeReviewComment(codeReview, codeReviewComment);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewComment_userSubscribedToCodeReviewOnly_receivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertNotificationOnTopicSentBranchNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewComment_userSubscribedToBranchOnly_doNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewComment_userSubscribedBothToBranchAndCodeReview_receivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationOnTopicSentBranchNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewComment_userSubscribedToCodeReviewOnly_doNotReceiveNotificationsIfHeDeletesComment() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertNotificationsNotSent(codeReview, codeReviewStarter);
    }
}