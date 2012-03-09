package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @autor masyan
 */
public class PMPage {

	public static final String pmInboxLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/inbox']";

	public static final String pmOutboxLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/outbox']";

	public static final String pmNewMessageLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/pm/new']";

	public static final String toFieldSel = "recipient";

	public static final String titleFieldSel = "title";

	public static final String messageFieldSel = "tbMsg";

	public static final String sendButtonSel = "post";


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

	@FindBy(id = sendButtonSel)
	private WebElement sendButton;


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
}
