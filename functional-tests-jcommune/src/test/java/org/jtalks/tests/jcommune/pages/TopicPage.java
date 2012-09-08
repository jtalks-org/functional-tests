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
public class TopicPage {

    public static final String newButtonSel = "new-topic-btn";

    public static final String subjectFieldSel = "subject";

    public static final String messageFieldSel = "tbMsg";

    public static final String postButtonSel = "post";

    public static final String topicSubjectSel = "//div[@id='branch-header']/h3";

    public static final String topicMessageSel = "//div[@class = 'post']/table/tbody/tr/td[@class='post-content-td']/div";

    public static final String topicsListSel = "//table[@id='topics-table']/tbody/tr/td/a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/topics/')]";

    public static final String subjectErrorMessageSel = "//span[@id='subject']";

    public static final String bodyErrorMessageSel = "//span[@id='bodyText.errors']";

    public static final String backButtonOnEditFormSel = "//a[@class='back-btn']";

    public static final String topicLinksFromDateInLastColumnSel = "//table[@id='topics-table']/tbody/tr/td[contains(@class,'latest-by')]/a";

    public static final String topicLinksFromRecentActivitySel = "//table[@id='topics-table']/tbody/tr/td/a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/topics/')]";

    public static final String amountsOfViewTopicsSel = "//span[@class='test-views']";

    public static final String profileLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/user' and not(@class='currentusername')]";

    public static final String whoBrowsingTopicSel = "//div[@class='forum_misc_info']/a";

    public static final String topicsButtonsSel = "//div[@class='pagination pull-right forum-pagination']/ul/li/a";

    @FindBy(xpath = topicsButtonsSel)
    private List<WebElement> topicsButtons;

    @FindBy(xpath = profileLinkSel)
    private WebElement profileLink;

    @FindBy(id = newButtonSel)
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

    @FindBy(xpath = topicsListSel)
    private List<WebElement> topicsList;

    @FindBy(xpath = subjectErrorMessageSel)
    WebElement subjectErrorMessage;

    @FindBy(xpath = bodyErrorMessageSel)
    WebElement bodyErrorMessage;

    @FindBy(xpath = backButtonOnEditFormSel)
    WebElement backButtonOnEditForm;

    @FindBy(xpath = topicLinksFromDateInLastColumnSel)
    List<WebElement> topicLinksFromDateInLastColumn;

    @FindBy(xpath = topicLinksFromRecentActivitySel)
    List<WebElement> topicLinksFromRecentActivity;

    @FindBy(xpath = amountsOfViewTopicsSel)
    List<WebElement> amountsOfViewTopics;

    @FindBy(xpath = whoBrowsingTopicSel)
    List<WebElement> whoBrowsingTopic;

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

    public List<WebElement> getTopicsList() {
        return topicsList;
    }

    public WebElement getSubjectErrorMessage() {
        return subjectErrorMessage;
    }

    public WebElement getBodyErrorMessage() {
        return bodyErrorMessage;
    }

    public WebElement getBackButtonOnEditForm() {
        return backButtonOnEditForm;
    }

    public List<WebElement> getTopicLinksFromDateInLastColumn() {
        return topicLinksFromDateInLastColumn;
    }

    public List<WebElement> getTopicLinksFromRecentActivity() {
        return topicLinksFromRecentActivity;
    }

    public List<WebElement> getAmountsOfViewTopics() {
        return amountsOfViewTopics;
    }

    public List<WebElement> getWhoBrowsingTopic() {
        return whoBrowsingTopic;
    }

    public WebElement getProfileLink() {
        return profileLink;
    }

    public List<WebElement> getTopicsButtons() {
        return topicsButtons;
    }

    public WebElement getButtonTopicsPageLink(int pageNum) {
        return topicsButtons.get(pageNum - 1);
    }
}
