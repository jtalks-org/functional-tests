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
import org.jtalks.tests.jcommune.webdriver.entity.topic.Post;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;


/**
 * Contain topic actions like creating, deleting etc.
 *
 * @author Guram Savinov
 */
public class Topics {
    private static final Logger LOGGER = LoggerFactory.getLogger(Topics.class);

    /**
     * Creates new topic. If {@link Topic#getBranch()} is null, then topic is created in a random branch,
     * otherwise the topic is created in a {@link Topic#getBranch()}.
     *
     * @throws PermissionsDeniedException if use cannot post in the first visible branch, she has no permissions
     * @throws CouldNotOpenPageException  if user was not able to find and open a branch with the specified name
     */

    public static Topic createTopic(Topic topic) throws PermissionsDeniedException, CouldNotOpenPageException, ValidationException {
        gotoMainPage();
        if (topic.getBranch() == null) {
            List<WebElement> branches = branchPage.getBranches();
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
        Branches.openBranch(topic.getBranch().getTitle());
        topicPage.clickCreateTopic();
        topicPage.fillTopicMainFields(topic);
        topicPage.fillPollSpecificFields(topic.getPoll());
        topicPage.clickAnswerToTopicButton();
        topic.setModificationDate(org.joda.time.DateTime.now().plusMinutes(1));
        assertFormValid();
        return topic;
    }

    public static void assertFormValid() throws ValidationException {
        String failedFields = "";
        if (Existence.exists(topicPage.getSubjectErrorMessage())) {
            WebElement subjectError = topicPage.getSubjectErrorMessage();
            failedFields += subjectError.getText() + "\n";
        }
        if (Existence.exists(topicPage.getBodyErrorMessage())) {
            WebElement bodyError = topicPage.getBodyErrorMessage();
            failedFields += bodyError.getText();
        }
        if (!failedFields.equals("")) {
            throw new ValidationException(failedFields);
        }
    }

    public static boolean isInCorrectBranch(Topic topic) {
        return topicPage.getBranchName().getText().trim().equals(topic.getBranch().getTitle());
    }

    public static void createCodeReview(Topic topic) throws PermissionsDeniedException, CouldNotOpenPageException {
        if (topic.getBranch() == null) {
            Branch branch = new Branch(branchPage.getBranches().get(0).getText());
            topic.withBranch(branch);
        }
        Branches.openBranch(topic.getBranch().getTitle());
        createNewCodeReview(topic);
    }

    private static void createNewCodeReview(Topic topic) {
        topicPage.getNewCodeReviewButton().click();
        topicPage.getSubjectField().sendKeys(topic.getTitle());
        Post firstPost = topic.getPosts().get(0);
        topicPage.getMainBodyArea().sendKeys(firstPost.getPostContent());
        topicPage.getPostButton().click();
    }

    public static void postAnswer(Topic topic, String branchTitle)
            throws PermissionsDeniedException, CouldNotOpenPageException, InterruptedException {
        //TODO: this might need to be uncommented, but right now we're not on the main page when we answer to the
        // topic - we are on the topic page already!
//        Branches.openBranch(branchTitle);
//        if (openTopicInCurrentBranch(100, topic.getTitle())) {
        answerToTopic(topic.getLastPost().getPostContent());
        LOGGER.info("postAnswerToTopic {}", topic.getTitle());
//        }
    }

    private static boolean findTopic(String topicTitle) throws CouldNotOpenPageException {
        boolean found = false;

        for (WebElement topics : topicPage.getTopicsList()) {
            if (topics.getText().trim().equals(topicTitle.trim())) {
                topics.click();
                found = true;
                break;
            }
        }
        return found;
    }

    private static void answerToTopic(String answer) throws PermissionsDeniedException {
        topicPage.getMainBodyArea().sendKeys(answer);
        topicPage.clickAnswerToTopicButton();
    }

    /**
     * Looks through several pages of the branch in order to find the topic with the specified id.
     *
     * @param numberOfPagesToCheck since the topic might not be on the first page (either someone simultaneously creates
     *                             a lot of topics, or there are a lot of sticked topics), we have to iteration through
     *                             this number of pages to search for the topic
     * @param topicToFind          a topic id to look for
     * @return true if the specified topic was found
     * @throws CouldNotOpenPageException if specified topic was not found
     */
    public static boolean openTopicInCurrentBranch(int numberOfPagesToCheck, String topicToFind)
            throws CouldNotOpenPageException {
        boolean found;
        while (!(found = findTopic(topicToFind))) {
            if (!openNextPage(numberOfPagesToCheck)) break;
        }
        if (!found) {
            LOGGER.info("No topic with title [{}]  found", topicToFind);
            throw new CouldNotOpenPageException(topicToFind);
        }
        return found;
    }

    private static boolean openNextPage(int pagesToCheck) {
        int max = 0;
        if (topicPage.getActiveTopicsButton().size() < 1) {
            return false;
        }
        WebElement activeBtn = topicPage.getActiveTopicsButton().get(0);
        int active = Integer.parseInt(activeBtn.getText().trim());
        for (WebElement el : topicPage.getTopicsButtons()) {
            if (Integer.parseInt(el.getText().trim()) > max)
                max = Integer.parseInt(el.getText().trim());
        }
        if ((active < pagesToCheck) && (active < max)) {
            for (WebElement elem : topicPage.getTopicsButtons()) {
                if (Integer.parseInt(elem.getText().trim()) == (active + 1)) {
                    elem.click();
                    return true;
                }
            }
        }
        return false;
    }

    private static void gotoMainPage() {
        mainPage.clickForumsTitle();
    }

    public static boolean isCreated(Topic topic) {
        String expectedTitle = topic.getTitle();
        String actualTitle = topicPage.getTopicSubjectAfterCreation().getText();

        return actualTitle.equals(expectedTitle);
    }

    public static void deleteByUser(Topic topic, User user) {
        throw new UnsupportedOperationException();
    }

    public static void subscribe(Topic topic, User user) {
        throw new UnsupportedOperationException();
    }

    public static void moveByUser(Topic topic, User user) {
        throw new UnsupportedOperationException();
    }

    public static void deleteAnswer(Topic topic, Post selectedPost) {
    }

    public static void assertHasNewMessages(Topic newTopic, User userThatWantsToSeeNewMessages) {
    }

    public static void assertHasNoNewMessages(Topic newTopic, User userThatWantsToSeeNewMessages) {
    }
}
