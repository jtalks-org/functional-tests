package org.jtalks.tests.jcommune.common;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.assertion.Existance.assertionExistById;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * Contain user actions like login, logout etc.
 *
 * @author Guram Savinov
 */
public class UserActions {

    /**
     * Login by userName and password. Fail a test when
     * user login is unsuccessful.
     *
     * @param userName the userName
     * @param password the password
     * @return the {@code User} instance
     */
    public static User login(String userName, String password) {
        //step 1
        mainPage.getLoginLink().click();
        assertionExistById(driver, signInPage.signInFormSel);

        //step 2
        signInPage.getUsernameField().sendKeys(userName);
        signInPage.getPasswordField().sendKeys(password);
        signInPage.getSubmitButton().click();
        assertElementExistsBySelector(driver, mainPage.currentUsernameLinkSel);

        return new User(userName, password);
    }

}
