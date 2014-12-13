package org.jtalks.tests.jcommune.webdriver.page;


import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.jtalks.tests.jcommune.assertion.Existence;
import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.privatemessage.PrivateMessage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;

/**
 * @author masyan
 * @author yacov
 * @author andrey pancheshenko
 */
public class PrivateMessagesPage {
    public static final String INCORRECT_TO_FIELD_ERROR = "(User not found)|(Пользователь не найден)";

    public static final String SAME_USER_IN_TO_FIELD_ERROR = "(Sending messages to yourself is not allowed)|" +
            "(Посылать сообщения самому себе - странная идея)";

    public static final String EMPTY_SUBJECT_FIELD_ERROR = "(may not be empty)|(Не может быть пустым)";

    public static final String INCORRECT_SUBJECT_LENGTH_ERROR = "(should be 2 - 120 characters)|(Должно быть 2 - 120 символов)";

    public static final String EMPTY_CONTENT_FIELD_ERROR = "(may not be empty\nshould be 2 - 20000 characters)|" +
            "(should be 2 - 20000 characters\nmay not be empty)|" +
            "(Не может быть пустым\nДолжно быть 2 - 20000 символов)|" +
            "(Должно быть 2 - 20000 символов\nНе может быть пустым)";

    public static final String INCORRECT_LENGTH_CONTENT_FIELD = "(should be 2 - 20000 characters)|(Должно быть 2 - 20000 символов)";

    public static final String pmInboxLinkSel = "//li[@id='inbox_link']/a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/inbox']";

    public static final String pmOutboxLinkSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/outbox']";

    public static final String PM_DRAFTS_LINK = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/drafts']";

    public static final String pmNewMessageLinkSel = "//a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/pm/new')]";

    public static final String toFieldSel = "recipient";

    public static final String titleFieldSel = "title";

    public static final String messageFieldSel = "postBody";

    public static final String DEL_BUTTON_ENABLED = "a#deleteCheckedPM.btn.btn-danger";

    public static final String DEL_BUTTON_DISABLED = "//a[@class='btn btn-danger disabled']";

    public static final String SEND_BUTTON_SEL = "post";

    public static final String pmSubjectLinksSel = "//td/a[contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/pm/')]";

    public static final String pmPaginationSel = "a[contains(@href,'?page=')]";

    public static final String recipientErrorMessageSel = "recipient.errors";

    public static final String subjectErrorMessageSel = "title.errors";

    public static final String bodyMsgErrorMessageSel = "body.errors";

    public static final String replyButtonSel = "//a[@class='btn btn-primary' and contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/reply/')]";

    public static final String quoteButtonSel = "//a[@class='btn' and contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/quote/')]";

    public static final String draftMessageTitlesSel = "//td/a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/pm/')]";

    public static final String draftMessageEditButtonSel = "editCheckedPM";

    public static final String PM_DEL_DIALOG = "//div[@id='jqi']";

    public static final String PM_DEL_MES_BUTTON_OK_SEL = "jqi_state0_buttonOk"; // OK Button on Alert message when you delete private message

    public static final String PM_DEL_MES_BUTTON_CANCEL_SEL = "jqi_state0_buttonCancel"; // Cancel Button on Alert message when you delete private message

    public static final String draftMessageCheckboxesSel = "//input[@class='checker']";

    public static final String PM_CHECKBOXES_SEL = "//input[@class='checker']";

    public static final String PM_LIST_SINGLE_MESSAGE_LINE_SEL = "//tr[@class='mess']";
    /**
     * If checkbox is checked and the message is unread, then the class of checkbox is next.
     */
    public static final String PM_CHECKED_UNREAD_CHECKBOX = "//tr[@class='mess pm_unread check']";
    /**
     * If checkbox is checked and the message is already read.
     */
    public static final String PM_CHECKED_READ_CHECKBOX = "//tr[@class='mess check']";

    public static final String pmHeadingOutboxSel = "//li[@id='outbox_link' and @class='active']";

    public static final String recepientsListSel = "//td[@class='pm_user_to_from']/a";

    public static final String counterSel = "test-pm-count";

    @FindBy(xpath = pmInboxLinkSel)
    private WebElement pmInboxLink;

    @FindBy(xpath = pmOutboxLinkSel)
    private WebElement pmOutboxLink;

    @FindBy(xpath = PM_DRAFTS_LINK)
    private WebElement pmDraftsLink;

    @FindBy(xpath = pmNewMessageLinkSel)
    private WebElement pmNewMessageLink;

    @FindBy(id = toFieldSel)
    private WebElement toField;

    @FindBy(id = titleFieldSel)
    private WebElement titleField;

    @FindBy(id = messageFieldSel)
    private WebElement messageField;

    @FindBy(id = PM_DEL_DIALOG)
    private WebElement pmDelAlertMessage;

    @FindBy(id = PM_DEL_MES_BUTTON_CANCEL_SEL)
    private WebElement pmDelMesButtonCancel;

    @FindBy(id = PM_DEL_MES_BUTTON_OK_SEL)
    private WebElement pmDelMesButtonOK;

    @FindBy(id = SEND_BUTTON_SEL)
    private WebElement sendButton;

    @FindBy(css = DEL_BUTTON_ENABLED)
    private WebElement delButton;

    @FindBy(xpath = pmSubjectLinksSel)
    private WebElement pmSubjectLink;

    @FindBy (xpath = pmPaginationSel)
    private List<WebElement> pmPaginationList;

    @FindBy(id = recipientErrorMessageSel)
    private WebElement recipientErrorMessage;

    @FindBy(id = subjectErrorMessageSel)
    private WebElement subjectErrorMessage;

    @FindBy(id = bodyMsgErrorMessageSel)
    private WebElement bodyMsgErrorMessage;

    @FindBy(xpath = replyButtonSel)
    private WebElement replyButton;

    @FindBy(xpath = quoteButtonSel)
    private WebElement quoteButton;

    @FindBy(id = "savePM")
    private WebElement saveButton;

    @FindBy(xpath = "//input[@class='btn margin-left-big disabled']")
    private WebElement saveButtonDisabled;

    @FindBy(xpath = draftMessageTitlesSel)
    private List<WebElement> draftMessageTitles;

    @FindBy(id = draftMessageEditButtonSel)
    private WebElement draftMessageEditButton;

    @FindBy(xpath = pmHeadingOutboxSel)
    private WebElement pmHeadingOutbox;

    @FindBy(xpath = draftMessageCheckboxesSel)
    private List<WebElement> draftMessageCheckboxes;

    @FindBy(xpath = PM_CHECKBOXES_SEL)
    private List<WebElement> inboxCheckboxes;

    @FindBy(xpath = PM_CHECKED_UNREAD_CHECKBOX + "//input[@type='checkbox']")
    public WebElement checkedCheckbox;

    @FindBy(xpath = recepientsListSel)
    private List<WebElement> recepientsList;

    @FindBy(className = "mess")
    private List<WebElement> messageList;

    @FindBy(className = "checker")
    private WebElement singlePmCheckbox;

    @FindBy(id = "remove-pm-ok")
    private WebElement okButtonRemovingPmDialog;

    @FindBy(className = counterSel)
    private WebElement messageCounter;

    public PrivateMessagesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Getters
    public WebElement getPmInboxLink() {
        return pmInboxLink;
    }

    public WebElement getPmOutboxLink() {
        return pmOutboxLink;
    }

    public WebElement getPmDraftsLink() {
        return pmDraftsLink;
    }

    public WebElement getPmNewMessageLink() {
        return pmNewMessageLink;
    }

    public WebElement getToField() {
        return toField;
    }

    @Step
    public void fillPrivateMessageFields(PrivateMessage pm) {
        info("Start filling PM fields.");
        fillToField(pm.getReceiver().getUsername());
        fillTitleField(pm.getMessageSubject());
        fillMessageField(pm.getMessageContent());
    }

    @Step
    private void fillToField(String username) {
        info("Filling 'To' field: [" + username + "]");
        this.toField.sendKeys(username);
    }

    @Step
    private void fillTitleField(String title) {
        info("Filling 'Title' field: [" + title + "]");
        this.titleField.sendKeys(title);
    }

    @Step
    private void fillMessageField(String message) {
        info("Filling 'Message' field [" + StringUtils.left(message, 25) + "]");
        for (String token : Splitter.fixedLength(100).split(message)) {
            this.messageField.sendKeys(token);
        }
    }

    @Step
    public void clearPrivateMessageFields() {
        info("Clearing all message fields.");
        this.toField.clear();
        this.titleField.clear();
        this.messageField.clear();
    }

    public WebElement findPmBySubject(String subject) {
        info("Looking for PM with subject: [" + subject + "]");
        List<WebElement> pmList = getPmList();
        for(WebElement singlePmRow : pmList) {
            WebElement pmLink = singlePmRow.findElements(By.cssSelector("a")).get(1);
            String messageSubject = pmLink.getText();
            info("Found subject in the list: [" + StringUtils.left(messageSubject, 7) + "...]");
            if(messageSubject.equals(subject)) {
                return singlePmRow;
            }
        }
        throw new NoSuchElementException("The PM with subject: [" + subject + "] doesn't exist.");
    }

    public WebElement getTitleField() {
        return titleField;
    }

    public WebElement getMessageField() {
        return messageField;
    }

    public WebElement getSendButton() {
        return sendButton;
    }

    public WebElement getPmSubjectLink() {
        return pmSubjectLink;
    }

    public WebElement getRecipientErrorMessage() {
        return recipientErrorMessage;
    }

    public WebElement getSubjectErrorMessage() {
        return subjectErrorMessage;
    }

    public WebElement getBodyMsgErrorMessage() {
        return bodyMsgErrorMessage;
    }

    public WebElement getPmDelAlertMessage() {
        return pmDelAlertMessage;
    }

    public WebElement getPmDelMesButtonOK() {
        return pmDelMesButtonOK;
    }

    public WebElement getPmDelMesButtonCancel() {
        return pmDelMesButtonCancel;
    }

    public WebElement getReplyButton() {
        return replyButton;
    }

    public WebElement getQuoteButton() {
        return quoteButton;
    }

    public WebElement getSaveButton() {
        return saveButton;
    }

    public WebElement getSaveButtonDisabled() {
        return saveButtonDisabled;
    }

    public boolean isSaveButtonEnabled() {
        info("Checking that the save button exists.");
        if (Existence.existsImmediately(driver, getSaveButtonDisabled())) {
            info("The save button disabled.");
            return false;
        } else if (Existence.existsImmediately(driver, getSaveButton())) {
            info("The save button exists and enabled.");
            return true;
        } else {
            throw new NoSuchElementException("Save button wasn't found.");
        }
    }

    public List<WebElement> getDraftMessageTitles() {
        return draftMessageTitles;
    }

    public WebElement getDraftMessageEditButton() {
        return draftMessageEditButton;
    }

    public WebElement getDelButton() {
        return delButton;
    }
    //*[@id="deleteCheckedPM"]

    public WebElement getPmHeadingOutbox() {
        return pmHeadingOutbox;
    }

    public List<WebElement> getDraftMessageCheckboxes() {
        return draftMessageCheckboxes;
    }

    public List<WebElement> getInboxCheckboxes() {
        return inboxCheckboxes;
    }

    public WebElement getCheckedCheckbox() {
        return checkedCheckbox;
    }

    public List<WebElement> getRecepientsList() {
        return recepientsList;
    }

    public List<WebElement> getPmList() {return messageList; }

    public WebElement getSinglePmCheckbox() {return singlePmCheckbox;}

    public WebElement getOkButtonRemovingPmDialog() {return okButtonRemovingPmDialog;}
    /**
     * Checks whether there is a checkbox for read or unread messages (we don't know here whether the message is checked
     * since we don't create that message).
     */
    public void assertOneOfPmMessagesIsChecked(WebDriver driver) {
        try {
            Existence.assertElementExistsBySelector(driver, PM_CHECKED_UNREAD_CHECKBOX);
        } catch (AssertionError e) {
            Existence.assertElementExistsBySelector(driver, PM_CHECKED_READ_CHECKBOX);
        }
    }

    public void clickComposeMessage() {
        info("Clicking compose button");
        getPmNewMessageLink().click();
    }

    public void clickOpenOutboxMessages() {
        info("Opening outbox folder");
        getPmOutboxLink().click();
    }

    public void clickOpenDrafts() {
        info("Opening drafts folder");
        getPmDraftsLink().click();
    }

    public void clickSendButton() {
        info("Trying to send private message");
        getSendButton().click();
    }

    public void clickSaveButton() {
        info("Clicking Save PM button");
        getSaveButton().click();
    }

    public void clickCheckbox(WebElement pmRow) {
        info("Clicking on the checkbox");
        pmRow.findElement(By.className("checker")).click();
    }

    public void clickDelButton() {
        info("Clicking delete button");
        getDelButton().click();
    }

    public void clickOkButtonRemovingPmDialog() {
        info("Clicking ok button on the removing dialog");
        getOkButtonRemovingPmDialog().click();
    }

    public int getMessagesCounter() {
        try {
            return Integer.parseInt(messageCounter.getText());
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Couldn't convert counter from string to integer!");
        }
    }
}
