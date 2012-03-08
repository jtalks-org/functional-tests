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

	public static final String deleteTopicButtonSel = "//li[@class='forum_row']//a[@class='button delete']";

	public static final String deleteConfirmOkButtonSel = "jqi_state0_buttonOk";

	public static final String deleteConfirmCancelButtonSel = "jqi_state0_buttonCancel";

	public static final String postErrorMessageSel = "bodyText.errors";

	public static final String postsListSel = "//li[@class='forum_row']";

	public static final String editPostButtonSel = "//a[@class='button' and contains(@href, 'posts') and contains(@href, 'edit')]";

	public static final String editTopicButtonSel = "//a[@class='button' and contains(@href, 'topics') and contains(@href, 'edit')]";

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

	@FindBy(id = deleteConfirmOkButtonSel)
	private WebElement deleteConfirmOkButton;

	@FindBy(id = deleteConfirmCancelButtonSel)
	private WebElement deleteConfirmCancelButton;

	@FindBy(id = postErrorMessageSel)
	private WebElement postErrorMessage;

	@FindBy(xpath = postsListSel)
	private List<WebElement> postsList;

	@FindBy(xpath = deleteTopicButtonSel)
	private List<WebElement> deleteTopicButton;

	@FindBy(xpath = editPostButtonSel)
	private List<WebElement> editPostButton;

	@FindBy(xpath = editTopicButtonSel)
	private List<WebElement> editTopicButton;


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

	public List<WebElement> getDeleteTopicButton() {
		return deleteTopicButton;
	}

	public List<WebElement> getEditPostButton() {
		return editPostButton;
	}

	public List<WebElement> getEditTopicButton() {
		return editTopicButton;
	}
}
