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
    public void autosubscribe_codeReviewStarterAutomaticallySubscribes() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));

        Topics.assertIsSubscribed(codeReview);
    }

    @Test
    public void  autosubscribe_codeReviewCommentatorAutomaticallySubscribes() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();

        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Users.logout();

        User codeReviewCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Topics.assertIsSubscribed(codeReview);
    }

    @Test
    public void createCodeReview_UserSubscribedToBranchReceivesBranchNotificationOnly() throws  Exception{
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User branchSubscriber = Users.signUpAndSignIn();
        Branches.subscribe(codeReview.getBranch());
        Users.logout();

        User codeReviewStarter = Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
        Users.logout();

        Users.signIn(branchSubscriber);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsOnTopicNotSent(codeReview, branchSubscriber);
        Notifications.assertNotificationOnBranchSent(codeReview.getBranch(), branchSubscriber);

    }

    @Test
    public void createCodeReview_UserNotSubscribedToBranchDoNotReciveNotifications() throws  Exception{
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User branchSubscriber = Users.signUpAndSignIn();
        Users.logout();

        User codeReviewStarter = Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
        Users.logout();

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), branchSubscriber);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, branchSubscriber);
    }

    @Test
    public void createCodeReview_UserSubscribedToBranchDoNotReciveNotificationsIfCreatedCodeReviewHimself() throws  Exception{
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User branchSubscriber = Users.signUpAndSignIn();
        Branches.subscribe(codeReview.getBranch());
        Topics.createCodeReview(codeReview);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), branchSubscriber);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, branchSubscriber);
    }

    @Test
    public void addCommentToCodeReview_UserSubscribedToCodeReviewOnlyReceivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Users.logout();

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertNotificationOnTopicSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
    }

    @Test
    public void addCommentToCodeReview_UserSubscribedToBranchOnlyDoNotReceiveNotifications() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());
        Users.logout();

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Users.logout();

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);

    }

    @Test
    public void addCommentToCodeReview_UserSubscribedBothToBranchAndCodReviewReceivesCodeReviewNotificationOnly() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());
        Users.logout();

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Users.logout();

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationOnTopicSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
    }

    @Test
    public void addCommentToCodeReview_UserSubscribedToCodeReviewDoNotReceiveNotificationsAboutHisOwnComments() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void addCommentToCodeReview_UnsubscribedUserDoNotReceiveNotifications() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Users.logout();

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_UserSubscribedToCodeReviewOnlyReceivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Users.logout();

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Notifications.assertNotificationOnTopicSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_UserSubscribedToBranchOnlyReceivesBranchNotificationOnly() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());
        Users.logout();

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);
        Users.logout();

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationOnBranchSent(codeReview.getBranch(), codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_UserSubscribedBothToBranchAndCodeReviewReceivesCodeReviewNotificationOnly() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());
        Users.logout();

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationOnTopicSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);

    }

    @Test
    public void deleteCodeReview_UserSubscribedToCodeReviewDoNotReceiveNotificationIfHeHimselfDeletedCodeReview() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.deleteTopic(codeReview);

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_UserSubscribedToBranchDoNotReceiveNotificationIfHeHimselfDeletedCodeReview() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Branches.subscribe(codeReview.getBranch());
        Topics.unsubscribe(codeReview);
        Topics.deleteTopic(codeReview);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReview_UnsubscribedUserDoNotReceiveNotifications() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Users.logout();

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_UserSubscribedToCodeReviewOnlyReceivesCodeReviewNotificationOnly() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branch oldBranch = codeReview.getBranch();
        Users.logout();

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Notifications.assertNotificationOnTopicSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(oldBranch, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_UserSubscribedToBranchOnlyDoNotReceiveNotification() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());
        Branch oldBranch = codeReview.getBranch();
        Users.logout();

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);
        Users.logout();

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(oldBranch);

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(oldBranch, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_UserSubscribedBothToBranchAndCodeReviewReceivesCodeReviewNotificationOnly() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());
        Branch oldBranch = codeReview.getBranch();
        Users.logout();

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);
        Users.logout();

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(oldBranch);

        Notifications.assertNotificationOnTopicSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(oldBranch, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_UserSubscribedToCodeReviewDoNotReceiveNotificationIfHeHimselfMovedCodeReview() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branch oldBranch = codeReview.getBranch();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(oldBranch, codeReviewStarter);
    }

    @Test
    public void moveCodeReview_UnsubscribedUserDoNotReceiveNotifications() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Branch oldBranch = codeReview.getBranch();
        Topics.unsubscribe(codeReview);
        Users.logout();

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(oldBranch, codeReviewStarter);
    }

    @Test
    public void editCodeReviewComment_UserSubscribedToCodeReviewOnlyDoNotReciveNotification() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Users.logout();

        User codeReviewCommentEditor = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview);
        CodeReviewComment codeReviewComment = codeReview.getComments().get(0);
        // two lines above should be replaced by following:
        // CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview);
        Topics.editCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void editCodeReviewComment_UserSubscribedToBranchOnlyDoNotReciveNotification() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());
        Users.logout();

        User codeReviewCommentEditor = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview);
        CodeReviewComment codeReviewComment = codeReview.getComments().get(0);
        // two lines above should be replaced by following:
        // CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview);
        Topics.editCodeReviewComment(codeReview, codeReviewComment);
        Users.logout();

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewComment_UserSubscribedToCodeReviewOnlyReceivesCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Users.logout();

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview);
        CodeReviewComment codeReviewComment = codeReview.getComments().get(0);
        // two lines above should be replaced by following:
        // CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview);
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertNotificationOnTopicSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewComment_UserSubscribedToBranchOnlyDoNotReceiveNotifications() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());
        Users.logout();

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview);
        CodeReviewComment codeReviewComment = codeReview.getComments().get(0);
        // two lines above should be replaced by following:
        // CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview);
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);
        Users.logout();

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewComment_UserSubscribedBothToBranchAndCodeReviewReceivesCodeReviewNotificationOnly() throws Exception{
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());
        Users.logout();

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview);
        CodeReviewComment codeReviewComment = codeReview.getComments().get(0);
        // two lines above should be replaced by following:
        // CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview);
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);
        Users.logout();

        Users.signIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertNotificationOnTopicSent(codeReview, codeReviewStarter);
        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewComment_UserSubscribedToCodeReviewOnlyDoNotReceiveNotificationsIfHimselfDeletesComment() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.leaveCodeReviewComment(codeReview);
        CodeReviewComment codeReviewComment = codeReview.getComments().get(0);
        // two lines above should be replaced by following:
        // CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview);
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertNotificationsOnBranchNotSent(codeReview.getBranch(), codeReviewStarter);
        Notifications.assertNotificationsOnTopicNotSent(codeReview, codeReviewStarter);
    }

}