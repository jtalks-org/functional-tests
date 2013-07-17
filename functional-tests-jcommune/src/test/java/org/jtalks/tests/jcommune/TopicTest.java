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

import java.util.Date;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Guram Savinov
 */
public class TopicTest {

    @BeforeMethod
    @Parameters({ "appUrl" })
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn();
    }

	@Test
	public void signUpAndCreateTopic() throws Exception {
		    Topic topic;
			
		topic = new Topic("subject", "message");
		Topics.signUpAndСreateTopic(topic);
	}

	@Test
	public void signUpAndCreateTopicInBranch() throws Exception {
		Topic topic = new Topic("subject123", "message");
		String branchTitle = "TestBranch2";
        Users.signIn(Users.signUp());
        Topics.createTopic(topic, branchTitle);
    }
	
	@Test
	public void signUpAndPostAnswerToTopic() throws Exception {
		//In this test subject of Topic variable means subject of post we want to add answer to 
		Topic topic = new Topic("subject123", "New final test answer");
		String branchTitle = "TestBranch";
        Users.signIn(Users.signUp());
        Topics.postAnswer(topic, branchTitle);
    }
	
	

    @Test(expectedExceptions = PermissionsDeniedException.class)
	public void createTopicAsAnonymous() throws Exception {
    	Topic topic = new Topic("subject", "message");
		Topics.createTopic(topic);
	}

	@Test
	public void createStickedTopic() throws Exception {
		Topic topic = new Topic("subject", "message");
		topic.setSticked(true);
		Topics.signUpAndСreateTopic(topic);
	}

	@Test
	public void createAnnouncementTopic() throws Exception {
		Topic topic = new Topic("subject", "message");
		topic.setAnnouncement(true);
		Topics.signUpAndСreateTopic(topic);
	}

	@Test
	public void createTopicWithPoll() throws Exception {
		Topic topic = new Topic("subject", "message");
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        topic.setPoll(poll);
        Topics.signUpAndСreateTopic(topic);
	}

    @Test
    public void createTopicWithPollEndDate() throws Exception {
        Topic topic = new Topic("subject", "message");
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        poll.setEndDate(new Date(System.currentTimeMillis()+86400000));
        topic.setPoll(poll);
        Topics.signUpAndСreateTopic(topic);
    }

    @Test
    public void createTopicWithPollMultipleAnswers() throws Exception {
        Topic topic = new Topic("subject", "message");
        Poll poll = new Poll("poll title", new String[]{"option1", "option2", "option3"});
        poll.setMultipleAnswers(true);
        topic.setPoll(poll);
        Topics.signUpAndСreateTopic(topic);
    }

}
