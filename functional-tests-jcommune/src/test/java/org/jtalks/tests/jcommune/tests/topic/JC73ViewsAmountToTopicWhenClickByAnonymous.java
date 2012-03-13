package org.jtalks.tests.jcommune.tests.topic;

import org.jtalks.tests.jcommune.pages.TopicPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

import utils.CollectionHelp;


import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * Created by IntelliJ IDEA.
 * User: masyan
 * Date: 13.03.12
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public class JC73ViewsAmountToTopicWhenClickByAnonymous {
	TopicPage topicPage;

	@BeforeMethod
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
		clickOnRandomBranch();
		topicPage = new TopicPage(driver);
	}

	@Test
	public void BreadCrumbsGoToBranchTest() {
		//amount views for first topic
		int amountBefore = new Integer(CollectionHelp.getFirstWebElementFromCollection(topicPage.getAmountsOfViewTopics()).getText()).intValue();

		CollectionHelp.getFirstWebElementFromCollection(topicPage.getTopicsList()).click();

		topicPage.getBackButton().click();

		int amountAfter = new Integer(CollectionHelp.getFirstWebElementFromCollection(topicPage.getAmountsOfViewTopics()).getText()).intValue();

		assertEquals(amountBefore + 1, amountAfter);
	}
}
