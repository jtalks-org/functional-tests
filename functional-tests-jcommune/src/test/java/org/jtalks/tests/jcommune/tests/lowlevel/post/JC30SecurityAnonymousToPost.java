package org.jtalks.tests.jcommune.tests.lowlevel.post;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.List;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotEmptyCollection;
import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomTopic;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.postPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @author masyan
 */
public class JC30SecurityAnonymousToPost {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		String branch = driver.getCurrentUrl();
		createTopicForTest();
		logOut(appUrl);
		driver.get(branch);
		clickOnRandomTopic();
	}

	@Test
	public void securityAnonymousToPostTest() {
		//view the posts list
		List<WebElement> posts = postPage.getPostsList();
		assertionNotEmptyCollection(posts);

		//create post
		assertionNotExistBySelector(driver, postPage.newButtonSel);

		//delete topic (anonymous haven't topic which created by him)
		assertionNotExistBySelector(driver, postPage.deleteTopicButtonSel);

		//delete post (anonymous haven't post which created by him)
		assertionNotExistBySelector(driver, postPage.deleteButtonNearLastPostSel);

		//edit topic
		assertionNotExistBySelector(driver, postPage.editTopicButtonSel);

		//edit post
		assertionNotExistBySelector(driver, postPage.editPostButtonSel);
	}
}
