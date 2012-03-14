package org.jtalks.tests.jcommune.tests.post;

import org.jtalks.tests.jcommune.pages.PostPage;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotEmptyCollection;
import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomTopic;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 */
public class JC30SecurityAnonymousToPost {
	PostPage postPage;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		clickOnRandomBranch();
		clickOnRandomTopic();
		postPage = new PostPage(driver);
	}

	@Test
	public void securityAnonymousToPostTest() {
		//view the posts list
		List<WebElement> posts = postPage.getPostsList();
		assertNotEmptyCollection(posts);

		//create post
		assertNotExistBySelector(driver, postPage.newButtonSel);

		//delete topic (anonymous haven't topic which created by him)
		assertNotExistBySelector(driver, postPage.deleteTopicButtonSel);

		//delete post (anonymous haven't post which created by him)
		assertNotExistBySelector(driver, postPage.deleteButtonNearLastPostSel);

		//edit topic
		assertNotExistBySelector(driver, postPage.editTopicButtonSel);

		//edit post
		assertNotExistBySelector(driver, postPage.editPostButtonSel);
	}
}
