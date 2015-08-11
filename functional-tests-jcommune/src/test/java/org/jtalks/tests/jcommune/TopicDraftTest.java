package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Draft;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.Pages;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.assertj.core.api.StrictAssertions.assertThat;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.postPage;

/**
 * Created by @baranov.r.p
 */
public class TopicDraftTest {

    private final String BRANCH_BICYCLE = "Bicycle";
    private final String BRANCH_EUROPE = "Europe";

    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    // Only business logic
    public void autoSaveDraft_DuringTyping_ShouldSaveIn15Sec_QA2761() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswerWithinMilSec(createdTopic, 15000);

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ReloadPage_ShouldSave_QA2757() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus().reloadPage();

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    // Only business logic
    public void autoSaveDraft_OpenOtherApps_ShouldSave_QA2758() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic);

        // there is should be action(s)
        // what does it mean open other application?

        Assert.assertTrue(Topics.isDraftCreated());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_MoveFocus_ShouldSave_QA2758() {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);

        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_Logout_ShouldSave_QA2759() {
        User draftCreator = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic);

        Users.logOutAndSignIn(draftCreator);
        Topics.openRequiredTopic(createdTopic);

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    // framework error (elem not found, during moving post
    public void remainDraft_MoveTopic_ShouldRemain_QA2772() {
        User draftCreator = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());

        Users.logOutAndSignIn(User.admin());
        Topics.moveTopic(topic, BRANCH_BICYCLE); // elem not found

        Users.logOutAndSignIn(draftCreator);
        Topics.openRequiredTopic(createdTopic);

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void remainDraft_CloseTopicAndReloadPage_ShouldRemain_QA2773() {
        // user with permissions to close topic
        Users.signIn(User.admin());

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());

        Topics.openAndCloseTopic(topic);
        Pages.reloadPage(driver);

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentWith2Symb_ShouldSave_QA2774() {
        int quantitySymb = 2;

        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);

        Draft draft = Topics.typeAnswerCustomLength(createdTopic, quantitySymb).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    // Unresponsive Script Error
    public void autoSaveDraft_ContentWith20kSymb_ShouldSave_QA2775() {
        int quantitySymb = 20000;

        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);

        Draft draft = Topics.typeAnswerCustomLength(createdTopic, quantitySymb).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    // Only business logic
    public void autoSaveDraft_ContentWith1Symb_ShouldNotSave_QA2791() {
        int quantitySymb = 1;
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswerCustomLength(createdTopic, quantitySymb).loseFocus();

        Assert.assertFalse(Topics.isDraftCreated());
    }

    // Only business logic
    public void autoSaveDraft_ContentOver20kSymb_ShouldNotSave_QA2792() {
        int quantitySymb = 20001;

        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswerCustomLength(createdTopic, quantitySymb).loseFocus();

        Assert.assertFalse(Topics.isDraftCreated());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentCopyPast_ShouldSave_QA2779() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.pasteAnswer(createdTopic).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_DifferentUsersSameTopic_ShouldSaveBoth_QA2768() {
        User firstUser = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft firstDraft = Topics.pasteAnswer(createdTopic).loseFocus();

        assertThat(firstDraft.getPostContent()).isEqualTo(postPage.findDraftContent());

        Users.logout();
        Users.signUpAndSignIn();
        Topics.openRequiredTopic(createdTopic);
        Draft secondDraft = Topics.pasteAnswer(createdTopic).loseFocus();

        assertThat(secondDraft.getPostContent()).isEqualTo(postPage.findDraftContent());

        Users.logOutAndSignIn(firstUser);
        Topics.openRequiredTopic(createdTopic);

        assertThat(firstDraft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_DifferentDraftInDifferentBranches_ShouldSave_QA2780() {
        Users.signUpAndSignIn();

        Topic topicFirst = Topics.createTopic(new Topic().withBranch(BRANCH_EUROPE));
        Draft draftFirst = Topics.typeAnswer(topicFirst).loseFocus();
        Topic topicSecond = Topics.createTopic(new Topic().withBranch(BRANCH_BICYCLE));
        Draft draftSecond = Topics.typeAnswer(topicSecond).loseFocus();

        Topics.openRequiredTopic(topicFirst);
        assertThat(draftFirst.getPostContent()).isEqualTo(postPage.findDraftContent());

        Topics.openRequiredTopic(topicSecond);
        assertThat(draftSecond.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void presenceOfDraft_DeleteCookies_ShouldBePresent_QA2802() {
        User user = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());

        Users.deleteCookiesAndLogout();
        Users.signIn(user);
        Topics.openRequiredTopic(createdTopic);

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }

    // Only business logic
    public void autoSaveDraft_ContentEmpty_ShouldNotSave_QA2767() {
        String spaceSymb = " ";

        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
//        Draft draft = Topics.typeAnswer(createdTopic, spaceSymb);

        Pages.reloadPage(driver);

        Assert.assertFalse(Topics.isDraftCreated());
    }

    // Only business logic
    public void removeDraft_PublishPost_ShouldRemove_QA2777() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        draft.publish();
        Pages.reloadPage(driver);

        Assert.assertFalse(Topics.isDraftCreated());
    }

    // Only business logic
    public void removeDraft_DeleterContent_ShouldRemove_QA2765() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        draft.deleteContent();
        Pages.reloadPage(driver);

        Assert.assertFalse(Topics.isDraftCreated());
    }


    public void autoSaveDraft_AfterEditRemain1Symb_ShouldSave_QA2778() {
        int quntitySymb = 1;
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        draft.editContent(quntitySymb);
        Pages.reloadPage(driver);

        assertThat(draft.getPostContent()).isEqualTo(postPage.findDraftContent());
    }
}