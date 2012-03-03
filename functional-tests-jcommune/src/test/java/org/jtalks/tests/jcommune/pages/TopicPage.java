package org.jtalks.tests.jcommune.pages;


import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.getApplicationContextPath;


/**
 * @autor masyan
 */
public class TopicPage {

	public static final String newButtonSel = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/topics/new')]";

	public static final String subjectFieldSel = "subject";

	public static final String messageFieldSel = "tbMsg";

	public static final String postButtonSel = "post";

	public static final String topicSubjectSel = "//a[contains(@class,'heading')]";

	public static final String topicMessageSel = "//div[contains(@class, 'forum_message_cell_text')]";

	public static final String backButtonSel = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/branches')]";

	public static final String topicsListSel = "//ul[@class='forum_table']/li//a[@class='forum_link']";

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

}
