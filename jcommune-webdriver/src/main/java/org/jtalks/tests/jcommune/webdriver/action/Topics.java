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

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Poll;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Post;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.jtalks.tests.jcommune.utils.StringHelp.randomString;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.postPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.topicPage;

/**
 * Contain topic actions like creating, deleting etc.
 *
 * @author Guram Savinov
 */
public class Topics {
    private static final Logger LOGGER = LoggerFactory.getLogger(Users.class);


    private static final String POLL_END_DATE_FORMAT = "dd-MM-yyyy";

    /**
     * Sign-up new user and create new topic
     *
     * @param topic the topic representation private static final String POLL_END_DATE_FORMAT = "dd-MM-yyyy";
     *              <p/>
     *              /** Signs-up up a new random user and creates the topic in the first available branch.
     * @throws ValidationException        if specified topic does not pass forum validation
     * @throws PermissionsDeniedException if use cannot post in the first visible branch, she has no permissions
     */
    public static void signUpAndCreateTopic(Topic topic) throws ValidationException, PermissionsDeniedException {
        User user = Users.signUp();
        Users.signIn(user);
        topic.withTopicStarter(user);
        createTopic(topic);
    }

    public static Topic loginAndCreateTopic(Topic topic) throws ValidationException, PermissionsDeniedException {
        User existentUser = new User("P_10hkgd", "123456");
        topic.withTopicStarter(existentUser);
        Users.signIn(existentUser);
        return createTopic(topic);
    }

    public static Boolean isBranch(Topic topic) {
        boolean isBranch = false;

        Branches.openBranch(topic.getBranch().getTitle());
        openTopicInCurrentBranch(100, topic.getTitle());

        return isBranch;

    }

    public static boolean isInCorrectBranch(Topic topic) {
        return topicPage.getBranchName().getText().trim().equals(topic.getBranch().getTitle());
    }

    public static boolean isTopicNewer(DateTime topicDate, String dateFromLastRow) {
        DateTimeFormatter mask = new DateTimeFormatterBuilder()
                .appendDayOfMonth(2)
                .appendLiteral(' ')
                .appendMonthOfYearShortText()
                .appendLiteral(' ')
                .appendYear(4, 4)
                .appendLiteral(' ')
                .appendHourOfDay(2)
                .appendLiteral(':')
                .appendMinuteOfHour(2)
                .toFormatter();
        DateTime dat = DateTime.parse(dateFromLastRow, mask);
        return topicDate.isAfter(dat.getMillis());
    }

    /**
     * Creates new topic. If {@link Topic#getBranch()} is null, then topic is created in a random branch,
     * otherwise the topic is created in a {@link Topic#getBranch()}.
     *
     * @throws PermissionsDeniedException if use cannot post in the first visible branch, she has no permissions
     * @throws CouldNotOpenPageException  if user was not able to find and open a branch with the specified name
     */

    public static Topic createTopic(Topic topic) throws PermissionsDeniedException, CouldNotOpenPageException {
        if (topic.getBranch() == null) {
            Branch branch = new Branch(branchPage.getBranchList().get(0).getText());
            topic.withBranch(branch);
        }
        return createNewTopic(topic);
    }

    public static void createCodeReview(Topic topic) throws PermissionsDeniedException, CouldNotOpenPageException {
        if (topic.getBranch() == null) {
            Branch branch = new Branch(branchPage.getBranchList().get(0).getText());
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
        answerToTopic(topic, topic.getLastPost().getPostContent());
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

    private static boolean findTopic(Topic topic) throws CouldNotOpenPageException {
        boolean found = false;

        for (WebElement topics : topicPage.getTopicsList()) {
            if (topics.getText().trim().equals(topic.getTitle().trim())) {
                topics.click();
                found = true;
                break;
            }
        }
        return found;
    }


    private static void answerToTopic(Topic topic, String answer) throws PermissionsDeniedException {
        postPage.getNewButton().click();
        topicPage.getMainBodyArea().sendKeys(answer);
        clickAnswerToTopicButton(topic);
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
    private static boolean openTopicInCurrentBranch(int numberOfPagesToCheck, String topicToFind)
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


    public static boolean senseToPageNext(Topic topic) {
        WebElement bottomRowOfTopics = topicPage.getLastTopicLine();
        System.out.println(bottomRowOfTopics.getText());
        System.out.println(bottomRowOfTopics.findElement(By.className("sticky")).getText());
        System.out.println("Topic date is " + topic.getModificationDate());
        //if (bottomRowOfTopics.findElements(By.xpath("*/span[contains(@class,'sticky')]")).size()>0) return true;
        String dateFromBottomRowOfTopics = bottomRowOfTopics.findElement(By.xpath("*/a[contains(@class,'date')]")).getText().trim();
        return isTopicNewer(DateTime.now(), dateFromBottomRowOfTopics);
    }


    /**
     * Sets state for checkbox element
     *
     * @param checkboxElement the checkbox web element
     * @param state           the state: true - checked, false - unchecked, null - the element is not used
     */

    private static void setCheckboxState(WebElement checkboxElement, Boolean state) {
        if (state == null) {
            return;
        }
        if (state && !checkboxElement.isSelected()) {
            checkboxElement.click();
        } else if (!state && checkboxElement.isSelected()) {
            checkboxElement.click();
        }
    }


    /**
     * Sets the end date in the poll.
     *
     * @param poll the poll.
     */
    private static void setPollsEndDate(Poll poll) {
        String date;
        if ((date = dateToString(poll.getEndDate(), POLL_END_DATE_FORMAT)) != null) {
            topicPage.getTopicsPollEndDateField().sendKeys(date);
        }

    }

    /**
     * Returns date in string type.
     *
     * @param date  the date.
     * @param format the format of date in string.
     * @return the date in the string.
     */


    /**
     * Create new topic
     *
     * @param topic the topic representation
     * @throws PermissionsDeniedException
     */
    private static Topic createNewTopic(Topic topic) throws PermissionsDeniedException {
        Branches.openBranch(topic.getBranch().getTitle());
        clickCreateTopic();
        fillTopicFields(topic);
        fillPollSpecificFields(topic.getPoll());
        clickAnswerToTopicButton(topic);
        topic.setModificationDate(org.joda.time.DateTime.now().plusMinutes(1));
        return topic;
    }

    private static void clickAnswerToTopicButton(Topic topic) throws PermissionsDeniedException {
        try {
            topicPage.getPostButton().click();
        } catch (NoSuchElementException e) {
            throw new PermissionsDeniedException("User does not have permissions to leave posts in branch "
                    + topic.getBranch().getTitle());
        }
    }

    private static void fillPollSpecificFields(Poll poll) {
        if (poll != null) {
            topicPage.getTopicPollTitleField().sendKeys(poll.getTitle());
            WebElement optionsField = topicPage.getTopicPollItemsField();
            for (String option : poll.getOptions()) {
                optionsField.sendKeys(option + "\n");
            }
            setPollsEndDate(poll);
            setCheckboxState(topicPage.getTopicsPollMultipleChecker(), poll.isMultipleAnswers());
        }
    }

    private static void fillTopicFields(Topic topic) {
        setCheckboxState(topicPage.getTopicSticked(), topic.getSticked());
        setCheckboxState(topicPage.getTopicAnnouncement(), topic.getAnnouncement());
        topicPage.getSubjectField().sendKeys(!topic.getTitle().equals("") ? topic.getTitle() : randomString(15));
        topicPage.getMainBodyArea().sendKeys(topic.getFirstPost().getPostContent());
    }

    private static void clickCreateTopic() throws PermissionsDeniedException {
        try {
            topicPage.getNewButton().click();
        } catch (NoSuchElementException e) {
            throw new PermissionsDeniedException();
        }
    }


    /**
     * Returns date in string type.
     *
     * @param date   the date.
     * @param format the format of date in string.
     * @return the date in the string.
     */
    private static String dateToString(Date date, String format) {
        if (date != null) {
            return new SimpleDateFormat(format).format(date);
        }
        return null;
    }


    /**
     * Create new topic
     *
     * @param topic
     *            the topic representation
     * @throws PermissionsDeniedException
     */

}
