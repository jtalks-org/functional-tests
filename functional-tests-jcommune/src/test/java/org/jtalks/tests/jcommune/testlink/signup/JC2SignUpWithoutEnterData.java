package org.jtalks.tests.jcommune.testlink.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC2
 *
 * @author masyan
 * @author erik
 */
public class JC2SignUpWithoutEnterData {

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        signUpPage.getSignUpButton().click();
    }

    @Test
    public void registrationWithEmptyDataTest() {
        signUpPage.getSubmitButton().click();
        assertElementExistsBySelector(driver, signUpPage.usernameErrorMessageSel);
    }
}