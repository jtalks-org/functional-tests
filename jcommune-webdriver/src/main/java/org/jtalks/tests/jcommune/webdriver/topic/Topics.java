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
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignInUserException;
import org.jtalks.tests.jcommune.webdriver.exceptions.MailtrapException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.openqa.selenium.NoSuchElementException;

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
     */
    public static void signUpAndcreateTopic(Topic topic) throws MailtrapException, ValidationException,
            CouldNotSignInUserException {
        User user = Users.signUp();
        Users.signIn(user);
        createTopic(topic);
    }

    /**
     * Create new topic
     *
     * @param topic the topic representation
     */
    public static void createTopic(Topic topic) {
        // Open first branch from the main page top
        branchPage.getBranchList().get(0).click();

        // Create new topic
        try {
            topicPage.getNewButton().click();
        } catch (NoSuchElementException e) {
            throw new CouldNotOpenPageException("new branch", e);
        }
        topicPage.getSubjectField().sendKeys(topic.getTitle());
        Post firstPost = topic.getPosts().get(0);
        topicPage.getMessageField().sendKeys(firstPost.getPostContent());
        topicPage.getPostButton().click();
    }
}
