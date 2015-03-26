/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jtalks.tests.jcommune.webdriver.action;

import junit.framework.AssertionFailedError;
import org.apache.commons.lang3.StringUtils;
import org.jtalks.tests.jcommune.assertion.Existence;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.entity.privatemessage.PrivateMessage;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.ArrayList;
import java.util.List;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;

/**
 * Contains actions for private messages
 *
 * @author Varro
 * @author andrey pancheshenko
 */
public class PrivateMessages {
    private static final Logger LOGGER = LoggerFactory.getLogger(Topics.class);

    private static void composeMessage(PrivateMessage pm) {
        mainPage.openPrivateMessages();
        pmPage.clickComposeMessage();
        pmPage.fillPrivateMessageFields(pm);
    }

    private static void openDraft(PrivateMessage pm) {
        mainPage.openPrivateMessages();
        pmPage.clickOpenDrafts();
        if (!openMessageOnCurrentPage(pm)) {
            throw new AssertionFailedError("The draft is not present in drafts folder. " +
                    "Receiver: [" + pm.getReceiver() + "], " +
                    "Subject: [" + StringUtils.left(pm.getMessageSubject(), 10) + "], " +
                    "Content: [" + StringUtils.left(pm.getMessageContent(), 10) + "]");
        }
    }

    private static boolean openMessageOnCurrentPage(PrivateMessage pm) {
        try {
            WebElement pmLink = pmPage.findPmBySubject(pm.getMessageSubject()).findElements(By.cssSelector("a")).get(1);
            pmLink.click();
            return true;
        } catch (NoSuchElementException e) {
            info("Expected PM wasn't found");
            return false;
        }
    }

    private static void removePm(PrivateMessage pm) {
        String subject = pm.getMessageSubject();
        info("Removing Private Message: " + pm);
        try {
            WebElement pmRow = pmPage.findPmBySubject(subject);
            info("The private message for deleting with subject [" + subject + "] was found!");
            pmPage.clickCheckbox(pmRow);
            pmPage.clickDelButton();
            pmPage.clickOkButtonRemovingPmDialog();
            info("The private message had to be deleted");
            return;
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("Can't delete private message because it's not present on the page: " + pm, e);
        }
    }

    private static void removeMultiplePm(int messageCount, List<PrivateMessage> pmList) {
        info("Clicking on the checkboxes near messages");
        for (int i = 0; i < messageCount; i++) {
            String subject = pmList.get(i).getMessageSubject();
            try {
                WebElement pmRow = pmPage.findPmBySubject(subject);
                info("The private message for deleting with subject [" + subject + "] was found! Clicking on the checkbox.");
                pmPage.clickCheckbox(pmRow);
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("Can't find private message on the page: " + pmList.get(i), e);
            }
        }
        pmPage.clickDelButton();
        pmPage.clickOkButtonRemovingPmDialog();
        info("The private messages had to be deleted");
        return;
    }

    @Step
    public static List<PrivateMessage> createPrivateMessageList(int messageCount, User receiver) {
        List<PrivateMessage> messageList = new ArrayList<PrivateMessage>();
        info("Creating " + messageCount + " private messages.");
        for (int i = 0; i < messageCount; i++) {
            info("Creating private message #" + (i + 1));
            messageList.add(new PrivateMessage().withReceiver(receiver));
        }
        return messageList;
    }

    @Step
    public static void sendPrivateMessage(PrivateMessage pm) throws ValidationException {
        composeMessage(pm);
        pmPage.clickSendButton();
        assertFormValid();
        info("Private message had to be sent");
    }

    @Step
    public static void sendPrivateMessageList(List<PrivateMessage> pmList) {
        int messageCount = pmList.size();
        info("Sending " + messageCount + " private messages.");
        for (int i = 0; i < messageCount; i++) {
            info("Sending PM #" + (i + 1));
            sendPrivateMessage(pmList.get(i));
        }
    }

    @Step
    public static void savePrivateMessageAsDraft(PrivateMessage pm) throws NoSuchElementException, ValidationException {
        composeMessage(pm);
        if (pmPage.isSaveButtonEnabled()) {
            try {
                pmPage.clickSaveButton();
            } catch (NoSuchElementException e) {
                throw new NoSuchElementException("Couldn't find Save button.", e);
            }
        }
        assertFormValid();
        info("Draft had to be saved");
    }

    @Step
    public static void removePmFromInbox(PrivateMessage pm) {
        info("Start to remove PM from inbox folder");
        mainPage.openPrivateMessages();
        removePm(pm);
    }

    @Step
    public static void removePmFromOutbox(PrivateMessage pm) {
        info("Start to remove PM from outbox folder");
        mainPage.openPrivateMessages();
        pmPage.clickOpenOutboxMessages();
        removePm(pm);
    }

    @Step
    public static void removePmFromDrafts(PrivateMessage pm) {
        info("Start to remove PM from drafts folder");
        mainPage.openPrivateMessages();
        pmPage.clickOpenDrafts();
        removePm(pm);
    }

    @Step
    public static void removeMultipleMessagesFromInbox(int messageCount, List<PrivateMessage> pmList) {
        info("Start to remove " + messageCount + " messages from inbox folder.");
        if (messageCount > pmList.size()) {
            throw new AssertionFailedError("The number of messages to be removed exceeds the number of created messages.");
        }
        mainPage.openPrivateMessages();
        removeMultiplePm(messageCount, pmList);
    }

    @Step
    public static void removeMultipleMessagesFromOutbox(int messageCount, List<PrivateMessage> pmList) {
        info("Start to remove " + messageCount + " messages from outbox folder.");
        if (messageCount > pmList.size()) {
            throw new AssertionFailedError("The number of messages to be removed exceeds the number of created messages.");
        }
        mainPage.openPrivateMessages();
        pmPage.clickOpenOutboxMessages();
        removeMultiplePm(messageCount, pmList);
    }

    @Step
    public static void openMessageInbox(PrivateMessage pm) {
        mainPage.openPrivateMessages();
        openMessageOnCurrentPage(pm);
    }

    @Step
    public static void editDraft(PrivateMessage pmCurrent, PrivateMessage pmNew)
            throws AssertionFailedError, NoSuchElementException, ValidationException {
        openDraft(pmCurrent);
        pmPage.clearPrivateMessageFields();
        pmPage.fillPrivateMessageFields(pmNew);
        if (pmPage.isSaveButtonEnabled()) {
            pmPage.clickSaveButton();
        } else {
            info("The save button is disabled. No permissions?");
            throw new PermissionsDeniedException("The save button is disabled. No permissions?");
        }
        assertFormValid();
        info("Draft had to be edited and saved");
    }

    // Assertions

    @Step
    public static void assertPmExistsInRemoteInbox(User receiver, PrivateMessage pm) {
        Users.logout();
        Users.signIn(receiver);
        info("Asserting that PM was received");
        mainPage.openPrivateMessages();
        if(!pmExists(pm)) {
            throw new AssertionFailedError("The PM is not present in inbox folder. " +
                    "Receiver: [" + pm.getReceiver() + "], " +
                    "Subject: [" + StringUtils.left(pm.getMessageSubject(), 10) + "], " +
                    "Content: [" + StringUtils.left(pm.getMessageContent(), 10) + "]");
        }
        info("Success. PM exists in inbox folder.");
    }

    @Step
    public static void assertPmExistsInOutbox(PrivateMessage pm) {
        info("Asserting that sent PM was saved in outbox");
        mainPage.openPrivateMessages();
        pmPage.clickOpenOutboxMessages();
        if(!pmExists(pm)) {
            throw new AssertionFailedError("The PM is not present in outbox folder. " +
                    "Receiver: [" + pm.getReceiver() + "], " +
                    "Subject: [" + StringUtils.left(pm.getMessageSubject(), 10) + "], " +
                    "Content: [" + StringUtils.left(pm.getMessageContent(), 10) + "]");
        }
        info("Success. PM exists in outbox folder.");
    }

    @Step
    public static void assertPmExistsInDraft(PrivateMessage pm) {
        info("Asserting that draft was really saved");
        mainPage.openPrivateMessages();
        pmPage.clickOpenDrafts();
        if(!pmExists(pm)) {
            throw new AssertionFailedError("The draft is not present in drafts folder. " +
                    "Receiver: [" + pm.getReceiver() + "], " +
                    "Subject: [" + StringUtils.left(pm.getMessageSubject(), 10) + "], " +
                    "Content: [" + StringUtils.left(pm.getMessageContent(), 10) + "]");
        }
        info("Success. PM exists in drafts folder.");
    }

    @Step
    public static void assertPmNotExistInInbox(PrivateMessage pm) {
        info("Asserting that PM doesn't exist in inbox folder");
        mainPage.openPrivateMessages();
        if(pmExists(pm)) {
            throw new AssertionFailedError("The PM is present in inbox folder. " +
                    "Receiver: [" + pm.getReceiver() + "], " +
                    "Subject: [" + StringUtils.left(pm.getMessageSubject(), 10) + "], " +
                    "Content: [" + StringUtils.left(pm.getMessageContent(), 10) + "]");
        }
        info("Success. PM doesn't exist in inbox folder.");
    }

    @Step
    public static void assertPmNotExistInOutbox(PrivateMessage pm) {
        info("Asserting that PM doesn't exist in outbox folder");
        mainPage.openPrivateMessages();
        pmPage.clickOpenOutboxMessages();
        if(pmExists(pm)) {
            throw new AssertionFailedError("The PM is present in outbox folder. " +
                    "Receiver: [" + pm.getReceiver() + "], " +
                    "Subject: [" + StringUtils.left(pm.getMessageSubject(), 10) + "], " +
                    "Content: [" + StringUtils.left(pm.getMessageContent(), 10) + "]");
        }
        info("Success. PM doesn't exist in outbox folder.");
    }

    @Step
    public static void assertPmNotExistInDrafts(PrivateMessage pm) {
        info("Asserting that PM doesn't exist in drafts folder");
        mainPage.openPrivateMessages();
        pmPage.clickOpenDrafts();
        if(pmExists(pm)) {
            throw new AssertionFailedError("The PM is present in drafts folder. " +
                    "Receiver: [" + pm.getReceiver() + "], " +
                    "Subject: [" + StringUtils.left(pm.getMessageSubject(), 10) + "], " +
                    "Content: [" + StringUtils.left(pm.getMessageContent(), 10) + "]");
        }
        info("Success. PM doesn't exist in drafts folder.");
    }

    @Step
    public static void assertPmCounter(int expectedCounter) {
        int actualCounter = pmPage.getMessagesCounter();
        info("Checking PM counter. Expected: " + expectedCounter + ", actual: " + actualCounter);
        if (expectedCounter != actualCounter) {
            throw new AssertionFailedError("Actual PM counter differs from the expected value");
        }
        info("Success. Expected counter equals actual number.");
    }

    private static void assertFormValid() throws ValidationException {
        info("Check there are no validation errors...");
        String failedFields = "";
        if (Existence.existsImmediately(driver, pmPage.getRecipientErrorMessage())) {
            WebElement recipientError = pmPage.getRecipientErrorMessage();
            failedFields += recipientError.getText();
        }
        if (Existence.existsImmediately(driver, pmPage.getSubjectErrorMessage())) {
            WebElement subjectError = pmPage.getSubjectErrorMessage();
            failedFields += subjectError.getText();
        }
        if (Existence.existsImmediately(driver, pmPage.getBodyMsgErrorMessage())) {
            WebElement bodyMsgError = pmPage.getBodyMsgErrorMessage();
            failedFields += bodyMsgError.getText();
        }
        if (StringUtils.isNotBlank(failedFields)) {
            info("Validation failed!");
            throw new ValidationException(failedFields);
        }
        info("Validation completed successfully! No errors found.");
    }

    private static boolean pmExists(PrivateMessage pm) {
        try {
            pmPage.findPmBySubject(pm.getMessageSubject());
            info("The message exists.");
            return true;
        } catch (NoSuchElementException e) {
            info("Expected PM wasn't found");
            return false;
        }
    }
}
