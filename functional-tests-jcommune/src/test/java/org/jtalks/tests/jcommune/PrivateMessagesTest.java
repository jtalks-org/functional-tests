package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.PrivateMessages;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.privatemessage.PrivateMessage;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author stanislav bashkirtsev
 * @author Varro
 */
public class PrivateMessagesTest {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test (enabled = false)
    public void pmWithCorrectFields_ShouldPass() throws Exception {
        User receiver = Users.signUpAndSignIn();
        Users.logout();
        Users.signUpAndSignIn();
        PrivateMessage pm = new PrivateMessage(receiver);
        PrivateMessages.sendPrivateMessage(pm);
        PrivateMessages.assertPmReceived(receiver, pm);
    }
}