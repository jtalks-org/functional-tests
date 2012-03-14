package org.jtalks.tests.jcommune.tests.post;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import java.util.List;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertExistBySelector;
import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotEmptyCollection;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createAnswerForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC36SecurityAdminToPost {
	PostPage postPage;

	String urlTopic;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "aUsername", "aPassword", "uUsername2", "uPassword2"})
	public void setupCase(String appUrl, String username, String password, String username2, String password2) {
		driver.get(appUrl);
		signIn(username2, password2);
		clickOnRandomBranch();
		//create topic and answer by User2
		createTopicForTest();
		createAnswerForTest(StringHelp.getRandomString(10));
		//save url to this topic
		urlTopic = driver.getCurrentUrl();
		logOut(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		//create topic by User1
		createTopicForTest();
		createAnswerForTest(StringHelp.getRandomString(10));
		postPage = new PostPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void securityAdminToPostTest() {
		//view the posts list
		List<WebElement> posts = postPage.getPostsList();
		assertNotEmptyCollection(posts);

		//create post
		assertExistBySelector(driver, postPage.newButtonSel);


		//delete topic (user is owner this topic)
		assertExistBySelector(driver, postPage.deleteTopicButtonSel);

		//delete post (user is owner this post)
		assertExistBySelector(driver, postPage.deleteButtonNearLastPostSel);

		//edit topic (user is owner this topic)
		assertExistBySelector(driver, postPage.editTopicButtonSel);

		//edit post (user is owner this post)
		assertExistBySelector(driver, postPage.editPostButtonSel);

		driver.get(urlTopic);

		//delete topic (user is't owner this topic)
		assertExistBySelector(driver, postPage.deleteTopicButtonSel);

		//delete post (user is't owner this post)
		assertExistBySelector(driver, postPage.deleteButtonNearLastPostSel);

		//edit topic (user is't owner this topic)
		assertExistBySelector(driver, postPage.editTopicButtonSel);

		//edit post (user is't owner this post)
		assertExistBySelector(driver, postPage.editPostButtonSel);
	}
}
