package org.jtalks.tests.jcommune.tests.pm;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.jtalks.tests.jcommune.pages.PMPage;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.CollectionHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsByCssSelector;
import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;

/**
 * @author yacov
 */
public class JC157SelectOneMessageInOutbox extends JCommuneSeleniumTest {

    @BeforeClass(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public static void setupCase(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
    }

    @Test
    public void deleteButtonShouldBeDisabledByDefault() {
        pmPage.getPmOutboxLink().click(); //Enter into PM Outbox
        assertElementExistsBySelector(driver, PMPage.DEL_BUTTON_DISABLED);
    }

    @Test
    public void checkingPmShouldEnableDeleteButtonAndUncheckingShouldDisableIt() throws Exception {
        pmPage.getPmOutboxLink().click();
        CollectionHelp.getFirstWebElementFromCollection(pmPage.getInboxCheckboxes()).click();  //Click on the checkbox of the first message

        assertElementExistsByCssSelector(driver, PMPage.DEL_BUTTON_ENABLED);

        pmPage.checkedCheckbox.click(); //Un-check highlighted message
        assertElementExistsBySelector(driver, PMPage.DEL_BUTTON_DISABLED);
    }

    @AfterClass(alwaysRun = true)
    @Parameters({"app-url"})
    public static void destroy(String appUrl) {
        logOut(appUrl);
    }

}
