package org.jtalks.tests.jcommune.tests.lowlevel.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC5
 *
 * @author masyan
 * @author erik
 */
public class JC5EmailValidationWithNotValidData {


    @DataProvider(name = "notValidEmail")
    public Object[][] notValidEmail() {
        String notValidEmail = StringHelp.getRandomString(6);
        String emptyEmail = "";
        return new Object[][]{
                {notValidEmail}
        };
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        signUpPage.getSignUpButton().click();
    }

    @Test(dataProvider = "notValidEmail")
    public void emailValidationWithNotValidDataTest(String email) {
        signUpPage.getEmailField().clear();
        signUpPage.getEmailField().sendKeys(email);
        signUpPage.getSubmitButton().click();
        assertElementExistsBySelector(driver, signUpPage.emailErrorMessageSel);
    }
}
