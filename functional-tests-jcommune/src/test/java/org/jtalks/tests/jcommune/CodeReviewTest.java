package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReview;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReviewComment;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.TopicPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Andrey Pancheshenko 27.08.14.
 */
public class CodeReviewTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test(groups = "ui-tests")
    public void createCodeReviewWithValidData_ShouldPass() throws Exception {
        CodeReview codeReview = new CodeReview();
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.SUBJECT_SIZE_ERROR)
    public void createCodeReviewWithEmptyTitle_ShouldFail() throws Exception {
        CodeReview codeReview = new CodeReview().withTitle("");
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test
    public void createCodeReviewWithMinTitle_ShouldPass() throws Exception {
        CodeReview codeReview = new CodeReview().withTitle(randomAlphanumeric(1));
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test
     public void createCodeReviewWithMaxTitle_ShouldPass() throws Exception {
        CodeReview codeReview = new CodeReview().withTitle(randomAlphanumeric(120));
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.SUBJECT_SIZE_ERROR)
    public void createCodeReviewExceedingMaxTitle_ShouldFail() throws Exception {
        CodeReview codeReview = new CodeReview().withTitle(randomAlphanumeric(121));
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_BODY_ERROR)
    public void createCodeReviewWithEmptyBody_ShouldFail() throws Exception {
        CodeReview codeReview = new CodeReview().withContent("");
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test
    public void createCodeReviewWithMinBody_ShouldPass() throws Exception {
        CodeReview codeReview = new CodeReview().withContent(randomAlphanumeric(2));
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test
    public void createCodeReviewWithMaxBody_ShouldPass() throws Exception {
        CodeReview codeReview = new CodeReview().withContent(randomAlphanumeric(20000));
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_BODY_ERROR)
    public void createCodeReviewExceedingMaxBody_ShouldFail() throws Exception {
        CodeReview codeReview = new CodeReview().withContent(randomAlphanumeric(20001));
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test
    public void createCodeReviewWithMultipleLines_ShouldPass() throws Exception {
        CodeReview codeReview = new CodeReview().withNumberOfLines(10);
        Users.signUpAndSignIn();
        Topics.createCodeReview(codeReview);
    }

    @Test(groups = "ui-tests")
    public void leaveValidCommentToCodeReview_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        CodeReview codeReview = new CodeReview();
        CodeReviewComment codeReviewComment = new CodeReviewComment();
        Topics.createCodeReview(codeReview);
        Topics.leaveCodeReviewComment(codeReviewComment);
    }

    @Test(groups = "ui-tests", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_SUBJECT_ERROR)
    public void leaveEmptyCommentToCodeReview_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        CodeReview codeReview = new CodeReview();
        CodeReviewComment codeReviewComment = new CodeReviewComment().withContent("");
        Topics.createCodeReview(codeReview);
        Topics.leaveCodeReviewComment(codeReviewComment);
    }

    @Test
    public void leaveMinCommentToCodeReview_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        CodeReview codeReview = new CodeReview();
        CodeReviewComment codeReviewComment = new CodeReviewComment().withContent(randomAlphanumeric(1));
        Topics.createCodeReview(codeReview);
        Topics.leaveCodeReviewComment(codeReviewComment);
    }

    @Test
    public void leaveMaxCommentToCodeReview_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        CodeReview codeReview = new CodeReview();
        CodeReviewComment codeReviewComment = new CodeReviewComment().withContent(randomAlphanumeric(5000));
        Topics.createCodeReview(codeReview);
        Topics.leaveCodeReviewComment(codeReviewComment);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.CR_COMMENT_LENGTH_ERROR)
    public void leaveCommentToCodeReviewExceedingMaxLength_ShouldFail() throws Exception {
        Users.signUpAndSignIn();
        CodeReview codeReview = new CodeReview();
        CodeReviewComment codeReviewComment = new CodeReviewComment().withContent(randomAlphanumeric(5001));
        Topics.createCodeReview(codeReview);
        Topics.leaveCodeReviewComment(codeReviewComment);
    }

    @Test
    public void leaveCommentToCodeReviewNotInFirstLine_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        CodeReview codeReview = new CodeReview().withNumberOfLines(5);
        CodeReviewComment codeReviewComment = new CodeReviewComment().onLineNumber(4);
        Topics.createCodeReview(codeReview);
        Topics.leaveCodeReviewComment(codeReviewComment);
    }
}
