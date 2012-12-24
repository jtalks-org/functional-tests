package org.jtalks.tests.jcommune.testlink.logout;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertionNotExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

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
        JCommuneSeleniumTest.logOut(null);

        assertionNotExistBySelector(driver, mainPage.logOutButtonSel);
        assertionNotExistBySelector(driver, mainPage.currentUsernameLinkSel);

    }

}
