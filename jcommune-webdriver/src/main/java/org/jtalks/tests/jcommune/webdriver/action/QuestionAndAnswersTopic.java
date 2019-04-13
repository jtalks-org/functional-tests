package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.assertion.Existence;
import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.QuestionAndAnswers;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;
import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.action.Topics.openRequiredTopic;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.questionAndAnswersPage;

public class QuestionAndAnswersTopic {

    public static QuestionAndAnswers createQuestionAndAnswers(QuestionAndAnswers questionAndAnswers)
            throws PermissionsDeniedException, CouldNotOpenPageException, ValidationException {
        if (questionAndAnswers.getBranch() == null) {
            List<WebElement> branches = sectionPage.getBranches();
            if (isEmpty(branches)) {
                throw new CouldNotOpenPageException("Could not open any branch, there were 0 on the page. " +
                        "Page URL: [" + JCommuneSeleniumConfig.driver.getCurrentUrl() + "]. " +
                        "Page Title: [" + JCommuneSeleniumConfig.driver.getTitle() + "]. " +
                        "Page source: " + JCommuneSeleniumConfig.driver.getPageSource());
            }
            Branch branch = new Branch(sectionPage.getBranches().get(0).getText());
            questionAndAnswers.withBranch(branch);
        }
        Branches.openBranch(questionAndAnswers.getBranch());
        branchPage.clickQuestionAndAnswers();
        questionAndAnswersPage.fillQuesionAndAnswersFields(questionAndAnswers);
        questionAndAnswersPage.clickAnswerToTopicButton();
        assertQuestionAndAnswersFormValid();
        return questionAndAnswers;
    }

    private static void assertQuestionAndAnswersFormValid() throws ValidationException {
        String failedFields = "";
        info("Check subject");
        if (Existence.existsImmediately(driver, questionAndAnswersPage.getSubjectErrorMessage())) {
            WebElement subjectError = questionAndAnswersPage.getSubjectErrorMessage();
            failedFields += subjectError.getText() + "\n";
        }
        info("Check body");
        if (Existence.existsImmediately(driver, questionAndAnswersPage.getBodyErrorMessage())) {
            WebElement bodyError = questionAndAnswersPage.getBodyErrorMessage();
            failedFields += bodyError.getText();
        }
        info("Check finished");
        if (!failedFields.equals("")) {
            info("Found validation errors: " + failedFields);
            throw new ValidationException(failedFields);
        }
        info("Check successful. No errors.");
    }

    @Step
    public static String fillCommentToQuestion(QuestionAndAnswers question, String comment)
            throws PermissionsDeniedException, ValidationException {
        openRequiredTopic(question);
        info("We are in the required topic");
        questionAndAnswersPage.addFirstComment(comment);
        questionAndAnswersPage.clickSubmitCommentButton();
        Assert.assertEquals(questionAndAnswersPage.getFirstCommentContent().getText(),comment);
        return comment;
    }

    @Step
    public static String editCommentToQuestion(QuestionAndAnswers question, String comment)
            throws PermissionsDeniedException, ValidationException {
        openRequiredTopic(question);
        info("We are in the required topic");
        questionAndAnswersPage.findComment(comment);
        questionAndAnswersPage.clickEditCommentButton();
        String newCommentContent = randomAlphanumeric(100);
        questionAndAnswersPage.editComment(newCommentContent);
        questionAndAnswersPage.clickConfirmEditCommentButton();
        Assert.assertEquals(questionAndAnswersPage.getFirstCommentContent().getText(),newCommentContent);
        return newCommentContent;
    }

    @Step
    public static void deleteQAComment(QuestionAndAnswers question,String comment) throws PermissionsDeniedException {
        openRequiredTopic(question);
        info("We are in the required topic");
        questionAndAnswersPage.findComment(comment);
        info("Clicking delete button for topic's first comment");
        try {
            questionAndAnswersPage.clickDeleteCommentButton();;
        } catch (NoSuchElementException e) {
            info("Delete button was not found");
            throw new PermissionsDeniedException("Delete button was not found. Lack of permissions?");
        }
        questionAndAnswersPage.getConfirmDeleteCommentButton().click();
    }

    @Step
    public static void answerToQuestion(QuestionAndAnswers question) throws PermissionsDeniedException {
        openRequiredTopic(question);
        info("We are in the required topic");
        questionAndAnswersPage.clickAnswerToQuestionButton();
        questionAndAnswersPage.getConfirmAnswerButton().click();
        info("Adding a new answer");
        String answer = randomAlphanumeric(100);
        questionAndAnswersPage.fillBody(answer);
        info("Click the post answer button");
        questionAndAnswersPage.getPostButton().click();
        Assert.assertEquals(questionAndAnswersPage.findAnswer(answer),answer);
    }
}
