package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.QuestionAndAnswersTopic;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.QuestionAndAnswers;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.QuestionAndAnswersPage;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

public class QuestionAndAnswersTest {

    @BeforeClass(alwaysRun = true)
    @Parameters({"appUrl"})
    public void enablingQuestionAndAnswersPlugin (String appUrl) throws Exception {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
        Users.signIn(User.admin());
        mainPage.openPluginsPage();
        mainPage.ensurePlugingEnabled();
    }


    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }


    @Test(groups = "ui-tests")
    public void userCanCreateQuestionWithValidTitleAndBody_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        QuestionAndAnswers createdTopic = QuestionAndAnswersTopic.createQuestionAndAnswers(question);
        Assert.assertTrue(Topics.isCreated(createdTopic));
    }

    @Test(groups = "ui-tests")
    public void postValidCommentToOwnQuestion_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        String comment = (randomAlphanumeric(200));
        QuestionAndAnswersTopic.createQuestionAndAnswers(question);
        QuestionAndAnswersTopic.fillCommentToQuestion(question, comment);
    }

    @Test(groups = "ui-tests")
    public void editCommentToOwnQuestion_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        String comment = (randomAlphanumeric(200));
        QuestionAndAnswersTopic.createQuestionAndAnswers(question);
        QuestionAndAnswersTopic.fillCommentToQuestion(question, comment);
        QuestionAndAnswersTopic.editCommentToQuestion(question, comment);
    }

    @Test(groups = "ui-tests")
    public void deleteCommentToOwnQuestion_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        String comment = (randomAlphanumeric(200));
        QuestionAndAnswersTopic.createQuestionAndAnswers(question);
        QuestionAndAnswersTopic.fillCommentToQuestion(question, comment);
        QuestionAndAnswersTopic.deleteQAComment(question, comment);
        Thread.sleep(100);
        Assert.assertFalse(driver.getPageSource().contains(comment), "The comment is still present on the page");
    }

    @Test(groups = "ui-tests")
    public void addAnswerToOwnQuestion_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        QuestionAndAnswersTopic.createQuestionAndAnswers(question);
        QuestionAndAnswersTopic.answerToQuestion(question);

    }
}
