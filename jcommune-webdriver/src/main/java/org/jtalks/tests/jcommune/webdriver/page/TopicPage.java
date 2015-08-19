package org.jtalks.tests.jcommune.webdriver.page;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReview;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Poll;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.DriverMethodHelp.setCheckboxState;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

/**
 * @author masyan
 * @author pancheshenko andrey
 */
public class TopicPage {
    public static final String CR_COMMENT_EMPTY_ERROR = "(may not be empty\n)" +
            "|(Не может быть пустым\n)";

    public static final String EMPTY_BODY_ERROR = "(Size must be between 2 and 20000)" +
            "|(Размер должен быть между 2 и 20000)";

    public static final String SUBJECT_SIZE_ERROR = "(Size must be between 1 and 120\n)" +
            "|(Размер должен быть между 1 и 120\n)";

    public static final String POLL_SUBJECT_EMPTY_ERROR = "(Poll title could not be blank if poll items arent blank)" +
            "|(Если заданы опции голосования, то заголовок голосования не может быть пустым)";

    public static final String POLL_SUBJECT_SIZE_ERROR = "(Size must be between 3 and 120)" +
            "|(Размер должен быть между 3 и 120)";

    public static final String POLL_OPTIONS_ERROR = "(Poll items could not be blank if poll title is not blank)" +
            "|(Если задан заголовок голосования, необходимо задать и опции голосования)";

    public static final String POLL_OPTIONS_NUMBER_ERROR = "(Options number should be 2 - 50)" +
            "|(Количество элементов должно быть 2 - 50)";

    public static final String POLL_OPTIONS_LENGTH_ERROR = "(Item length should be 1 - 50)" +
            "|(Длина опции голосования должна быть: 1 - 50)";

    public static final String POLL_DUPLICATES_ERROR = "(Poll items could not contain duplicates)" +
            "|(Опции голосования не могут содержать дубликаты)";

    public static final String CR_COMMENT_LENGTH_ERROR = "(Size must be between 1 and 5000\n)" +
            "|(Размер должен быть между 1 и 5000\n)";

    private static final String postMessageSel = "//td[@class='post-content-td']/div";

    private static final String editButtonSel = "edit_button";

    public static final String backButtonOnEditFormSel = "//a[@class='back-btn']";
    
    @FindBy(id = "subjectError")
    private WebElement subjectErrorMessage;

    @FindBy(id = "bodyText.errors")
    private WebElement bodyErrorMessage;

    @FindBy(xpath = backButtonOnEditFormSel)
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

    @FindBy(xpath = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user' and not(@class='currentusername')]")
    private WebElement profileLink;

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

    @FindBy(xpath = postMessageSel)
    private WebElement postMessage;

    @FindBy(className = editButtonSel)
    private WebElement editButton;

    @FindBy(id = "remove-entity-ok")
    private WebElement deleteConfirmDialogButtonOk;

    private final WebDriver driver;

    public TopicPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Step
    public void fillTopicMainFields(Topic topic) throws PermissionsDeniedException {
        info("Filling topic fields");
        fillTopicTitle(topic.getTitle());
        fillTopicBody(topic.getFirstPost().getPostContent());
        markTopicAsSticked(topic.isSticked());
        markTopicAsAnnouncement(topic.isAnnouncement());
    }

    private void fillTopicTitle(String topicTitle) {
        info("Fill topic title: [" + topicTitle + "]");
        this.subjectField.sendKeys(topicTitle);
    }

    private void fillTopicBody(String body) {
        info("Filling topic body: [" + StringUtils.left(body, 100) + "...]");
        getMainBodyArea().clear();
        for (String token : Splitter.fixedLength(100).split(body)) {
            getMainBodyArea().sendKeys(token);
        }
    }

    private void markTopicAsSticked(boolean isTopicSticked) throws PermissionsDeniedException {
        if (isTopicSticked) {
            info("Marking topic as sticked");
        } else {
            info("Marking topic as non-sticked");
        }
        if (DriverMethodHelp.isElementDisplayedImmediately(driver, getTopicStickedCheckbox())) {
            setCheckboxState(getTopicStickedCheckbox(), isTopicSticked);
        } else {
            info("Sticked checkbox is not on the page since user doesn't have permissions, so nothing to change");
        }
    }

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
            getTopicsPollTitleField().sendKeys(poll.getTitle());
            fillPollOptions(poll.getOptions());
            fillPollEndDate(poll.getEndDateAsString());
            setCheckboxState(getTopicsPollMultipleChecker(), poll.isMultipleAnswers());
        }
    }

    private void fillPollOptions(String[] pollOptions) {
        info("Filling poll options. Their number is: " + pollOptions.length);
        WebElement optionsField = getTopicPollItemsField();
        for (String option : pollOptions) {
            info("Filling poll option: " + option);
            optionsField.sendKeys(option + "\n");
        }
    }

    private void fillPollEndDate(String endDate) {
        if (endDate != null) {
            info("Fill poll end date: " + endDate);
            getTopicsPollEndDateField().sendKeys(endDate);
        } else {
            info("Leaving poll end date empty");
        }
    }

    @Step
    public void clickAnswerToTopicButton() throws PermissionsDeniedException {
        info("Clicking a button to post the topic/answer");
        try {
            getPostButton().click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are granted");
            throw new PermissionsDeniedException("User does not have permissions to leave posts in the branch");
        }
    }

    public void editPostMessageBody(String newMessage) {
        info("Editing post body: [" + StringUtils.left(newMessage, 100) + "...]");
        getMainBodyArea().clear();
        for (String token : Splitter.fixedLength(100).split(newMessage)) {
            getMainBodyArea().sendKeys(token);
        }
    }

    public void fillCodeReviewFields(CodeReview codeReview) {
        info("Filling code review title: [" + StringUtils.left(codeReview.getTitle(), 100) + "...]");
        getSubjectField().sendKeys(codeReview.getTitle());
        info("Filling code review body: [" + StringUtils.left(codeReview.getContent(), 100) + "...]");
        getMainBodyArea().sendKeys(codeReview.getContent());
    }
    
    //Getters

    public WebElement getSubjectField() {
        return subjectField;
    }

    public WebElement getMainBodyArea() {
        return mainBodyArea;
    }

    public WebElement getPostButton() {
        return postButton;
    }

    public WebElement getSubjectErrorMessage() {
        return subjectErrorMessage;
    }

    public WebElement getBodyErrorMessage() {
        return bodyErrorMessage;
    }

    public WebElement getTopicStickedCheckbox() {
        return topicSticked;
    }

    public WebElement getPollTitleErrorMessage() {
        return pollTitleErrorMessage;
    }

    public WebElement getPollItemsErrorMessage() {
        return pollItemsErrorMessage;
    }

    public WebElement getTopicPollItemsField() {
        return topicsPollItemsField;
    }

    public WebElement getTopicsPollTitleField() {
        return topicsPollTitleField;
    }

    public WebElement getTopicsPollEndDateField() {
        return topicsPollEndDateField;
    }

    public WebElement getTopicsPollMultipleChecker() {
        return topicsPollMultipleChecker;
    }
}
