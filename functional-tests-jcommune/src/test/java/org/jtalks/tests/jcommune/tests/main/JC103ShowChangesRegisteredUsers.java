package org.jtalks.tests.jcommune.tests.main;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.registerNewUser;
import static org.testng.Assert.assertTrue;

/**
 * @autor masyan
 */
public class JC103ShowChangesRegisteredUsers {

	public class JC102ShowChangesPostCount {

		int prevRegCount;

		@BeforeMethod(alwaysRun = true)
		@Parameters({"app-url", "uUsername", "uPassword"})
		public void setupCase(String appUrl, String username, String password) {
			driver.get(appUrl);
			prevRegCount = new Integer(mainPage.getUsersCount().getText()).intValue();
		}

		@Test
		@Parameters({"app-url"})
		public void showChangesRegisteredUsersTest(String appUrl) {
			String username = StringHelp.getRandomString(10);
			String email = StringHelp.getRandomEmail();
			String pass = StringHelp.getRandomString(10);
			registerNewUser(username, email, pass);
			driver.get(appUrl);
			int count = new Integer(mainPage.getUsersCount().getText()).intValue();
			assertTrue(prevRegCount + 1 == count);
		}
	}
}
