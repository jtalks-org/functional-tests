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
 */
public class Topics {

    /**
     * Sign-up new user and create new topic
     *
     * @param topic the topic representation
     * @throws MailtrapException
     * @throws ValidationException
     * @throws CouldNotSignInUserException
     * @throws PermissionsDeniedException
     */
    public static void signUpAndcreateTopic(Topic topic) throws MailtrapException, ValidationException,
            CouldNotSignInUserException, PermissionsDeniedException {
        User user = Users.signUp();
        Users.signIn(user);
        createTopic(topic);
    }

    /**
     * Create new topic
     *
     * @param topic the topic representation
     * @throws PermissionsDeniedException
     */
    public static void createTopic(Topic topic) throws PermissionsDeniedException {
        // Open first branch from the main page top
        branchPage.getBranchList().get(0).click();

        // Create new topic
        try {
            topicPage.getNewButton().click();
            setStateFlag(topicPage.getTopicStickedFlag(),topic.isSticked());
        } catch (NoSuchElementException e) {
            throw new PermissionsDeniedException();
        }
        topicPage.getSubjectField().sendKeys(topic.getTitle());
        Post firstPost = topic.getPosts().get(0);
        topicPage.getMessageField().sendKeys(firstPost.getPostContent());
        topicPage.getPostButton().click();
    }

    /**
     * Sets state for flag element.
     * @param flagElement the flag web element.
     * @param state the state: true - enabled, false- disabled, null - the element is not use,
     */
    private static void setStateFlag(WebElement flagElement, Boolean state){
        if(state == true){
            if(flagElement.isDisplayed()){
                flagElement.click();
            }
        }else if(state == false){
            if(flagElement.isEnabled()){
                flagElement.click();
            }
        }
    }

}
