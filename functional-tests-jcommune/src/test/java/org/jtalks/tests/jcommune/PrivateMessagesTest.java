package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.PrivateMessages;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.privatemessage.PrivateMessage;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.PrivateMessagesPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import java.util.List;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author stanislav bashkirtsev
 * @author Varro
 * @author andrey pancheshenko
 */
public class PrivateMessagesTest {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);

    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test(groups = "ui-tests")
    public void sendPmWithCorrectFields_ShouldComeToReceiversInbox() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
        PrivateMessages.assertPmExistsInRemoteInbox(receiver, pm);
    }

    @Test
    public void sendPmMinSubject_ShouldComeToReceiversInbox() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withSubject(randomAlphanumeric(2));
        PrivateMessages.sendPrivateMessage(pm);
        PrivateMessages.assertPmExistsInRemoteInbox(receiver, pm);
    }

    @Test
    public void sendPmMinMessage_ShouldComeToReceiversInbox() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withContent(randomAlphanumeric(2));
        PrivateMessages.sendPrivateMessage(pm);
        PrivateMessages.assertPmExistsInRemoteInbox(receiver, pm);
    }

    @Test
    public void sendPmMaxSubject_ShouldComeToReceiversInbox() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withSubject(randomAlphanumeric(120));
        PrivateMessages.sendPrivateMessage(pm);
        PrivateMessages.assertPmExistsInRemoteInbox(receiver, pm);
    }

    @Test
    public void sendPmMaxMessage_ShouldComeToReceiversInbox() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withContent(randomAlphabetic(20000));
        PrivateMessages.sendPrivateMessage(pm);
        PrivateMessages.assertPmExistsInRemoteInbox(receiver, pm);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.INCORRECT_SUBJECT_LENGTH_ERROR)
    public void sendPmExceedMaxSubject_ShouldFail() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withSubject(randomAlphanumeric(121));
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.INCORRECT_SUBJECT_LENGTH_ERROR)
    public void sendPmBelowMinBoundarySubject_ShouldFail() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withSubject(randomAlphanumeric(1));
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.INCORRECT_SUBJECT_LENGTH_ERROR)
    public void sendPmEmptySubject_ShouldFail() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withSubject("");
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.INCORRECT_LENGTH_CONTENT_FIELD)
    public void sendPmEmptyMessage_ShouldFail() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withContent("");
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.INCORRECT_LENGTH_CONTENT_FIELD)
    public void sendPmBelowMinBoundaryMessage_ShouldFail() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withContent(randomAlphabetic(1));
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.INCORRECT_LENGTH_CONTENT_FIELD)
    public void sendPmExceedMaxMessage_ShouldFail() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withContent(randomAlphabetic(20001));
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.INCORRECT_TO_FIELD_ERROR)
    public void sendPmToNonRegisteredUsername_ShouldFail() throws Exception {
        User receiver = new User();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.INCORRECT_TO_FIELD_ERROR)
    public void sendPmWithEmptyUsername_ShouldFail() throws Exception {
        User receiver = new User("","");
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.SAME_USER_IN_TO_FIELD_ERROR)
    public void sendPmWithMessageAuthorAsReceiver_ShouldFail() throws Exception {
        User receiver = Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = PrivateMessagesPage.SAME_USER_IN_TO_FIELD_ERROR)
    public void sendPmWithReceiverUsernameAsAuthorUsernameInDiffLetterCase_ShouldFail() throws Exception {
        User receiver = Users.signUpAndSignIn();
        receiver.setUsername(receiver.getUsername().toLowerCase());
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
    }

    @Test(groups = "ui-tests")
    public void sendPm_ShouldSaveCopyInOutbox() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
        PrivateMessages.assertPmExistsInOutbox(pm);
    }

    // Drafts tests

    @Test(groups = "ui-tests")
    public void saveDraftWithAllPmCorrectFields_ShouldPass() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.savePrivateMessageAsDraft(pm);
        PrivateMessages.assertPmExistsInDraft(pm);
    }

    @Test (enabled = false) // bug: http://jira.jtalks.org/browse/JC-2016
    public void saveDraftWithAuthorAsReceiver_ShouldPass() throws Exception {
        User receiver = Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.savePrivateMessageAsDraft(pm);
        PrivateMessages.assertPmExistsInDraft(pm);
    }

    @Test (enabled = false) // bug: http://jira.jtalks.org/browse/JC-2016
    public void saveDraftWithEmptyReceiver_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        User receiver = new User("","");
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.savePrivateMessageAsDraft(pm);
        PrivateMessages.assertPmExistsInDraft(pm);
    }

    @Test (enabled = false) // bug: http://jira.jtalks.org/browse/JC-2016
    public void saveDraftWithNonExistentReceiver_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        User receiver = new User(randomAlphanumeric(10),"");
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.savePrivateMessageAsDraft(pm);
        PrivateMessages.assertPmExistsInDraft(pm);
    }

    @Test (enabled = false) // bug: http://jira.jtalks.org/browse/JC-2016
    public void saveDraftWithEmptyContent_ShouldPass() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver).withContent("");
        PrivateMessages.savePrivateMessageAsDraft(pm);
        PrivateMessages.assertPmExistsInDraft(pm);
    }

    @Test
    public void editDraft_ShouldSaveDraftWithNewContentInFields() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessage pmEdited = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.savePrivateMessageAsDraft(pm);
        PrivateMessages.editDraft(pm, pmEdited);
        PrivateMessages.assertPmExistsInDraft(pmEdited);
    }

    // Deleting tests

    @Test(groups = "ui-tests")
    public void deleteReceivedPmFromInbox_ShouldPass() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
        Users.logout();
        Users.signIn(receiver);
        PrivateMessages.removePmFromInbox(pm);
        PrivateMessages.assertPmNotExistInInbox(pm);
    }

    @Test
    public void deleteSentPmFromOutbox_ShouldPass() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
        PrivateMessages.removePmFromOutbox(pm);
        PrivateMessages.assertPmNotExistInOutbox(pm);
    }

    @Test
    public void deleteSavedDraft_ShouldPass() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.savePrivateMessageAsDraft(pm);
        PrivateMessages.removePmFromDrafts(pm);
        PrivateMessages.assertPmNotExistInDrafts(pm);
    }

    @Test
    public void deleteMessagesFromOutbox_ShouldPass() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        List<PrivateMessage> privateMessages = PrivateMessages.createPrivateMessageList(2, receiver);
        PrivateMessages.sendPrivateMessageList(privateMessages);
        PrivateMessages.removeMultipleMessagesFromOutbox(2, privateMessages);
        PrivateMessages.assertPmNotExistInOutbox(privateMessages.get(0));
        PrivateMessages.assertPmNotExistInOutbox(privateMessages.get(1));
    }

    // PM Counter tests

    @Test(groups = "ui-tests")
    public void newMessageReceived_ShouldIncrementPmCounter() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage().withReceiver(receiver);
        PrivateMessages.sendPrivateMessage(pm);
        Users.logout();
        Users.signIn(receiver);
        PrivateMessages.assertPmCounter(1);
    }

    @Test
    public void readingNewMessage_ShouldDecreaseCounter() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        List<PrivateMessage> privateMessages = PrivateMessages.createPrivateMessageList(2, receiver);
        PrivateMessages.sendPrivateMessageList(privateMessages);
        Users.logout();
        Users.signIn(receiver);
        PrivateMessages.openMessageInbox(privateMessages.get(1));
        PrivateMessages.assertPmCounter(1);
    }

    @Test
    public void deletingUnreadMessage_ShouldDecreaseCounter() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        List<PrivateMessage> privateMessages = PrivateMessages.createPrivateMessageList(2, receiver);
        PrivateMessages.sendPrivateMessageList(privateMessages);
        Users.logout();
        Users.signIn(receiver);
        PrivateMessages.removePmFromInbox(privateMessages.get(1));
        PrivateMessages.assertPmCounter(1);
    }

    @Test
    public void deletingReadMessage_ShouldNotAffectCounter() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        List<PrivateMessage> privateMessages = PrivateMessages.createPrivateMessageList(2, receiver);
        PrivateMessages.sendPrivateMessageList(privateMessages);
        Users.logout();
        Users.signIn(receiver);
        PrivateMessages.openMessageInbox(privateMessages.get(1));
        PrivateMessages.assertPmCounter(1);
        PrivateMessages.removePmFromInbox(privateMessages.get(1));
        PrivateMessages.assertPmCounter(1);
    }

    @Test
    public void deletingUnreadMessages_ShouldDecreaseCounterEqually() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        List<PrivateMessage> privateMessages = PrivateMessages.createPrivateMessageList(2, receiver);
        PrivateMessages.sendPrivateMessageList(privateMessages);
        Users.logout();
        Users.signIn(receiver);
        PrivateMessages.removeMultipleMessagesFromInbox(2, privateMessages);
        PrivateMessages.assertPmCounter(0);
    }
}
