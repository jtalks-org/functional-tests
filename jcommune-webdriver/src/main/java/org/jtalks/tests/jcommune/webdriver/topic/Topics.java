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
import org.jtalks.tests.jcommune.webdriver.exceptions.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.topicPage;

/**
 * Contain topic actions like creating, deleting etc.
 * 
 * @author Guram Savinov
 */
public class Topics {

	/**
	 * Sign-up new user and create new topic
	 * 
	 * @param topic
	 *            the topic representation
	 * @throws ValidationException
	 * @throws CouldNotSignInUserException
	 * @throws PermissionsDeniedException
	 */
	public static void signUpAndcreateTopic(Topic topic)
			throws ValidationException, CouldNotSignInUserException,
			PermissionsDeniedException {
		User user = Users.signUp();
		Users.signIn(user);
		createTopic(topic);
	}
	
	public static void signUpAndcreateTopicInBranch(Topic topic, String branchTitle)
			throws ValidationException, CouldNotSignInUserException,
			PermissionsDeniedException {
		User user = Users.signUp();
		Users.signIn(user);
		createTopicInBranch(topic, branchTitle);
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
		
		System.out.println(branchPage.getBranchList().size()+ " amount of branches");
		
		for (WebElement x:branchPage.getBranchList()) 
				System.out.println(x.getText());
		
		// Open first branch from the main page top
	/*	branchPage.getBranchList().get(0).click();
		
		//Printing total amount of branches found
		System.out.println(branchPage.getBranchList().size()+ " amount of branches");

		// Create new topic
		try {
			topicPage.getNewButton().click();
			setCheckboxState(topicPage.getTopicSticked(), topic.getSticked());
			setCheckboxState(topicPage.getTopicAnnouncement(),
					topic.getAnnouncement());
		} catch (NoSuchElementException e) {
			throw new PermissionsDeniedException();
		}

		// Type subject and message
		topicPage.getSubjectField().sendKeys(topic.getTitle());
		Post firstPost = topic.getPosts().get(0);
		topicPage.getMessageField().sendKeys(firstPost.getPostContent());

		// Type poll title and options
		Poll poll = topic.getPoll();
		if (poll != null) {
			topicPage.getTopicPollTitleField().sendKeys(poll.getTitle());
			WebElement optionsField = topicPage.getTopicPollItemsField();
			for (String option : poll.getOptions()) {
				optionsField.sendKeys(option + "\n");
			}
		}

		topicPage.getPostButton().click();*/
	}

	
	public static void createTopicInBranch(Topic topic, String branchTitle)
			throws PermissionsDeniedException {
		
		System.out.println(branchPage.getSearchedBranch().getText());
		
	
		
		// Open first branch from the main page top
	/*	branchPage.getBranchList().get(0).click();
		
		//Printing total amount of branches found
		System.out.println(branchPage.getBranchList().size()+ " amount of branches");

		// Create new topic
		try {
			topicPage.getNewButton().click();
			setCheckboxState(topicPage.getTopicSticked(), topic.getSticked());
			setCheckboxState(topicPage.getTopicAnnouncement(),
					topic.getAnnouncement());
		} catch (NoSuchElementException e) {
			throw new PermissionsDeniedException();
		}

		// Type subject and message
		topicPage.getSubjectField().sendKeys(topic.getTitle());
		Post firstPost = topic.getPosts().get(0);
		topicPage.getMessageField().sendKeys(firstPost.getPostContent());

		// Type poll title and options
		Poll poll = topic.getPoll();
		if (poll != null) {
			topicPage.getTopicPollTitleField().sendKeys(poll.getTitle());
			WebElement optionsField = topicPage.getTopicPollItemsField();
			for (String option : poll.getOptions()) {
				optionsField.sendKeys(option + "\n");
			}
		}

		topicPage.getPostButton().click();*/
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
}
