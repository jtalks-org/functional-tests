package org.jtalks.tests.jcommune.testlink.topic;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * @autor masyan
 */
public class JC109ShowingRegisteredUsersWhoBrowsingTopic {

	String topicUrl;
	String username;
	String url;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
//		driver.get(appUrl);
//		signIn(username, password);
//		clickOnRandomBranch();
//		createTopicForTest();
//		this.topicUrl = driver.getCurrentUrl();
//		this.username = username;
//		this.url = appUrl;
	}

	@Test
	public void showingRegisteredUsersWhoBrowsingTopic() {
//		//step 1
//		assertTrue(containsTextInCollection(topicPage.getWhoBrowsingTopic(), this.username));
//		//step 2
//		logOut(this.url);
//		driver.get(this.topicUrl);
//		assertFalse(containsTextInCollection(topicPage.getWhoBrowsingTopic(), this.username));
	}
}
