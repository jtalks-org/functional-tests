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
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

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
	private static final String POLL_END_DATE_FORMAT = "dd-MM-yyyy";

	/**
     * Signs-up up a new random user and creates the topic in the first available branch.
     *
     * @throws ValidationException if specified topic does not pass forum validation
     * @throws PermissionsDeniedException if use cannot post in the first visible branch, she has no permissions
     */
    public static void signUpAndCreateTopic(Topic topic) throws ValidationException, PermissionsDeniedException {
        User user = Users.signUp();
        Users.signIn(user);
        createTopic(topic);
    }

    /**
     * Creates new topic in the first visible branch.
     *
     * @param topic the topic representation
     * @throws PermissionsDeniedException if use cannot post in the first visible branch, she has no permissions
     */
    public static void createTopic(Topic topic) throws PermissionsDeniedException {
        branchPage.getBranchList().get(0).click();
        createNewTopic(topic);

    }

    /**
     * Creates new topic in the specified branch.
     *
     * @param branchTitle the title of branch to post in it. If there are 2 or more branches with the same name, then
     *                    only first one is taken
     * @throws PermissionsDeniedException if use cannot post in the first visible branch, she has no permissions
     * @throws CouldNotOpenPageException if user was not able to find and open a branch with the specified name
     */

    public static void createTopic(Topic topic, String branchTitle) throws PermissionsDeniedException,
            CouldNotOpenPageException {
        openBranch(branchTitle);
        createNewTopic(topic);
	}

    public static void postAnswer(Topic topic, String branchTitle)
            throws PermissionsDeniedException, CouldNotOpenPageException, InterruptedException {
        openBranch(branchTitle);
        if (openTopicInCurrentBranch(6, topic.getTitle())) {
            answerToTopic(topic.getPosts().get(0).getPostContent());
            LOGGER.info("postAnswerToTopic {}", topic.getTitle());
        }
    }

    private static void openBranch(String branchTitle) throws CouldNotOpenPageException {
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
			throw new CouldNotOpenPageException(branchTitle);
		}
	}

	private static boolean findTopic(String topicTitle)
			throws CouldNotOpenPageException {
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

    /**
     * Looks through several pages of the branch in order to find the topic with the specified id.
     * @param numberOfPagesToCheck since the topic might not be on the first page (either someone simultaneously
     *                             creates a lot of topics, or there are a lot of sticked topics),
     *                             we have to iteration through this number of pages to search for the topic
     * @param topicToFind a topic id to look for
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
     * @param poll the poll.
     */
    private static void setPollsEndDate(Poll poll){
        if(poll==null){return;}
        String date = null;
        if((date=dateToString(poll.getEndDate(),POLL_END_DATE_FORMAT))!=null){
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
    private static void createNewTopic(Topic topic) throws PermissionsDeniedException{
        try {
            topicPage.getNewButton().click();
            setCheckboxState(topicPage.getTopicSticked(), topic.getSticked());
            setCheckboxState(topicPage.getTopicAnnouncement(), topic.getAnnouncement());
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
            setCheckboxState(topicPage.getTopicsPollMultipleChecker(),poll.isMultipleAnswers());
        }

        topicPage.getPostButton().click();
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
	
}
