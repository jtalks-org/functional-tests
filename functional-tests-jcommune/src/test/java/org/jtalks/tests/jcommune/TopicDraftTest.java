package org.jtalks.tests.jcommune;

import junit.framework.Assert;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Draft;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Post;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.Pages;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * Created by @baranov.r.p
 */
public class TopicDraftTest {

    private final String BRANCH_CLASSICAL_MECHANICS = "Classical Mechanics";
    private final String BRANCH_NOTIFICATION_TESTS = "Notification tests";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_DuringTyping_ShouldSaveIn15Sec_QA2761() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswerWithinMilSec(createdTopic, 15000);

        Pages.reloadPage(driver);

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ReloadPage_ShouldSave_QA2757() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic);

        Pages.reloadPage(driver);

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_OpentherApps_ShouldSave_QA2758() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic);

        // there is should be action(s)
        // what does it mean open other application?

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_MoveFocus_ShouldSave_QA2758() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic);

        draft.loseFocus();

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_Logout_ShouldSave_QA2759() {
        User draftCreator = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic);

        Users.logOutAndSignIn(draftCreator);
        Topics.openRequiredTopic(createdTopic);

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void remainDraft_MoveTopic_ShouldRemain_QA2772() {
        User draftCreator = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic);

        Users.signUpAndSignIn();
        Topics.moveTopic(topic, BRANCH_CLASSICAL_MECHANICS);

        Users.logOutAndSignIn(draftCreator);
        Topics.openRequiredTopic(createdTopic);

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void remainDraft_CloseTopic_ShouldRemain_QA2773() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic);
        Topics.closeTopic(); // user should have permissions to close topic

        Pages.reloadPage(driver);

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentWith2Symb_ShouldSave_QA2774() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic, new Post(randomAlphanumeric(2)));

        Pages.reloadPage(driver);

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentWith20kSymb_ShouldSave_QA2775() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic, new Post(randomAlphanumeric(20000)));

        Pages.reloadPage(driver);

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentWith1Symb_ShouldNotSave_QA2791() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic, new Post(randomAlphanumeric(1)));

        Pages.reloadPage(driver);

        Assert.assertFalse(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentOver20kSymb_ShouldNotSave_QA2792() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic, new Post(randomAlphanumeric(20001)));

        Pages.reloadPage(driver);

        Assert.assertFalse(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentCopyPast_ShouldSave_QA2779() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.pasteAnswer(createdTopic);

        Pages.reloadPage(driver);

        Assert.assertFalse(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_DifferentUsersSameTopic_ShouldSaveBoth_QA2768() {
        User firstUser = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft firstDraft = (Draft) Topics.pasteAnswer(createdTopic);

        Pages.reloadPage(driver);

        Assert.assertTrue(Topics.isDraftCreated(firstDraft));

        Users.logout();
        User secondUser = Users.signUpAndSignIn();
        Topics.openRequiredTopic(createdTopic);
        Draft secondDraft = (Draft) Topics.pasteAnswer(createdTopic);

        Pages.reloadPage(driver);

        Assert.assertTrue(Topics.isDraftCreated(secondDraft));

        Users.logOutAndSignIn(firstUser);
        Topics.openRequiredTopic(createdTopic);

        Assert.assertFalse(Topics.isDraftCreated(firstDraft));

    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_DifferentDraftInDifferentBranches_ShouldSave_QA2780() {
        Users.signUpAndSignIn();

        Topic topicFirst = Topics.createTopic(new Topic().withBranch(BRANCH_NOTIFICATION_TESTS));
        Draft draftFirst = (Draft) Topics.typeAnswer(topicFirst);
        Topic topicSecond = Topics.createTopic(new Topic().withBranch(BRANCH_NOTIFICATION_TESTS));
        Draft draftSecond = (Draft) Topics.typeAnswer(topicSecond);

        Topics.openRequiredTopic(topicFirst);
        Assert.assertTrue(Topics.isDraftCreated(draftFirst));

        Topics.openRequiredTopic(topicSecond);
        Assert.assertTrue(Topics.isDraftCreated(draftSecond));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void presenceOfDraft_DeleteCookies_ShouldBePresent_QA2802() {
        User user = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic);
        Pages.reloadPage(driver);
        Assert.assertTrue(Topics.isDraftCreated(draft));

        Users.clearCookiesAndLogout();
        Users.signIn(user);
        Topics.openRequiredTopic(createdTopic);

        Assert.assertTrue(Topics.isDraftCreated(draft));
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentEmpty_ShouldNotSave_QA2767() {
        String spaceSymb = " ";

        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = (Draft) Topics.typeAnswer(createdTopic, new Post(spaceSymb));

        Pages.reloadPage(driver);

        Assert.assertFalse(Topics.isDraftCreated(draft));
    }
}