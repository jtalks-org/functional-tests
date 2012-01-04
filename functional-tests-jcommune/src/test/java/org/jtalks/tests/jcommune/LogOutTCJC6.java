package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * This functional test covers LogOut test case TC-JC6
 * http://jtalks.org/display/jcommune/TC-JC6+Log+Out
 *
 * @autor masyan
 */
public class LogOutTCJC6 extends JCommuneSeleniumTest {

	@Test(priority = 1)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void logOutTest(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password, appUrl);
		logOut();
	}
}
