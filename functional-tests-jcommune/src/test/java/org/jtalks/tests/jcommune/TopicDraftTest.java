package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Pages;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Draft;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
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

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_OpenAndCloseNewTab_ShouldSave() {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);

        Draft draft = Topics.typeAnswer(createdTopic);
        Pages.openAndCloseNewTab();

        assertThat(draft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_MoveFocus_ShouldSave() {
        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);

        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_Logout_ShouldSave() {
        User draftCreator = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic);

        Users.logOutAndSignIn(draftCreator);
        Topics.openRequiredTopic(createdTopic);

        assertThat(draft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void remainDraft_LoseFocusCloseTopic_ShouldRemain() {
        // user with permissions to close topic
        Users.signIn(User.admin());

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        Topics.findAndCloseTopic(topic);

        assertThat(draft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentWith2SymbLoseFocus_ShouldSave() {
        int quantitySymb = 2;

        Users.signUpAndSignIn();
        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);

        Draft draft = Topics.typeAnswerCustomLength(createdTopic, quantitySymb).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_ContentCopyPastLoseFocus_ShouldSave() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.pasteAnswer(createdTopic).loseFocus();

        assertThat(draft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_DifferentUsersSameTopicLoseFocus_ShouldSaveBoth() {
        Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft firstDraft = Topics.pasteAnswer(createdTopic).loseFocus();

        assertThat(firstDraft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());

        Users.logoutSignUpAndSignIn();
        Topics.openRequiredTopic(createdTopic);
        Draft secondDraft = Topics.pasteAnswer(createdTopic).loseFocus();

        assertThat(secondDraft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void autoSaveDraft_DifferentDraftInDifferentBranchesLoseFocus_ShouldSave() {
        Users.signUpAndSignIn();

        Topic topicFirst = Topics.createTopic(new Topic().withBranch(BRANCH_EUROPE));
        Draft draftFirst = Topics.typeAnswer(topicFirst).loseFocus();
        assertThat(draftFirst.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());

        Topic topicSecond = Topics.createTopic(new Topic().withBranch(BRANCH_BICYCLE));
        Draft draftSecond = Topics.typeAnswer(topicSecond).loseFocus();
        assertThat(draftSecond.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }

    @Test(groups = {"ui-tests", "primary"})
    public void presenceOfDraft_LoseFocusLogoutDeleteCookies_ShouldBePresent() {
        User user = Users.signUpAndSignIn();

        Topic topic = new Topic();
        Topic createdTopic = Topics.createTopic(topic);
        Draft draft = Topics.typeAnswer(createdTopic).loseFocus();

        Users.logoutAndDeleteAllCookies();
        Users.signIn(user);
        Topics.openRequiredTopic(createdTopic);

        assertThat(draft.getPostContent()).isEqualTo(postPage.reloadAndFindDraftContent());
    }
}