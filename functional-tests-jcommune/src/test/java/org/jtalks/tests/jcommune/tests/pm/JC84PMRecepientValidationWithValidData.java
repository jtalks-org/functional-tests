package org.jtalks.tests.jcommune.tests.pm;

import org.jtalks.tests.jcommune.pages.PMPage;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.Assert.Exsistence.assertNotExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;

/**
 * @autor masyan
 */
public class JC84PMRecepientValidationWithValidData {
	PMPage pmPage;
	String username2;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2"})
	public void setupCase(String appUrl, String username, String password, String username2) {
		driver.get(appUrl);
		signIn(username, password);
		this.username2 = username2;
		pmPage = new PMPage(driver);
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
		assertNotExistById(driver, pmPage.recipientErrorMessageSel);
	}
}
