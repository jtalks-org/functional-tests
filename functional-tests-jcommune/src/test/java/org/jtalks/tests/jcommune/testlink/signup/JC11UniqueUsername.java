package org.jtalks.tests.jcommune.testlink.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC11
 *
 * @author masyan
 * @author erik
 */
public class JC11UniqueUsername {
    String existUsername;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername"})
    public void setupCase(String appUrl, String username) {
        this.existUsername = username;
        driver.get(appUrl);
        signUpPage.getSignUpButton().click();
    }

    @Test
    public void usernameUniqueValidationTest() {
        signUpPage.getUsernameField().clear();
        signUpPage.getUsernameField().sendKeys(this.existUsername);
        signUpPage.getSubmitButton().click();
        assertElementExistsBySelector(driver, signUpPage.usernameErrorMessageSel);
    }
}
