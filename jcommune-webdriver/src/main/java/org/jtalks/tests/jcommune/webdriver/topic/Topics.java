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

package org.jtalks.tests.jcommune.webdriver.topic;

import org.jtalks.tests.jcommune.webdriver.User;
import org.jtalks.tests.jcommune.webdriver.Users;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenBranchException;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignInUserException;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.TopicPage;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.browserlaunchers.Sleeper;
import org.openqa.selenium.internal.seleniumemulation.WaitForCondition;
import org.openqa.selenium.internal.seleniumemulation.WaitForPageToLoad;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.selenium.Wait;

import java.text.SimpleDateFormat;
import java.util.AbstractSequentialList;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeSet;
import java.lang.Integer;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
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
	 * @param topic
	 *            the topic representation
	 * @throws ValidationException
	 * @throws CouldNotSignInUserException
	 * @throws PermissionsDeniedException
	 */
	public static void signUpAndСreateTopic(Topic topic)
			throws ValidationException, CouldNotSignInUserException,
			PermissionsDeniedException {
		User user = Users.signUp();
		Users.signIn(user);
		createTopic(topic);
	}

	/**
	 * Create new topic
	 * 
	 * @param topic
	 *            the topic representation
	 * @throws PermissionsDeniedException
	 */
	public static void createTopic(Topic topic)
			throws PermissionsDeniedException {

		// Open first branch from the main page top
		branchPage.getBranchList().get(0).click();
		createNewTopic(topic);
	}

	/**
	 * Creates new topic in branch.
	 * 
	 * @param topic
	 *            the topic representation.
	 * @param branchTitle
	 *            the title of branch.
	 * @throws PermissionsDeniedException
	 * @throws CouldNotOpenBranchException
	 */
	public static void createTopic(Topic topic, String branchTitle)
			throws PermissionsDeniedException, CouldNotOpenBranchException {
		openBranch(branchTitle);
		createNewTopic(topic);

	}

	public static void postAnswer(Topic topic, String branchTitle)
			throws PermissionsDeniedException, CouldNotOpenBranchException,
			InterruptedException {
		openBranch(branchTitle);
		if (choosePageWithTopics(6, topic.getTitle())){
			answerToTopic(topic.getPosts().get(0).getPostContent());
			LOGGER.info("postAnswerToTopic {}", topic.getTitle());
		}
	}

	private static void openBranch(String branchTitle)
			throws CouldNotOpenBranchException {
		boolean found = false;
		for (WebElement branch : branchPage.getBranchList()) {
			if (branch.getText().equals(branchTitle)) {
				branch.click();
				found = true;
				break;
			}
		}
		if (!found) {
			LOGGER.info("No branch found with name [{}]", branchTitle);
			throw new CouldNotOpenBranchException(branchTitle);
		}
	}

	private static boolean findTopic(String topicTitle)
			throws CouldNotOpenBranchException {
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

	private static void answerToTopic(String answer) {
		topicPage.getNewButton().click();
		topicPage.getMessageField().sendKeys(answer);
		topicPage.getPostButton().click();
	}

	private static boolean choosePageWithTopics(int numberOfPagesToCheck,
			String topicToFind) throws CouldNotOpenBranchException,
			InterruptedException {
		boolean found = false;
		while (!(found=findTopic(topicToFind))) {
			if (!thumbToNextPage(numberOfPagesToCheck)) break;
		}
		if (!found) {
			LOGGER.info("No topic with title [{}]  found", topicToFind);
			throw new CouldNotOpenBranchException(topicToFind);
		}
		return found;
	}

	private static boolean thumbToNextPage(int pagesToCheck)
			throws InterruptedException {
		int maxPagesToCheck = pagesToCheck;
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
		if ((active < maxPagesToCheck) && (active < max)) {
			for (WebElement elem : topicPage.getTopicsButtons()) {
				if (Integer.parseInt(elem.getText().trim()) == (active + 1)) {
					elem.click();
					return true;
				}
			}

		} 
		return false;
	}

	/**
	 * Sets state for checkbox element
	 * 
	 * @param checkboxElement
	 *            the checkbox web element
	 * @param state
	 *            the state: true - checked, false - unchecked, null - the
	 *            element is not used
	 */

	private static void setCheckboxState(WebElement checkboxElement,
			Boolean state) {
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
	 * @param poll
	 *            the poll.
	 */
	private static void setPollsEndDate(Poll poll) {
		if (poll == null) {
			return;
		}
		String date = null;
		if ((date = dateToString(poll.getEndDate(), POLL_END_DATE_FORMAT)) != null) {
			topicPage.getTopicsPollEndDateField().sendKeys(date);
		}

	}

	/**
	 * Returns date in string type.
	 * 
	 * @param date
	 *            the date.
	 * @param format
	 *            the format of date in string.
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
	private static void createNewTopic(Topic topic)
			throws PermissionsDeniedException {
		try {
			topicPage.getNewButton().click();
			setCheckboxState(topicPage.getTopicSticked(), topic.getSticked());
			setCheckboxState(topicPage.getTopicAnnouncement(),
					topic.getAnnouncement());
		} catch (NoSuchElementException e) {
			throw new PermissionsDeniedException();
		}

		// Fill subject and message
		topicPage.getSubjectField().sendKeys(topic.getTitle());
		Post firstPost = topic.getPosts().get(0);
		topicPage.getMessageField().sendKeys(firstPost.getPostContent());

		// Fill poll title, options, end date, multiple answers
		Poll poll = topic.getPoll();
		if (poll != null) {
			topicPage.getTopicPollTitleField().sendKeys(poll.getTitle());
			WebElement optionsField = topicPage.getTopicPollItemsField();
			for (String option : poll.getOptions()) {
				optionsField.sendKeys(option + "\n");
			}
			setPollsEndDate(poll);
			setCheckboxState(topicPage.getTopicsPollMultipleChecker(),
					poll.isMultipleAnswers());
		}

		topicPage.getPostButton().click();
	}
}
