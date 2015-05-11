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

    // Create code review topic

    @Test
    public void createCodeReviewByOther_userSubscribedToBranch_shouldReceiveBranchNotificationOnly() throws Exception {
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User branchSubscriber = Users.signUpAndSignIn();
        Branches.subscribe(codeReview.getBranch());

        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);

        Users.logOutAndSignIn(branchSubscriber);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertTopicNotificationNotSent(branchSubscriber);
        Notifications.assertBranchNotificationSent(branchSubscriber);
    }

    @Test
    public void createCodeReviewByOther_userNotSubscribedToBranch_shouldNotReceiveNotifications() throws Exception {
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User testUser = Users.signUpAndSignIn();

        User codeReviewStarter = Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);

        Notifications.assertTopicNotificationNotSent(testUser);
        Notifications.assertBranchNotificationNotSent(testUser);
    }

    @Test
    public void createCodeReviewByUser_ifHeSubscribedToBranch_shouldNotReceiveNotifications() throws Exception {
        CodeReview codeReview = new CodeReview().withBranch(NOTIFICATION_BRANCH);

        User branchSubscriber = Users.signUpAndSignIn();
        Branches.subscribe(codeReview.getBranch());
        Topics.createCodeReview(codeReview);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertTopicNotificationNotSent(branchSubscriber);
        Notifications.assertBranchNotificationNotSent(branchSubscriber);
    }

    // Add comment to code review

    @Test
    public void addCommentByOther_userSubscribedToCodeReviewOnly_shouldReceiveCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
        Notifications.assertTopicNotificationSent(codeReviewStarter);
    }

    @Test
    public void addCommentByOther_userSubscribedToBranchOnly_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    @Test
    public void addCommentByOther_userSubscribedBothToBranchAndCodeReview_shouldReceiveCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
        Notifications.assertTopicNotificationSent(codeReviewStarter);
    }

    @Test
    public void addOwnComment_ifSubscribedToCodeReview_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    @Test
    public void addCommentByOther_unsubscribedUser_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);

        User topicCommentator = Users.signUpAndSignIn();
        Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    // Delete code review topic

    @Test
    public void deleteCodeReviewByOther_userSubscribedToCodeReviewOnly_shouldReceiveCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
        Notifications.assertTopicNotificationSent(codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewByOther_userSubscribedToBranchOnly_shouldReceiveBranchNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationSent(codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewByOther_userSubscribedBothToBranchAndCodeReview_shouldReceiveCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
        Notifications.assertTopicNotificationSent(codeReviewStarter);
    }

    @Test
    public void deleteOwnCodeReview_ifSubscribedToCodeReview_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.deleteTopic(codeReview);

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    @Test
    public void deleteOwnCodeReview_ifSubscribedToBranch_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Branches.subscribe(codeReview.getBranch());
        Topics.unsubscribe(codeReview);

        Topics.deleteTopic(codeReview);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewByOther_unsubscribedUser_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);

        User codeReviewDeleter = Users.signUpAndSignIn();
        Topics.deleteTopic(codeReview);

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    // Move code review

    @Test
    public void moveCodeReviewByOther_userSubscribedToCodeReviewOnly_shouldReceiveCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
        Notifications.assertTopicNotificationSent(codeReviewStarter);
    }

    @Test
    public void moveCodeReviewByOther_userSubscribedToBranchOnly_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        Branch oldBranch = codeReview.getBranch();

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(oldBranch);

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    @Test
    public void moveCodeReviewByOther_userSubscribedBothToBranchAndCodeReview_shouldReceiveCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());
        Branch oldBranch = codeReview.getBranch();

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(oldBranch);

        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
        Notifications.assertTopicNotificationSent(codeReviewStarter);
    }

    @Test
    public void moveOwnCodeReview_ifSubscribedToCodeReview_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    @Test
    public void moveCodeReviewByOther_unsubscribedUser_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Branch oldBranch = codeReview.getBranch();
        Topics.unsubscribe(codeReview);

        User codeReviewMover = Users.signUpAndSignIn();
        Topics.moveTopic(codeReview, BRANCH_NAME_TO_MOVE_TOPIC_IN);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    // Edit code review comment

    @Test
    public void editCodeReviewCommentByOther_userSubscribedToCodeReviewOnly_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User codeReviewCommentEditor = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.editCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    @Test
    public void editCodeReviewCommentByOther_userSubscribedToBranchOnly_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        User codeReviewCommentEditor = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.editCodeReviewComment(codeReview, codeReviewComment);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    // Delete code review comment

    @Test
    public void deleteCodeReviewCommentByOther_userSubscribedToCodeReviewOnly_shouldReceiveCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
        Notifications.assertTopicNotificationSent(codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewCommentByOther_userSubscribedToBranchOnly_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        Topics.unsubscribe(codeReview);
        Branches.subscribe(codeReview.getBranch());

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }

    @Test
    public void deleteCodeReviewCommentByOther_userSubscribedBothToBranchAndCodeReview_shouldReceiveCodeReviewNotificationOnly() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        Branches.subscribe(codeReview.getBranch());

        User codeReviewCommentDeleter = Users.signUpAndSignIn();
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Users.logOutAndSignIn(codeReviewStarter);
        Branches.unsubscribeIgnoringFail(codeReview.getBranch());

        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
        Notifications.assertTopicNotificationSent(codeReviewStarter);
    }

    @Test
    public void deleteOwnCodeReviewComment_ifSubscribedToCodeReviewOnly_shouldNotReceiveNotifications() throws Exception {
        User codeReviewStarter = Users.signUpAndSignIn();
        CodeReview codeReview = Topics.createCodeReview(new CodeReview().withBranch(NOTIFICATION_BRANCH));
        //We do not subscribe codeReviewStarter to topic explicitly, because he is automatically subscribed
        CodeReviewComment codeReviewComment = Topics.leaveCodeReviewComment(codeReview, new CodeReviewComment());
        Topics.deleteCodeReviewComment(codeReview, codeReviewComment);

        Notifications.assertTopicNotificationNotSent(codeReviewStarter);
        Notifications.assertBranchNotificationNotSent(codeReviewStarter);
    }
}