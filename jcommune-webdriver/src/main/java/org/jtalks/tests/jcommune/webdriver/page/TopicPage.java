package org.jtalks.tests.jcommune.webdriver.page;


import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Poll;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.DriverMethodHelp.setCheckboxState;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.topicPage;


/**
 * @author masyan
 */
public class TopicPage {
    public static final String EMPTY_SUBJECT_ERROR = "(may not be empty\n)|(Не может быть пустым\n)";
    public static final String EMPTY_BODY_ERROR = "size must be between 2 and 20000";
    public static final String SUBJECT_SIZE_ERROR = "size must be between 1 and 120\n";
    public static final String POLL_SUBJECT_EMPTY_ERROR = "Poll title could not be blank if poll items arent blank";
    public static final String POLL_SUBJECT_SIZE_ERROR = "size must be between 3 and 120";
    public static final String POLL_OPTIONS_ERROR = "Poll items could not be blank if poll title is not blank";
    public static final String POLL_OPTIONS_NUMBER_ERROR = "Options number should be 2 - 50";
    public static final String POLL_OPTIONS_LENGTH_ERROR = "Item length should be 1 - 50";
    public static final String POLL_DUPLICATES_ERROR = "Poll items could not contain duplicates";
    @FindBy(id = "subjectError")
    private WebElement subjectErrorMessage;
    @FindBy(id = "bodyText.errors")
    private WebElement bodyErrorMessage;
    @FindBy(xpath = "//a[@class='back-btn']")
    private WebElement backButtonOnEditForm;
    @FindBy(xpath = "//table[@id='topics-table']/tbody/tr/td[contains(@class,'latest-by')]/a")
    private List<WebElement> topicLinksFromDateInLastColumn;
    @FindBy(xpath = "//table[@id='topics-table']/tbody/tr/td/a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/topics/')]")
    private List<WebElement> topicLinksFromRecentActivity;
    @FindBy(xpath = "//span[@class='test-views']")
    private List<WebElement> amountsOfViewTopics;
    @FindBy(xpath = "//div[@class='forum_misc_info']/a")
    private List<WebElement> whoBrowsingTopic;
    @FindBy(id = "pollTitle")
    private WebElement topicsPollTitleField;
    @FindBy(id = "pollItems")
    private WebElement topicsPollItemsField;
    @FindBy(id = "datepicker")
    private WebElement topicsPollEndDateField;
    @FindBy(id = "multipleChecker")
    private WebElement topicsPollMultipleChecker;
    @FindBy(id = "topic.poll.title.errors")
    private WebElement pollTitleErrorMessage;
    @FindBy(id = "topic.poll.pollItems.errors")
    private WebElement pollItemsErrorMessage;
    @FindBy(xpath = "//*[@id='topics-table']/tbody/tr[last()]")
    private WebElement lastTopicLine;
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
    @FindBy(className = "topic-types-dropdown")
    private WebElement newTopicTypeToggle;
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
    @FindBy(xpath = "//div[@id='branch-header']/h1/a")
    private WebElement topicSubjectAfterCreation;
    @FindBy(xpath = "//div[@class = 'post']/table/tbody/tr/td[@class='post-content-td']/div")
    private WebElement topicMessage;
    @FindBy(xpath = "//table[@id='topics-table']/tbody/tr/td/a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/topics/')]")
    private List<WebElement> topicsList;
    private final WebDriver driver;


    public TopicPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void goToTopicPage() throws ValidationException {
        Users.signUpAndSignIn();
        Topic topic = new Topic("subject123", "New final test answer");
        if (topic.getBranch() == null) {
            Branch branch = new Branch(branchPage.getBranches().get(0).getText());
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
        topicPage.getNewTopicToggle().click();
        topicPage.getNewCodeReviewButton().click();
    }

    //Getters
    public WebElement getNewButton() {
        return newButton;
    }

    @Step
    public void clickCreateTopic() throws PermissionsDeniedException {
        info("Clicking New Topic button");
        try {
            topicPage.getNewButton().click();
        } catch (NoSuchElementException e) {
            info("No such button found!");
            throw new PermissionsDeniedException("Couldn't find New Topic button. Here is the page source: \n"
                    + driver.getPageSource());
        }
    }

    @Step
    public void fillTopicMainFields(Topic topic) throws PermissionsDeniedException {
        info("Filling topic fields");
        fillTopicTitle(topic.getTitle());
        fillTopicBody(topic.getFirstPost().getPostContent());
        markTopicAsSticked(topic.isSticked());
        markTopicAsAnnouncement(topic.isAnnouncement());
    }

    @Step
    private void fillTopicTitle(String topicTitle) {
        info("Fill topic title: [" + topicTitle + "]");
        this.subjectField.sendKeys(topicTitle);
    }

    @Step
    private void fillTopicBody(String body) {
        info("Filling topic body: [" + StringUtils.left(body, 100) + "...]");
        for (String token : Splitter.fixedLength(100).split(body)) {
            this.mainBodyArea.sendKeys(token);
        }
    }

    @Step
    private void markTopicAsSticked(boolean topicIsSticked) throws PermissionsDeniedException {
        if (topicIsSticked) {
            info("Marking topic as sticked");
        } else {
            info("Marking topic as non-sticked");
        }
        if (DriverMethodHelp.isElementDisplayedImmediately(driver, topicSticked)) {
            setCheckboxState(topicSticked, topicIsSticked);
        } else {
            info("Sticked checkbox is not on the page since user doesn't have permissions, so nothing to change");
        }
    }

    @Step
    private void markTopicAsAnnouncement(boolean isAnnouncement) throws PermissionsDeniedException {
        if (isAnnouncement) {
            info("Marking topic as announcement");
        } else {
            info("Leaving topic as not an announcement");
        }
        if (DriverMethodHelp.isElementDisplayedImmediately(driver, topicAnnouncement)) {
            setCheckboxState(topicAnnouncement, isAnnouncement);
        } else {
            info("Announcement checkbox is not on the page since user doesn't have permissions, so nothing to change");
        }
    }

    @Step
    public void fillPollSpecificFields(Poll poll) {
        if (poll != null) {
            info("Filling poll title: " + poll.getTitle());
            topicsPollTitleField.sendKeys(poll.getTitle());
            fillPollOptions(poll.getOptions());
            fillPollEndDate(poll.getEndDateAsString());
            setCheckboxState(topicPage.getTopicsPollMultipleChecker(), poll.isMultipleAnswers());
        }
    }

    @Step
    private void fillPollOptions(String[] pollOptions) {
        info("Filling poll options. Their number is: " + pollOptions.length);
        WebElement optionsField = getTopicPollItemsField();
        for (String option : pollOptions) {
            info("Filling poll option: " + option);
            optionsField.sendKeys(option + "\n");
        }
    }

    @Step
    private void fillPollEndDate(String endDate) {
        if (endDate != null) {
            info("Fill poll end date: " + endDate);
            topicPage.getTopicsPollEndDateField().sendKeys(endDate);
        } else {
            info("Leaving poll end date empty");
        }
    }

    @Step
    public void clickAnswerToTopicButton() throws PermissionsDeniedException {
        info("Clicking a button to post the topic/answer");
        try {
            postButton.click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are granted");
            throw new PermissionsDeniedException("User does not have permissions to leave posts in the branch");
        }
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

    public WebElement getTopicSubjectAfterCreation() {
        return topicSubjectAfterCreation;
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

    public WebElement getPollTitleErrorMessage() {
        return pollTitleErrorMessage;
    }

    public WebElement getPollItemsErrorMessage() {
        return pollItemsErrorMessage;
    }

    public List<WebElement> getTopicsButtons() {
        return topicsButtons;
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

    public WebElement getNewTopicToggle() {
        return newTopicTypeToggle;
    }

    public WebElement getNewCodeReviewButton() {
        return newCodeReviewButton;
    }

    public WebElement getBranchName() {
        return branchName;
    }

}
