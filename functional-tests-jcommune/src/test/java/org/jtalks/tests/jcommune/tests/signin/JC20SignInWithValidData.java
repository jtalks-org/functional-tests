package org.jtalks.tests.jcommune.tests.signin;

import org.jtalks.tests.jcommune.common.UserActions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Existance.assertElementExistsBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * @author masyan
 * @author erik
 * @author Guram Savinov
 */
public class JC20SignInWithValidData {
    String userName;
    String password;

    @BeforeMethod(alwaysRun = true)
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setupCase(String appUrl, String userName, String password) {
        driver.get(appUrl);

        this.userName = userName;
        this.password = password;
    }

    @AfterMethod(alwaysRun = true)
    @Parameters({"app-url"})
    public void destroy(String appUrl) {
        logOut(appUrl);
    }

    @Test
    public void signInWithValidDataTest() {
        UserActions.login(userName, password);
    }

}
