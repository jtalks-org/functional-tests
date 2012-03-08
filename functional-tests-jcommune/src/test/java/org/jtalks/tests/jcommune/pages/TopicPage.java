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
public class TopicPage {

	public static final String newButtonSel = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/topics/new')]";

	public static final String subjectFieldSel = "subject";

	public static final String messageFieldSel = "tbMsg";

	public static final String postButtonSel = "post";

	public static final String topicSubjectSel = "//h2[contains(@class,'heading')]";

	public static final String topicMessageSel = "//div[contains(@class, 'forum_message_cell_text')]";

	public static final String backButtonSel = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/branches')]";

	public static final String topicsListSel = "//ul[@class='forum_table']/li//a[@class='forum_link']";

	public static final String subjectErrorMessageSel = "//span[@class='error' and @id='subject']";

	public static final String bodyErrorMessageSel = "//span[@class='error' and @id='bodyText.errors']";


	@FindBy(xpath = newButtonSel)
	private WebElement newButton;

	@FindBy(id = subjectFieldSel)
	private WebElement subjectField;

	@FindBy(id = messageFieldSel)
	private WebElement messageField;

	@FindBy(id = postButtonSel)
	private WebElement postButton;

	@FindBy(xpath = topicSubjectSel)
	private WebElement topicSubject;

	@FindBy(xpath = topicMessageSel)
	private WebElement topicMessage;

	@FindBy(xpath = backButtonSel)
	private WebElement backButton;

	@FindBy(xpath = topicsListSel)
	private List<WebElement> topicsList;

	@FindBy(xpath = subjectErrorMessageSel)
	WebElement subjectErrorMessage;

	@FindBy(xpath = bodyErrorMessageSel)
	WebElement bodyErrorMessage;


	public TopicPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	//Getters
	public WebElement getNewButton() {
		return newButton;
	}

	public WebElement getSubjectField() {
		return subjectField;
	}

	public WebElement getMessageField() {
		return messageField;
	}

	public WebElement getPostButton() {
		return postButton;
	}

	public WebElement getTopicSubject() {
		return topicSubject;
	}

	public WebElement getTopicMessage() {
		return topicMessage;
	}

	public WebElement getBackButton() {
		return backButton;
	}

	public List<WebElement> getTopicsList() {
		return topicsList;
	}

	public WebElement getSubjectErrorMessage() {
		return subjectErrorMessage;
	}

	public WebElement getBodyErrorMessage() {
		return bodyErrorMessage;
	}
}
