package org.jtalks.tests.jcommune.tests.post;

import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import java.util.ArrayList;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionContainsStringInList;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * @author masyan
 */
public class JC71PagingShowAllButton {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		createAnswerForTest(StringHelp.getRandomString(10));
		createAnswerForTest(StringHelp.getRandomString(10));
        createAnswerForTest(StringHelp.getRandomString(10));
        createAnswerForTest(StringHelp.getRandomString(10));
		createAnswerForTest(StringHelp.getRandomString(10));
		createAnswerForTest(StringHelp.getRandomString(10));
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
    // This test doesn't pass with firefox driver. There are difference between return values of
    // postPage.getPagesButtons method. getPagesButtons returns 4 buttons independently: 1, 2, Show pages, Next topic.
    // I can't fixed it (pvolkov), but I fixed test for HtmlUnit.
	public void pagingShowAllButtonTest() {
		postPage.getShowAllButton().click();
        for (WebElement el : postPage.getPagesButtons()) {
			ArrayList list = new ArrayList<String>();
            // el.getText() returns "Show pages Next topic" in right case, but "1 2 Show all Next topic" in wrong case.
			list.add("Show pages Next topic");
                assertionContainsStringInList(list, el.getText());
		}
	}


}
