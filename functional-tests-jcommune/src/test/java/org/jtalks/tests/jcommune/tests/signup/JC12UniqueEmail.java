package org.jtalks.tests.jcommune.tests.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC12
 *
 * @author masyan
 * @author erik
 */
public class JC12UniqueEmail {

    String existEmail;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uEmail"})
    public void setupCase(String appUrl, String email) {
        this.existEmail = email;
        driver.get(appUrl);
        signUpPage.getSignUpButton().click();
    }

    @Test
    public void emailUniqueValidationTest() {
        signUpPage.getEmailField().clear();
        signUpPage.getEmailField().sendKeys(this.existEmail);
        signUpPage.getSubmitButton().click();
        assertElementExistsBySelector(driver, signUpPage.emailErrorMessageSel);
    }


}
