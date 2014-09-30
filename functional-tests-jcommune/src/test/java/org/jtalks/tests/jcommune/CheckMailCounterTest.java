package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.openqa.selenium.NoSuchElementException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.testng.Assert.assertTrue;

@Test(groups = "UI_tests")
public class CheckMailCounterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(Users.class);
    User registeredUser = new User("registered", "registered");

    @BeforeMethod(alwaysRun = true)
    @Parameters("appUrl")
    public void setup(String appUrl) {
        driver.get(appUrl);
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void checkMailCounterIsNotShowForLogOutUser() throws Exception {
        mainPage.logOutIfLoggedIn(driver);
        assertTrue(mainPage.getMailCounter().isEnabled());
        LOGGER.info("User is logout, counter should be not shown");
    }

    @Test
    public void checkMailCounterIsShowForRegisteredUser() throws ValidationException {
        Users.signIn(registeredUser);
        assertTrue(mainPage.getMailCounter().isEnabled());
        LOGGER.info("User is SignIN, counter should be shown");
    }

}
