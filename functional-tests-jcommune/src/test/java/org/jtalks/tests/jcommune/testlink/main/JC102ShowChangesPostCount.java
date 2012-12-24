package org.jtalks.tests.jcommune.testlink.main;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.clickOnRandomBranch;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.createTopicForTest;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertTrue;

/**
 * @autor masyan
 */
public class JC102ShowChangesPostCount {
	int prevPostCount;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		prevPostCount = new Integer(mainPage.getMessagesCount().getText()).intValue();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	@Parameters({"app-url"})
	public void showChangesPostCountTest(String appUrl) {
		clickOnRandomBranch();
		createTopicForTest();
		//go to main page
		driver.get(appUrl);
		int count = new Integer(mainPage.getMessagesCount().getText()).intValue();
		assertTrue(prevPostCount + 1 == count);
	}
}
