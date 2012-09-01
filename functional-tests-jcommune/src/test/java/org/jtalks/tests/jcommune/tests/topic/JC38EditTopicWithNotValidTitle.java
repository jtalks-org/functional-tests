package org.jtalks.tests.jcommune.tests.topic;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.postPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.topicPage;


/**
 * @author masyan
 */
public class JC38EditTopicWithNotValidTitle {

	@DataProvider(name = "notValidTitle")
	public Object[][] notValidTitle() {
		String shortTitle = StringHelp.getRandomString(4);
		String startSpTitle = " " + StringHelp.getRandomString(4);
		String endSpTitle = StringHelp.getRandomString(4) + " ";
		return new Object[][]{
				{shortTitle},
				{startSpTitle},
				{endSpTitle}

		};
	}

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		clickOnRandomBranch();
		createTopicForTest();
		postPage.getEditTopicButton().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);

	}

	@Test(dataProvider = "notValidTitle")
	public void editTopicWithNotValidTitleTest(String title) {

		topicPage.getSubjectField().clear();
		topicPage.getSubjectField().sendKeys(title);
		topicPage.getPostButton().click();

		assertElementExistsBySelector(driver, topicPage.subjectErrorMessageSel);
	}

}
