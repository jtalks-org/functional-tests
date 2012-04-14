package org.jtalks.tests.jcommune.tests.logout;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOutPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.mainPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @author masyan
 */
public class JC23LogOut {

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
	}

	@Test
	public void logOutTest() {
		logOutPage.getLogOutButton().click();

		assertionNotExistBySelector(driver, logOutPage.logOutButtonSel);
		assertionNotExistBySelector(driver, mainPage.currentUsernameLinkSel);

	}

}
