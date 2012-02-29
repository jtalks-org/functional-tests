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
public class TopicPage implements Page {


	@FindBy(xpath = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/topics/new')]")
	private WebElement newButton;

	@FindBy(id = "subject")
	private WebElement subjectField;

	@FindBy(id = "tbMsg")
	private WebElement messageField;

	@FindBy(id = "post")
	private WebElement postButton;

	@FindBy(xpath = "//a[contains(@class,'heading')]")
	private WebElement topicSubject;

	@FindBy(xpath = "//div[contains(@class, 'forum_message_cell_text')]")
	private WebElement topicMessage;


	@FindBy(xpath = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/branches')]")
	private WebElement backButton;

	@FindBy(xpath = "//ul[@class='forum_table']/li//a[@class='forum_link']")
	private List<WebElement> topicsList;


	public void init(WebDriver driver) {
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
