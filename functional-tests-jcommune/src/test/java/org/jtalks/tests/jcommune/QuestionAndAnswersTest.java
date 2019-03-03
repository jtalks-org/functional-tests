package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.QuestionAndAnswers;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
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
        mainPage.checkingEnabledPlugins();
    }


    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }


    @Test(groups = "ui-tests")
    public void createQuestionWithTitleAndBody_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        QuestionAndAnswers createdTopic = Topics.createQuestionAndAnswers(question);
        Assert.assertTrue(Topics.isCreated(createdTopic));
    }

    @Test(groups = "ui-tests")
    public void postValidCommentToOwnQuestion_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        String comment = (randomAlphanumeric(200));
        Topics.createQuestionAndAnswers(question);
        Topics.fillCommentToOwnQuestion(question, comment);
    }

    @Test(groups = "ui-tests")
    public void editCommentToOwnQuestion_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        String comment = (randomAlphanumeric(200));
        Topics.createQuestionAndAnswers(question);
        Topics.fillCommentToOwnQuestion(question, comment);
        Topics.EditOwnCommentToQuestion(question, comment);
    }

    @Test(groups = "ui-tests")
    public void deleteCommentToOwnQuestion_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        String comment = (randomAlphanumeric(200));
        Topics.createQuestionAndAnswers(question);
        Topics.fillCommentToOwnQuestion(question, comment);
        Topics.deleteOwnQAComment(question, comment);
    }

    @Test(groups = "ui-tests")
    public void addAnswerToOwnQuestion_ShouldPass() throws Exception {
        Users.signUpAndSignIn();
        QuestionAndAnswers question = new QuestionAndAnswers();
        Topics.createQuestionAndAnswers(question);
        Topics.answerToOwnQuestion(question);

    }
}
