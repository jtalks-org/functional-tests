package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.assertion.Existance;
import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @author masyan
 * @author yacov
 */
public class PMPage {

    public static final String pmInboxLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/inbox']";

    public static final String pmOutboxLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/outbox']";

    public static final String PM_DRAFTS_LINK = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/drafts']";

    public static final String pmNewMessageLinkSel = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/pm/new')]";

    public static final String toFieldSel = "recipient";

    public static final String titleFieldSel = "title";

    public static final String messageFieldSel = "tbMsg";

    public static final String DEL_BUTTON_ENABLED = "a#deleteCheckedPM.btn.btn-danger";

    public static final String DEL_BUTTON_DISABLED = "//a[@class='btn btn-danger disabled']";

    public static final String SEND_BUTTON_SEL = "post";

    public static final String pmSubjectLinksSel = "//td/a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/pm/')]";

    public static final String recipientErrorMessageSel = "recipient.errors";

    public static final String subjectErrorMessageSel = "title.errors";

    public static final String bodyMsgErrorMessageSel = "body.errors";

    public static final String replyButtonSel = "//a[@class='btn btn-primary' and contains(@href,'" + JCommuneSeleniumTest.contextPath + "/reply/')]";

    public static final String quoteButtonSel = "//a[@class='btn' and contains(@href,'" + JCommuneSeleniumTest.contextPath + "/quote/')]";

    public static final String SAVE_BUTTON = "savePM";

    public static final String draftMessageTitlesSel = "//td/a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/pm/')]";

    public static final String draftMessageEditButtonSel = "editCheckedPM";

    public static final String PM_DEL_DIALOG = "//div[@id='jqi']";

    public static final String PM_DEL_MES_BUTTON_OK_SEL = "jqi_state0_buttonOk"; // OK Button on Alert message when you delete private message

    public static final String PM_DEL_MES_BUTTON_CANCEL_SEL = "jqi_state0_buttonCancel"; // Cancel Button on Alert message when you delete private message

    public static final String draftMessageCheckboxesSel = "//input[@class='checker']";

    public static final String PM_CHECKBOXES_SEL = "//input[@class='checker']";
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
    private List<WebElement> pmSubjectLinks;

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

    @FindBy(id = SAVE_BUTTON)
    private WebElement saveButton;

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


    public PMPage(WebDriver driver) {
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

    public WebElement getTitleField() {
        return titleField;
    }

    public WebElement getMessageField() {
        return messageField;
    }

    public WebElement getSendButton() {
        return sendButton;
    }

    public List<WebElement> getPmSubjectLinks() {
        return pmSubjectLinks;
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

    /**
     * Checks whether there is a checkbox for read or unread messages (we don't know here whether the message is checked
     * since we don't create that message).
     */
    public void assertOneOfPmMessagesIsChecked(WebDriver driver) {
        try {
            Existance.assertElementExistsBySelector(driver, PM_CHECKED_UNREAD_CHECKBOX);
        } catch (AssertionError e) {
            Existance.assertElementExistsBySelector(driver, PM_CHECKED_READ_CHECKBOX);
        }
    }
}
