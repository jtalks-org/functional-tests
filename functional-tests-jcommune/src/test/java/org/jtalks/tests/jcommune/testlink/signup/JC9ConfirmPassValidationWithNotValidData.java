package org.jtalks.tests.jcommune.testlink.signup;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import utils.StringHelp;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.signUpPage;

/**
 * This functional test covers test case JC9
 *
 * @author masyan
 * @author erik
 */
public class JC9ConfirmPassValidationWithNotValidData {

    @DataProvider(name = "notValidConfirmPassword")
    public Object[][] notValidConfirmPassword() {
        String validPassword = StringHelp.getRandomString(10);
        String differentCase = validPassword.toUpperCase();
        String empty = "";
        String notEquals = validPassword + "notEquals";
        String notEqualsByBSStart = " " + validPassword;
        String notEqualsByBSEnd = validPassword + " ";
        return new Object[][]{
                {validPassword, differentCase},
                {validPassword, notEquals},
                {validPassword, empty},
                {validPassword, notEqualsByBSStart},
                {validPassword, notEqualsByBSEnd}
        };
    }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        signUpPage.getSignUpButton().click();
    }


    @Test(dataProvider = "notValidConfirmPassword")
    public void confirmPasswordValidationForRegiteringFormTest(String pass, String confirm) {
        signUpPage.getPasswordField().clear();
        signUpPage.getPasswordField().sendKeys(pass);

        signUpPage.getPasswordConfirmField().clear();
        signUpPage.getPasswordConfirmField().sendKeys(confirm);

        signUpPage.getSubmitButton().click();
        assertElementExistsBySelector(driver, signUpPage.passwordConfirmErrorMessageSel);
    }
}
