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

package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.topic.Poll;
import org.jtalks.tests.jcommune.webdriver.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.topic.Topics;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Guram Savinov
 */
public class TopicPost {

    private Topic topic;

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
		driver.get(appUrl);
        mainPage.logOutIfLoggedIn();
        topic = new Topic("subject", "message");
    }

    @Test
    public void signUpAndCreateTopic() throws Exception {
        Topics.signUpAndcreateTopic(topic);
    }

    @Test(expectedExceptions = PermissionsDeniedException.class)
    public void createTopicAsAnonymous() throws Exception {
        Topics.createTopic(topic);
    }

    @Test
    public void createStickedTopic() throws Exception {
        topic.setSticked(true);
        Topics.signUpAndcreateTopic(topic);
    }

    @Test
    public void createAnnouncementTopic() throws Exception {
        topic.setAnnouncement(true);
        Topics.signUpAndcreateTopic(topic);
    }

    @Test
    public void createPoll() throws Exception {
        Poll poll = new Poll("poll title");
        for (int i = 0; i < 5; i++) {
            poll.addItem("item" + i);
        }
        topic.setPoll(poll);
        Topics.signUpAndcreateTopic(topic);
    }
}
