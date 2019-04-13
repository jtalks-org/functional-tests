package org.jtalks.tests.jcommune.webdriver.page;

import org.apache.commons.lang3.StringUtils;
import org.jtalks.tests.jcommune.webdriver.entity.topic.QuestionAndAnswers;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import java.util.List;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;


public class QuestionAndAnswersPage extends TopicPage {
    private String commentContent = randomAlphanumeric(100);

    public static final String deleteCommentWarning = "(Are you sure you want to delete this comment?)" +
            "|(Удалить комментарий?)";
    public static final String answerToOwnQuestionWarning ="(Are you sure you want to answer your question?)" +
            "|(Use comments if you want to respond to someone's answer. Or edit your question if you want to add more details.)" +
            "|(Вы точно хотите ответить на свой же вопрос?)" +
            "|(Используйте комментарии, если хотите прокомментировать чей-то ответ. Либо отредактируйте свой вопрос, если хотите добавить больше информации.)";

     @FindBys({@FindBy (css= "[id^='prompt']")})
     private List<WebElement> commentFields;

    @FindBys({@FindBy (css= "[class^='comment-content']")})
    private List<WebElement> commentFieldsContent;

    @FindBys({@FindBy (css= "[class^='content']")})
    private List<WebElement> answersContent;

    @FindBy(css= "[class='comment-textarea edit-comment']")
    private WebElement commentTextArea;

    @FindBy(css= "[id='antiMultiAnswersBtn']")
    private WebElement answerButton;

    @FindBy(css= "[id='continue-answering']")
    private WebElement confirmAnswerButton;

//    @FindBy(className = "vote-up vote-up-unpressed")
//    private WebElement voteUpButton;
//
//    @FindBy(className = "vote-down vote-down-unpressed")
//    private WebElement voteDownButton;
//
//    @FindBy(className = "vote-result")
//    private WebElement voteResult;

    @FindBy (css= "[class='btn btn-primary comment-submit']")
    private WebElement commentButton;

    @FindBy(css= "[class='btn comment-cancel']")
    private WebElement commentCamcelButton;

    @FindBy(css= "[class='icon-pencil']")
    private WebElement editCommentButton;

    @FindBy(css= "[class='icon-trash']")
    private WebElement deleteCommentButton;

    @FindBy(css= "[class='btn btn-primary edit-submit']")
    private WebElement editCommentConfirmButton;

    @FindBy (css= "[id='remove-review-ok']")
    private WebElement confirmDeleteCommentButton;

    public QuestionAndAnswersPage(WebDriver driver) {
        super(driver);
    }

    public void fillQuesionAndAnswersFields(QuestionAndAnswers question) {
        info("Filling QA title: [" + StringUtils.left(question.getTitle(), 100) + "...]");
        getSubjectField().sendKeys(question.getTitle());
        fillBody(question.getFirstPost().getPostContent());
    }

    public String findComment (String comment) {
        info("Trying to find comment with content: " + comment);
        for (WebElement currentComment : getCommentFieldsContent()) {
            if (currentComment.getText().contains(comment)) {
                info("Comment was found.");
            }
            else {
                info("Comment wasn't found.");
            }
            break;
        }
        return comment;
    }

    public String findAnswer (String answer) {
        info("Trying to find answer with content: " + answer);
        for (WebElement currentAnswer : getAnswersContent()) {
            if (currentAnswer.getText().contains(answer)) {
                info("Answer was found.");
            }
            else {
                info("Answer wasn't found.");
            }
            break;
        }
        return answer;
    }


    public void clickSubmitCommentButton() throws PermissionsDeniedException {
        info("Clicking the button to post new comment to the post");
        try {
            getCommentButton().click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are granted");
            throw new PermissionsDeniedException("User does not have permissions to leave comments in the branch");
        }
    }

    public void clickEditCommentButton() throws PermissionsDeniedException {
        info("Clicking the button to edit a comment");
        try {
            getEditCommentButton().click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are granted");
            throw new PermissionsDeniedException("User does not have permissions to edit comments in the branch");
        }
    }


    public void clickConfirmEditCommentButton() throws PermissionsDeniedException {
        info("Clicking the button to save an updated comment");
        try {
            getEditCommentConfirmButton().click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are granted");
            throw new PermissionsDeniedException("User does not have permissions to edit comments in the branch");
        }
    }


    public void clickDeleteCommentButton() throws PermissionsDeniedException {
        info("Clicking the button to remove a comment");
        try {
            getDeleteCommentButton().click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are granted");
            throw new PermissionsDeniedException("User does not have permissions to delete comments in the branch");
        }
    }

    public void clickAnswerToQuestionButton() throws PermissionsDeniedException {
        info("Clicking the button to answer to own question");
        try {
            getAnswerButton().click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are granted");
            throw new PermissionsDeniedException("User does not have permissions to answer to question in the branch");
        }
    }


    @Override
    public void fillTopicMainFields(Topic topic) throws PermissionsDeniedException {
        super.fillTopicMainFields(topic);
    }

    @Override
    public void clickAnswerToTopicButton() throws PermissionsDeniedException {
        super.clickAnswerToTopicButton();
    }

    @Override
    public void fillBody(String newMessage) {
        super.fillBody(newMessage);
    }

    @Override
    public WebElement getPostButton() {
        return super.getPostButton();
    }

    @Override
    public WebElement getBodyErrorMessage() {
        return super.getBodyErrorMessage();
    }

    @Override
    public WebElement getSubjectErrorMessage() {
        return super.getSubjectErrorMessage();
    }

    @Override
    public WebElement getSubjectField() {
        return super.getSubjectField();
    }

    @Override
    public WebElement getMainBodyArea() {
        return super.getMainBodyArea();
    }

    public List<WebElement> getCommentFields() {
        return commentFields;
    }

    public WebElement getCommentButton() {
        return commentButton;
    }


    public WebElement getFirstCommentContent() {
        return getCommentFieldsContent().get(0);
    }

    public void addFirstComment (String answer){
        Actions actions = new Actions(driver);
        for (WebElement comment : getCommentFields()) {
            actions.moveToElement(comment);
            actions.click();
            info("Filling new comment: " + answer);
            actions.sendKeys(answer);
            actions.build().perform();
        }
    }

    public void editComment (String newComment){
        getCommentTextArea().clear();
        info("Filling new comment: " + newComment);
        getCommentTextArea().sendKeys(newComment);
    }


//
//    public WebElement getVoteUpButton() {
//        return voteUpButton;
//    }
//
//    public WebElement getVoteDownButton() {
//        return voteDownButton;
//    }
//
//    public WebElement getVoteResult() {
//        return voteResult;
//    }
//

    public WebElement getCommentCamcelButton() {
        return commentCamcelButton;
    }

    public String getCommentContent() {
        return commentContent;
    }
//
    public WebElement getEditCommentButton() {
        return editCommentButton;
    }

    public WebElement getEditCommentConfirmButton() {
        return editCommentConfirmButton;
    }

    public List<WebElement> getCommentFieldsContent() {
        return commentFieldsContent;
    }

    public List<WebElement> getAnswersContent() {
        return answersContent;
    }

    public WebElement getDeleteCommentButton() {
        return deleteCommentButton;
    }

    public WebElement getConfirmDeleteCommentButton() {
        return confirmDeleteCommentButton;
    }

    public WebElement getCommentTextArea() {
        return commentTextArea;
    }

    public WebElement getAnswerButton() {
        return answerButton;
    }

    public WebElement getConfirmAnswerButton() {
        return confirmAnswerButton;
    }

}