package org.jtalks.tests.jcommune.tests.pm;
import org.jtalks.tests.jcommune.assertion.Exsistence;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.logOut;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.pmPage;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signIn;
import static org.testng.Assert.assertEquals;

/**
 * @author yacov
 */
public class JC165DeleteOneMessageInInbox {
	String username;
	String password;
	String username2;
	String password2;
	String appUrl;

	@BeforeMethod(alwaysRun = true)
	@Parameters({"app-url", "uUsername", "uPassword", "uUsername2", "uPassword2"})
	public void setupCase(String appUrl, String username, String password, String username2, String password2) {
		driver.get(appUrl);
		signIn(username, password);
		this.username = username;
		this.password = password;
		this.username2 = username2;
		this.password2 = password2;
		this.appUrl = appUrl;
	}

	@AfterMethod(alwaysRun = true)
	@Parameters({"app-url"})
	public void destroy(String appUrl) {
		logOut(appUrl);
	}

	@Test
 public void JC165DeleteOneMessageInInbox() {
		pmPage.getPmInboxLink().click();   //Enter to Inbox
        CollectionHelp.getFirstWebElementFromCollection(pmPage.getInboxCheckboxes()).click();  //Click on the checkbox of the first message
        pmPage.getDelButton().click();  //Click on 'Delete' button
        Exsistence.assertionExistBySelector(driver, pmPage.jqiMessageSel);   //Control that Alert Message appears
        pmPage.getJqiButtonCancel().click();   //Click on 'Cancel'
        Exsistence.assertionExistBySelector(driver, pmPage.сheckedCheckboxSel);   //Control that a checkbox is still checked
        pmPage.getDelButton().click();   //Click again on 'Delete' button
        Exsistence.assertionExistBySelector(driver, pmPage.jqiMessageSel);      //Control that Alert Message appears
        pmPage.getJqiButtonOK().click(); //Click on 'OK' button of Alert Message
        Exsistence.assertionNotExistBySelector(driver, pmPage.сheckedCheckboxSel); //Control, that there is no more checked message on Inbox

         }
		}
