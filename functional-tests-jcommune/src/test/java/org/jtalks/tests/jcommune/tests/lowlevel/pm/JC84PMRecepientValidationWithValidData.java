package org.jtalks.tests.jcommune.tests.lowlevel.pm;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.pmPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @author masyan
 */
public class JC84PMRecepientValidationWithValidData {
	String username2;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2"})
	public void setupCase(String appUrl, String username, String password, String username2) {
		driver.get(appUrl);
		signIn(username, password);
		this.username2 = username2;
		pmPage.getPmInboxLink().click();
		pmPage.getPmNewMessageLink().click();
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
	public void pmRecepientValidationWithValidDataTest() {
		pmPage.getToField().sendKeys(this.username2);
		pmPage.getSendButton().click();
		assertionNotExistById(driver, pmPage.recipientErrorMessageSel);
	}
}
