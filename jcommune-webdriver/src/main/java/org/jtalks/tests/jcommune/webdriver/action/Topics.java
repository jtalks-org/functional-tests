/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.assertion.Existence;
import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReview;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReviewComment;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Draft;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Post;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.TimeoutException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.PostPage;
import org.jtalks.tests.jcommune.webdriver.page.TopicPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.moveTopicEditor;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.postPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.sectionPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.topicPage;

/**
 * Contain topic actions like creating, deleting etc.
 *
 * @author Guram Savinov
 * @author pancheshenko andrey
 */
public class Topics {

    /**
     * Creates new topic. If {@link Topic#getBranch()} is null, then topic is created in a random branch,
     * otherwise the topic is created in a {@link Topic#getBranch()}.
     *
     * @throws PermissionsDeniedException if use cannot post in the first visible branch, she has no permissions
     * @throws CouldNotOpenPageException  if user was not able to find and open a branch with the specified name
     */
    @Step
    public static Topic createTopic(Topic topic) throws PermissionsDeniedException, CouldNotOpenPageException, ValidationException {
        if (!JCommuneSeleniumConfig.driver.getCurrentUrl().equals(JCommuneSeleniumConfig.getAppUrl())) {
            mainPage.clickForumsTitle();
        }
        if (topic.getBranch() == null) {
            List<WebElement> branches = sectionPage.getBranches();
            if (isEmpty(branches)) {
                throw new CouldNotOpenPageException("Could not open any branch, there were 0 on the page. " +
                        "Page URL: [" + JCommuneSeleniumConfig.driver.getCurrentUrl() + "]. " +
                        "Page Title: [" + JCommuneSeleniumConfig.driver.getTitle() + "]. " +
                        "Page source: " + JCommuneSeleniumConfig.driver.getPageSource());
            }
            Branch branch = new Branch(branches.get(0).getText());
            topic.withBranch(branch);
        }
        info("Creating " + topic + " in " + topic.getBranch());
        Branches.openBranch(topic.getBranch());
        branchPage.clickCreateTopic();
        topicPage.fillTopicMainFields(topic);
        topicPage.fillPollSpecificFields(topic.getPoll());
        topicPage.clickAnswerToTopicButton();
        topic.setModificationDate(org.joda.time.DateTime.now().plusMinutes(1));
        assertTopicFormValid();
        return topic;
    }

    @Step
    public static Post postAnswer(Topic topic) throws PermissionsDeniedException, CouldNotOpenPageException {
        openRequiredTopic(topic);

        Post newPost = new Post(randomAlphanumeric(200));
        topic.addPost(newPost);
        postPage.getMessageField().sendKeys(newPost.getPostContent());
        postPage.clickAnswerToTopicButton();

        info("Answer to topic [" + topic.getTitle() + "] was left");
        return newPost;
    }

    @Step
    public static Post typeAnswerWithinMilSec(Topic topic, int withinMilSec) throws PermissionsDeniedException, CouldNotOpenPageException {
        openRequiredTopic(topic);

        Post newPost = new Draft(randomAlphanumeric(200));
        topic.addPost(newPost);
        postPage.getMessageField().sendKeys(newPost.getPostContent());
        assertDraftCreated();

//        info("Answer to topic [" + topic.getTitle() + "] was left");
        return newPost;
    }

    private static void assertDraftCreated() {

    }

    @Step
    public static Post typeAnswer(Topic topic) throws PermissionsDeniedException, CouldNotOpenPageException {
        openRequiredTopic(topic);

        Post newPost = new Draft(randomAlphanumeric(200));
        topic.addPost(newPost);
        postPage.getMessageField().sendKeys(newPost.getPostContent());

//        info("Answer to topic [" + topic.getTitle() + "] was left");
        return newPost;
    }

    @Step
    public static Post typeAnswer(Topic topic, Post post) throws PermissionsDeniedException, CouldNotOpenPageException {
        openRequiredTopic(topic);

        Post newPost = new Draft(post.getPostContent());
        postPage.getMessageField().sendKeys(newPost.getPostContent());

//        info("Answer to topic [" + topic.getTitle() + "] was left");
        return newPost;
    }

    @Step
    public static Post pasteAnswer(Topic topic) throws PermissionsDeniedException, CouldNotOpenPageException {
        openRequiredTopic(topic);

        Post newPost = new Draft(randomAlphanumeric(200));
        topic.addPost(newPost);

        // create method for paste data from clipboard
        postPage.getMessageField().sendKeys(newPost.getPostContent());

//        info("Answer to topic [" + topic.getTitle() + "] was left");
        return newPost;
    }

    @Step
    public static void postAnswer(Topic topic, Post post) throws PermissionsDeniedException, CouldNotOpenPageException, ValidationException {
        openRequiredTopic(topic);

        postPage.fillPostBody(post.getPostContent());
        postPage.clickAnswerToTopicButton();
        assertTopicPostFormValid();

        topic.addPost(post);
        info("Answer to topic [" + topic.getTitle() + "] was left");
    }

    @Step
    public static void editPost(Topic topic, Post postToEdit) {
        openRequiredTopic(topic);

        postPage.clickEditInPostContainingString(postToEdit.getPostContent());
        try {
            (new WebDriverWait(driver, 40)).until(ExpectedConditions.refreshed(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(TopicPage.backButtonOnEditFormSel))));
        } catch (TimeoutException e) {
            info("Edit post method failed by timeout after button [edit] was clicked.");
            throw e;
        }
        String newPostContent = randomAlphanumeric(100);
        topicPage.editPostMessageBody(newPostContent);
        topicPage.clickAnswerToTopicButton();
        for(int i = 0; i < topic.getPosts().size(); i++) {
            if (topic.getPosts().get(i).getPostContent().equals(postToEdit.getPostContent())) {
                topic.getPosts().get(i).setPostContent(newPostContent);
                break;
            }
        }
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(postPage.getFirstPost()));
    }

    @Step
    public static void deleteTopic(Topic topic) throws PermissionsDeniedException {
        openRequiredTopic(topic);
        info("Clicking delete button for topic's first post");
        try {
            postPage.getDeleteTopicButton().click();
        } catch (NoSuchElementException e) {
            info("Delete button was not found");
            throw new PermissionsDeniedException("Delete button was not found. Lack of permissions?");
        }
        postPage.closeDeleteConfirmDialogOk();
    }

    @Step
    public static void deletePost(Topic topic, Post selectedPost) {
        throw new UnsupportedOperationException();
    }

    @Step
    public static void moveTopic(Topic topic) {
        openRequiredTopic(topic);
        postPage.openMoveTopicEditorDialog();
        String newBranchName = moveTopicEditor.chooseBranchIndex(0);
        moveTopicEditor.clickConfirmMoveButton();
        info("Waiting for the page being refreshed and move performed successfully");
        try {
            (new WebDriverWait(driver, 40)).until(ExpectedConditions.refreshed(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(PostPage.branchNameSel + "[contains(text(),'" + newBranchName.trim() + "')]"))));
        } catch (TimeoutException e) {
            info("Move method failed by timeout after button [move] was clicked.");
            throw e;
        }
        topic.withBranch(newBranchName);
    }

    @Step
    public static void moveTopic(Topic topic, String newBranchName) {
        openRequiredTopic(topic);
        postPage.openMoveTopicEditorDialog();
        moveTopicEditor.chooseBranch(newBranchName);
        moveTopicEditor.clickConfirmMoveButton();
        info("Waiting for the page being refreshed and move performed successfully");
        try {
            (new WebDriverWait(driver, 40)).until(ExpectedConditions.refreshed(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(PostPage.branchNameSel + "[contains(text(),'" + newBranchName.trim() + "')]"))));
        } catch (TimeoutException e) {
            info("Move method failed by timeout after button [move] was clicked.");
            throw e;
        }
        topic.withBranch(newBranchName);
    }

    @Step
    public static void subscribe(Topic topic) throws NoSuchElementException {
        openRequiredTopic(topic);
        try {
            if (postPage.isUserAlreadySubscribed()) {
                info("User already subscribed on the topic");
            } else {
                info("Clicking subscribe button");
                postPage.getSubscribeButton().click();
            }
        } catch (NoSuchElementException e) {
            info("Subscribe button was not found");
            throw new NoSuchElementException("Subscribe button was not found", e);
        }
    }

    @Step
    public static void unsubscribe(Topic topic) throws NoSuchElementException {
        openRequiredTopic(topic);
        try {
            if (postPage.isUserAlreadySubscribed()) {
                info("User already subscribed on the topic. Clicking unsubscribe button");
                postPage.getSubscribeButton().click();
            } else {
                info("User isn't subscribed to branch");
            }
        } catch (NoSuchElementException e) {
            info("Subscribe button was not found");
            throw new NoSuchElementException("Subscribe button was not found", e);
        }
    }

    public static void openRequiredTopic(Topic topic) {
        if (!postPage.isUserInsideCorrectTopic(topic.getTitle())) {
            info("User isn't browsing required topic");
            Branches.openBranch(topic.getBranch());
            openTopicInCurrentBranch(50, topic.getTitle());
        }
    }

    /**
     * Looks through several pages of the branch in order to find the topic with the specified title.
     *
     * @param numberOfPagesToCheck since the topic might not be on the first page (either someone simultaneously creates
     *                             a lot of topics, or there are a lot of sticked topics), we have to iteration through
     *                             this number of pages to search for the topic
     * @param topicToFind          a topic title to look for
     * @return true if the specified topic was found
     * @throws CouldNotOpenPageException if specified topic was not found
     */
    @Step
    public static boolean openTopicInCurrentBranch(int numberOfPagesToCheck, String topicToFind) throws CouldNotOpenPageException {
        boolean found;
        while (!(found = branchPage.findAndOpenTopic(topicToFind))) {
            info("Topic [" + topicToFind + "] wasn't found on " + branchPage.getActiveTopicsButton().get(0).getText() + " page");
            if (!branchPage.openNextPage(numberOfPagesToCheck)) break;
        }
        if (!found) {
            info("No topic with title [" + topicToFind + "] found");
            throw new CouldNotOpenPageException(topicToFind);
        }
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(postPage.getTopicTitle()));
        return found;
    }

    public static void w3cGoToBranchPage() throws ValidationException {
        Users.signUpAndSignIn();
        Branch branch = new Branch(sectionPage.getBranches().get(0).getText());
        Branches.openBranch(branch);
    }

    public static void w3cGoToTopicCreatePage() throws ValidationException {
        w3cGoToBranchPage();
        branchPage.clickCreateTopic();
    }

    public static void w3cGoToReviewCreatePage() throws ValidationException {
        w3cGoToBranchPage();
        branchPage.clickCreateCodeReview();
    }

    public static boolean isCreated(Topic topic) {
        String expectedTitle = topic.getTitle();
        String actualTitle = postPage.getTopicTitle().getText();

        return actualTitle.equals(expectedTitle);
    }

    public static boolean isDraftCreated(Post draft) {

        return false;
    }

    public static boolean isInCorrectBranch(Topic topic) {
        return postPage.getBranchName().getText().trim().equals(topic.getBranch().getTitle());
    }

    public static void assertHasNewMessages(Topic newTopic, User userThatWantsToSeeNewMessages) {
        throw new UnsupportedOperationException();
    }

    public static void assertHasNoNewMessages(Topic newTopic, User userThatWantsToSeeNewMessages) {
        throw new UnsupportedOperationException();
    }

    public static void assertIsSubscribed(Topic topic) {
        Assert.assertTrue(postPage.isUserAlreadySubscribed(), "Assertion failed! User is not subscribed to the topic.");
    }

    private static void assertTopicFormValid() throws ValidationException {
        String failedFields = "";
        info("Check subject");
        if (Existence.existsImmediately(driver, topicPage.getSubjectErrorMessage())) {
            WebElement subjectError = topicPage.getSubjectErrorMessage();
            failedFields += subjectError.getText() + "\n";
        }
        info("Check body");
        if (Existence.existsImmediately(driver, topicPage.getBodyErrorMessage())) {
            WebElement bodyError = topicPage.getBodyErrorMessage();
            failedFields += bodyError.getText();
        }
        info("Check poll title");
        if (Existence.existsImmediately(driver, topicPage.getPollTitleErrorMessage())) {
            WebElement pollTitleError = topicPage.getPollTitleErrorMessage();
            failedFields += pollTitleError.getText();
        }
        info("Check poll items");
        if (Existence.existsImmediately(driver, topicPage.getPollItemsErrorMessage())) {
            WebElement pollItemsError = topicPage.getPollItemsErrorMessage();
            failedFields += pollItemsError.getText();
        }
        info("Check finished");
        if (!failedFields.equals("")) {
            info("Found validation errors: " + failedFields);
            throw new ValidationException(failedFields);
        }
        info("Check successful. No errors.");
    }

    private static void assertTopicPostFormValid() throws ValidationException {
        String failedFields = "";
        info("Check body");
        if (Existence.existsImmediately(driver, topicPage.getBodyErrorMessage())) {
            WebElement bodyError = topicPage.getBodyErrorMessage();
            failedFields += bodyError.getText();
        }
        info("Check finished");
        if (!failedFields.equals("")) {
            info("Found validation errors: " + failedFields);
            throw new ValidationException(failedFields);
        }
        info("Check successful. No errors.");
    }

    // Code review methods

    public static CodeReview createCodeReview(CodeReview codeReview)
            throws PermissionsDeniedException, CouldNotOpenPageException, ValidationException {
        if (codeReview.getBranch() == null) {
            List<WebElement> branches = sectionPage.getBranches();
            if (isEmpty(branches)) {
                throw new CouldNotOpenPageException("Could not open any branch, there were 0 on the page. " +
                        "Page URL: [" + JCommuneSeleniumConfig.driver.getCurrentUrl() + "]. " +
                        "Page Title: [" + JCommuneSeleniumConfig.driver.getTitle() + "]. " +
                        "Page source: " + JCommuneSeleniumConfig.driver.getPageSource());
            }
            Branch branch = new Branch(sectionPage.getBranches().get(0).getText());
            codeReview.withBranch(branch);
        }
        Branches.openBranch(codeReview.getBranch());
        branchPage.clickCreateCodeReview();
        topicPage.fillCodeReviewFields(codeReview);
        topicPage.clickAnswerToTopicButton();
        assertCodeReviewFormValid();
        return codeReview;
    }

    public static CodeReviewComment leaveCodeReviewComment(CodeReview codeReview, CodeReviewComment codeReviewComment)
            throws PermissionsDeniedException, ValidationException {
        openRequiredTopic(codeReview);
        postPage.clickLineInCodeReviewForComment(codeReviewComment.getCommentedLineNumber());
        postPage.fillCodeReviewCommentBody(codeReviewComment);
        postPage.clickAddCommentToCodeReviewButton();
        assertCodeReviewFormValid();
        codeReview.addComment(codeReviewComment);
        return codeReviewComment;
    }

    public static void editCodeReviewComment(CodeReview codeReview, CodeReviewComment codeReviewComment) {
        openRequiredTopic(codeReview);

        postPage.clickEditInCodeReviewCommentContainingString(codeReviewComment.getPostContent());
        try {
            (new WebDriverWait(driver, 20)).until(ExpectedConditions.refreshed(ExpectedConditions
                    .presenceOfElementLocated(By.xpath(PostPage.codeReviewCommentTextFieldSel))));
        } catch (TimeoutException e) {
            info("Edit post method failed by timeout after button [edit] was clicked.");
            throw e;
        }
        String newCommentContent = randomAlphanumeric(100);
        postPage.editCodeReviewCommentBody(newCommentContent);
        postPage.clickOkButtonInEditComment();
        for(int i = 0; i < codeReview.getComments().size(); i++) {
            if (codeReview.getComments().get(i).getPostContent().equals(codeReviewComment.getPostContent())) {
                codeReview.getComments().get(i).setPostContent(newCommentContent);
                break;
            }
        }
        (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(postPage.getFirstPost()));
    }

    public static void assertCodeReviewFormValid() throws ValidationException {
        String failedFields = "";
        info("Check subject");
        if (Existence.existsImmediately(driver, topicPage.getSubjectErrorMessage())) {
            WebElement subjectError = topicPage.getSubjectErrorMessage();
            failedFields += subjectError.getText() + "\n";
        }
        info("Check body");
        if (Existence.existsImmediately(driver, topicPage.getBodyErrorMessage())) {
            WebElement bodyError = topicPage.getBodyErrorMessage();
            failedFields += bodyError.getText();
        }
        info("Check CR comment body");
        if (Existence.existsUsingLowerTimeout(driver, postPage.getCRCommentBodyErrorMessage())) {
            WebElement codeReviewCommentBodyError = postPage.getCRCommentBodyErrorMessage();
            failedFields += codeReviewCommentBodyError.getText() + "\n";
        }
        info("Check finished");
        if (!failedFields.equals("")) {
            info("Found validation errors: " + failedFields);
            throw new ValidationException(failedFields);
        }
        info("Check successful. No errors.");
    }

    public static void editPost(CodeReview codeReview, Post postToEdit) {
        throw new UnsupportedOperationException("Edit post can't be done in code review type topics");
    }

    public static void deleteCodeReviewComment(CodeReview codeReview, CodeReviewComment codeReviewComment){
        openRequiredTopic(codeReview);

        postPage.clickDeleteInCodeReviewCommentContainingString(codeReviewComment.getPostContent());
        postPage.closeDeleteCRCommentConfirmDialogOk();
    }

    public static void closeTopic() {

    }
}
