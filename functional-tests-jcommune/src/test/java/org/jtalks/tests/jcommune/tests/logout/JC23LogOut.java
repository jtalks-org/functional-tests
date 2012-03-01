package org.jtalks.tests.jcommune.tests.logout;

import org.jtalks.tests.jcommune.pages.LogOutPage;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.getApplicationContextPath;
import static org.jtalks.tests.jcommune.Assert.Exsistence.*;

/**
 * @autor masyan
 */
public class JC23LogOut {

	LogOutPage logOutPage;

	@BeforeMethod
	@Parameters({"app-url", "uUsername", "uPassword"})
	public void setupCase(String appUrl, String username, String password) {
		driver.get(appUrl);
		signIn(username, password);
		logOutPage = new LogOutPage();
		logOutPage.init(driver);
	}

	@Test
	public void logOutTest() {
		logOutPage.getLogOutButton().click();

		assertNotExistBySelector(driver, logOutPage.logOutButtonSel);
		assertNotExistBySelector(driver, logOutPage.currentUsernameLinkSel);

	}

}
