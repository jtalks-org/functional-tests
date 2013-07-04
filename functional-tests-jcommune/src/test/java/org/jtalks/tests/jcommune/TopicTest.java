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

import org.jtalks.tests.jcommune.webdriver.Users;
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
public class TopicTest {
    private Topic topic;
	private String branchTitle;

	@BeforeMethod
	@Parameters({ "appUrl" })
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		mainPage.logOutIfLoggedIn();
		topic = new Topic("subject", "message");
		branchTitle = "TestBranch2";
	}

	@Test
	public void signUpAndCreateTopic() throws Exception {
		Topics.signUpAndСreateTopic(topic);
	}

	@Test
	public void signUpAndCreateTopicInBranch() throws Exception {
        Users.signIn(Users.signUp());
        Topics.createTopic(topic, branchTitle);
    }

    @Test(expectedExceptions = PermissionsDeniedException.class)
	public void createTopicAsAnonymous() throws Exception {
		Topics.createTopic(topic);
	}

	@Test
	public void createStickedTopic() throws Exception {
		topic.setSticked(true);
		Topics.signUpAndСreateTopic(topic);
	}

	@Test
	public void createAnnouncementTopic() throws Exception {
		topic.setAnnouncement(true);
		Topics.signUpAndСreateTopic(topic);
	}

	@Test
	public void createTopicWithPoll() throws Exception {
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        topic.setPoll(poll);
        Topics.signUpAndСreateTopic(topic);
	}
}
