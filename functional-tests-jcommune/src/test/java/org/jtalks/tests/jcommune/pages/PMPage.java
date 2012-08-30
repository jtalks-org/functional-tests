package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @author masyan
 */
public class PMPage {

	public static final String pmInboxLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/inbox']";

	public static final String pmOutboxLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/outbox']";

	public static final String pmNewMessageLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/pm/new']";

	public static final String toFieldSel = "recipient";

	public static final String titleFieldSel = "title";

	public static final String messageFieldSel = "tbMsg";
	
	public static final String delButtonSel = "deleteCheckedPM";

	public static final String sendButtonSel = "post";

	public static final String pmSubjectLinksSel = "//td/a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/pm/')]";

	public static final String recipientErrorMessageSel = "recipient.errors";

	public static final String subjectErrorMessageSel = "title.errors";

	public static final String bodyMsgErrorMessageSel = "body.errors";

	public static final String replyButtonSel = "//a[@class='btn btn-primary' and contains(@href,'" + JCommuneSeleniumTest.contextPath + "/reply/')]";

	public static final String quoteButtonSel = "//a[@class='btn' and contains(@href,'" + JCommuneSeleniumTest.contextPath + "/quote/')]";

	public static final String saveButtonSel = "save_pm";

	public static final String draftMessageTitlesSel = "//td/a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/pm/')]";

	public static final String draftMessageEditButtonSel = "editCheckedPM";

    public static final String jqiMessageSel = "jqi"; //Alert message when you delete private message

    public static final String jqiButtonOKSel = "jqi_state0_buttonOk"; // OK Button on Alert message when you delete private message

    public static final String jqiButtonCancelSel = "jqi_state0_buttonCancel"; // Cancel Button on Alert message when you delete private message

	public static final String draftMessageCheckboxesSel = "//input[@class='checker']";
	
	public static final String inboxCheckboxesSel = "//input[@type='checkbox' AND @class='checker']";

    public static final String сheckedCheckboxSel = "//input[@class='mess check']";
	
	public static final String pmHeadingOutboxSel = "//li[@id='outbox_link' and @class='active']";

	public static final String recepientsListSel = "//td[@class='pm_user_to_from']/a";

	@FindBy(xpath = pmInboxLinkSel)
	private WebElement pmInboxLink;

	@FindBy(xpath = pmOutboxLinkSel)
	private WebElement pmOutboxLink;

	@FindBy(xpath = pmNewMessageLinkSel)
	private WebElement pmNewMessageLink;

	@FindBy(id = toFieldSel)
	private WebElement toField;

	@FindBy(id = titleFieldSel)
	private WebElement titleField;

	@FindBy(id = messageFieldSel)
	private WebElement messageField;

	@FindBy(id = jqiMessageSel)
	private WebElement jqiMessage;

    @FindBy(id = jqiButtonCancelSel)
    private WebElement jqiButtonCancel;

    @FindBy(id = jqiButtonOKSel)
    private WebElement jqiButtonOK;

    @FindBy(id = sendButtonSel)
    private WebElement sendButton;

	@FindBy(id = delButtonSel)
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

	@FindBy(id = saveButtonSel)
	private WebElement saveButton;

	@FindBy(xpath = draftMessageTitlesSel)
	private List<WebElement> draftMessageTitles;

	@FindBy(id = draftMessageEditButtonSel)
	private WebElement draftMessageEditButton;

	@FindBy(xpath = pmHeadingOutboxSel)
	private WebElement pmHeadingOutbox;

	@FindBy(xpath = draftMessageCheckboxesSel)
	private List<WebElement> draftMessageCheckboxes;

	@FindBy(xpath = inboxCheckboxesSel)
	private List<WebElement> inboxCheckboxes;

    @FindBy(xpath = сheckedCheckboxSel)
    private WebElement checkedCheckbox;
	
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

	public List <WebElement> getPmSubjectLinks() {
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

	public WebElement getJqiMessage() {
		return jqiMessage;
	}

    public WebElement getJqiButtonOK() {
        return jqiButtonOK;
    }

    public WebElement getJqiButtonCancel() {
        return jqiButtonCancel;
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
}
