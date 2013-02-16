package org.jtalks.tests.jcommune.tests.lowlevel.main;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertTrue;

/**
 * @author masyan
 */
public class JC98WhoIsOnlineByUser {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void whoIsOnlineByUserTest() {
		int count = new Integer(mainPage.getUsersOnlineCount().getText()).intValue();
		int countGuests = new Integer(mainPage.getGuestsUsersOnlineCount().getText()).intValue();
		int countRegistered = new Integer(mainPage.getRegisteredUsersOnlineCount().getText()).intValue();

		assertTrue(count > 0);
		assertTrue(countGuests >= 0);
		assertTrue(countRegistered > 0);
	}
}
