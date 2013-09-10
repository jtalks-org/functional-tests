package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.topicPage;


/**
 * @author masyan
 */
public class TopicPage {

    @FindBy(xpath = "//div[@class='pagination pull-right forum-pagination']/ul/li/a")
    private List<WebElement> topicsButtons;

    @FindBy(xpath = "//div[@class='pagination pull-right forum-pagination']/ul/li[@class='active']/a")
    private List<WebElement> activeTopicsButton;

    @FindBy(xpath = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user' and not(@class='currentusername')]")
    private WebElement profileLink;

    @FindBy(className = "new-topic-btn")
    private WebElement newButton;

    @FindBy(xpath = "//ul[@class='breadcrumb']/li[last()]")
    private WebElement branchName;

    @FindBy(className = "new-code-review-btn")
    private WebElement newCodeReviewButton;

    @FindBy(id = "sticked")
    private WebElement topicSticked;

    @FindBy(id = "announcement")
    private WebElement topicAnnouncement;

    @FindBy(id = "subject")
    private WebElement subjectField;

    @FindBy(id = "postBody")
    private WebElement mainBodyArea;

    @FindBy(id = "post")
    private WebElement postButton;

    @FindBy(xpath = "//div[@id='branch-header']/h3")
    private WebElement topicSubject;

    @FindBy(xpath = "//div[@class = 'post']/table/tbody/tr/td[@class='post-content-td']/div")
    private WebElement topicMessage;

    @FindBy(xpath = "//table[@id='topics-table']/tbody/tr/td/a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/topics/')]")
    private List<WebElement> topicsList;

    @FindBy(xpath = "//span[@id='subject']")
    WebElement subjectErrorMessage;

    @FindBy(xpath = "//span[@id='bodyText.errors']")
    WebElement bodyErrorMessage;

    @FindBy(xpath = "//a[@class='back-btn']")
    WebElement backButtonOnEditForm;

    @FindBy(xpath = "//table[@id='topics-table']/tbody/tr/td[contains(@class,'latest-by')]/a")
    List<WebElement> topicLinksFromDateInLastColumn;

    @FindBy(xpath = "//table[@id='topics-table']/tbody/tr/td/a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/topics/')]")
    List<WebElement> topicLinksFromRecentActivity;

    @FindBy(xpath = "//span[@class='test-views']")
    List<WebElement> amountsOfViewTopics;

    @FindBy(xpath = "//div[@class='forum_misc_info']/a")
    List<WebElement> whoBrowsingTopic;

    @FindBy(id = "pollTitle")
    WebElement topicsPollTitleField;

    @FindBy(id = "pollItems")
    WebElement topicsPollItemsField;

    @FindBy(id = "datepicker")
    WebElement topicsPollEndDateField;

    @FindBy(id = "multipleChecker")
    WebElement topicsPollMultipleChecker;

    @FindBy(xpath = "//*[@id='topics-table']/tbody/tr[last()]")
    WebElement lastTopicLine;

    public static final String EMPTY_SUBJECT_ERROR = "Не может быть пустым\n";

    public static final String EMPTY_BODY_ERROR = "Не может быть пустым\n" +
            "Размер должен быть между 2 и 20000";


    public TopicPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void goToTopicPage() throws ValidationException {
        Users.signIn(Users.signUp());

        Topic topic = new Topic("subject123", "New final test answer");
        if (topic.getBranch() == null) {
            Branch branch = new Branch(branchPage.getBranchList().get(0).getText());
            topic.withBranch(branch);
        }
        Branches.openBranch(topic.getBranch().getTitle());
    }

    public void goToTopicCreatePage() throws ValidationException {
        topicPage.goToTopicPage();
        topicPage.getNewButton().click();
    }

    public void goToReviewCreatePage() throws ValidationException {
        topicPage.goToTopicPage();
        topicPage.getNewCodeReviewButton().click();
    }

    //Getters
    public WebElement getNewButton() {
        return newButton;
    }

    public WebElement getSubjectField() {
        return subjectField;
    }

    public WebElement getMainBodyArea() {
        return mainBodyArea;
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

    public WebElement getTopicSticked() {
        return topicSticked;
    }

    public WebElement getTopicAnnouncement() {
        return topicAnnouncement;
    }

    public WebElement getTopicPollTitleField() {
        return topicsPollTitleField;
    }

    public WebElement getTopicPollItemsField() {
        return topicsPollItemsField;
    }

    public WebElement getTopicsPollEndDateField() {
        return topicsPollEndDateField;
    }

    public WebElement getTopicsPollMultipleChecker() {
        return topicsPollMultipleChecker;
    }

    public List<WebElement> getActiveTopicsButton() {
        return activeTopicsButton;
    }

    public WebElement getNewCodeReviewButton() {
        return newCodeReviewButton;
    }

    public WebElement getLastTopicLine() {
        return lastTopicLine;
    }

    public WebElement getBranchName() {
        return branchName;
    }

}
