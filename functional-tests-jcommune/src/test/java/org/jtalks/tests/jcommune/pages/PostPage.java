package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


/**
 * @autor masyan
 */
public class PostPage {

	public static final String newButtonSel = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/posts/new')]";

	public static final String messageFieldSel = "tbMsg";

	public static final String postButtonSel = "post";

	public static final String lastPostMessageSel = "//li[@class='forum_row'][last()]//div[@class='forum_message_cell_text']";

	public static final String backButtonSel = "back";

	public static final String deleteButtonNearLastPostSel = "//li[@class='forum_row'][last()]//a[@class='button delete']";

	public static final String linkButtonNearLastPostSel = "//li[@class='forum_row'][last()]//a[@class='button postLink']";

	public static final String deleteTopicButtonSel = "//li[@class='forum_row']//a[@class='button delete']";

	public static final String deleteConfirmOkButtonSel = "jqi_state0_buttonOk";

	public static final String deleteConfirmCancelButtonSel = "jqi_state0_buttonCancel";

	public static final String postErrorMessageSel = "bodyText.errors";

	public static final String postsListSel = "//li[@class='forum_row']";

	public static final String postsMessagesSel = "//div[@class='forum_message_cell_text']";

	public static final String editPostButtonSel = "//a[@class='button' and contains(@href, 'posts') and contains(@href, 'edit')]";

	public static final String editTopicButtonSel = "//a[@class='button' and contains(@href, 'topics') and contains(@href, 'edit')]";

	public static final String permanentUrlToPostSel = "//div[@class='jqimessage']";

	public static final String lastPostLinksFromBranchSel = "//div[@class='forum_last_message']/a[contains(@href," +
			"'" + JCommuneSeleniumTest.contextPath + "/posts/')]";

	public static final String lastPostLinksFromTopicSel = "//div[@class='forum_last_message']/a[contains(@href," +
			"'" + JCommuneSeleniumTest.contextPath + "/posts/')]";

	//public static final String permanentUrlToPostSel = "//div[@class='jqimessage']";

	@FindBy(xpath = newButtonSel)
	private WebElement newButton;

	@FindBy(id = messageFieldSel)
	private WebElement messageField;

	@FindBy(id = postButtonSel)
	private WebElement postButton;

	@FindBy(xpath = lastPostMessageSel)
	private WebElement lastPostMessage;

	@FindBy(id = backButtonSel)
	private WebElement backButton;

	@FindBy(xpath = deleteButtonNearLastPostSel)
	private WebElement deleteButtonNearLastPost;

	@FindBy(xpath = linkButtonNearLastPostSel)
	private WebElement linkButtonNearLastPost;

	@FindBy(id = deleteConfirmOkButtonSel)
	private WebElement deleteConfirmOkButton;

	@FindBy(id = deleteConfirmCancelButtonSel)
	private WebElement deleteConfirmCancelButton;

	@FindBy(id = postErrorMessageSel)
	private WebElement postErrorMessage;

	@FindBy(xpath = postsListSel)
	private List<WebElement> postsList;

	@FindBy(xpath = postsMessagesSel)
	private List<WebElement> postsMessages;

	@FindBy(xpath = deleteTopicButtonSel)
	private WebElement deleteTopicButton;

	@FindBy(xpath = editPostButtonSel)
	private WebElement editPostButton;

	@FindBy(xpath = editTopicButtonSel)
	private WebElement editTopicButton;

	@FindBy(xpath = permanentUrlToPostSel)
	private WebElement permanentUrlToPost;

	@FindBy(xpath = lastPostLinksFromBranchSel)
	private List<WebElement> lastPostLinksFromBranch;

	@FindBy(xpath = lastPostLinksFromTopicSel)
	private List<WebElement> lastPostLinksFromTopic;


	public PostPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}


	//Getters
	public WebElement getNewButton() {
		return newButton;
	}

	public WebElement getMessageField() {
		return messageField;
	}

	public WebElement getPostButton() {
		return postButton;
	}

	public WebElement getLastPostMessage() {
		return lastPostMessage;
	}

	public WebElement getBackButton() {
		return backButton;
	}

	public WebElement getDeleteButtonNearLastPost() {
		return deleteButtonNearLastPost;
	}

	public WebElement getLinkButtonNearLastPost() {
		return linkButtonNearLastPost;
	}

	public WebElement getDeleteConfirmOkButton() {
		return deleteConfirmOkButton;
	}

	public WebElement getDeleteConfirmCancelButton() {
		return deleteConfirmCancelButton;
	}

	public WebElement getPostErrorMessage() {
		return postErrorMessage;
	}

	public List<WebElement> getPostsList() {
		return postsList;
	}

	public List<WebElement> getPostsMessages() {
		return postsMessages;
	}

	public WebElement getDeleteTopicButton() {
		return deleteTopicButton;
	}

	public WebElement getEditPostButton() {
		return editPostButton;
	}

	public WebElement getEditTopicButton() {
		return editTopicButton;
	}

	public WebElement getPermanentUrlToPost() {
		return permanentUrlToPost;
	}

	public List<WebElement> getLastPostLinksFromBranch() {
		return lastPostLinksFromBranch;
	}

	public List<WebElement> getLastPostLinksFromTopic() {
		return lastPostLinksFromTopic;
	}
}
