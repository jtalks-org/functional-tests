package org.jtalks.tests.jcommune.tests.main;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.testng.Assert.assertTrue;

/**
 * @author masyan
 */
public class JC97WhoIsOnlineByAnonymous {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void setupCase(String appUrl) {
		driver.get(appUrl);
	}

	@Test
	public void whoIsOnlineByAnonymousTest() {
		int count = new Integer(mainPage.getUsersOnlineCount().getText()).intValue();
		int countGuests = new Integer(mainPage.getGuestsUsersOnlineCount().getText()).intValue();
		int countRegistered = new Integer(mainPage.getRegisteredUsersOnlineCount().getText()).intValue();

		assertTrue(count > 0);
		assertTrue(countGuests > 0);
		assertTrue(countRegistered >= 0);
	}
}
