/**
 * Copyright (C) 2011  JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.Branches;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.entity.user.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.SignInPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Guram Savinov
 * @author Andrey Ivanov
 */
public class SignInTest {

    @BeforeMethod(alwaysRun = true)
    @Parameters("appUrl")
    public void setup(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class)
    public void signInWithoutActivationRegistrationShouldFailLogin_JC_564() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
    }

    @Test(groups = "smoke")
    public void usernameAndPasswordCorrectShouldLogin_JC_20() throws Exception {
        User user = Users.signUp();
        Users.activate(user);
        Users.logout();
        Users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void usernameEmptyShouldFailLogin_JC_21() throws Exception {
        String password = randomAlphanumeric(9);
        Users.signIn(new User("", password));
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void usernameNotExistShouldFailLogin_JC_21() throws Exception {
        User user = Users.signUp();
        Users.activate(user);
        Users.logout();
        user.setUsername(randomAlphanumeric(15));
        Users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void passwordIncorrectShouldFailLogIn_JC_22() throws Exception {
        User user = Users.signUp();
        Users.activate(user);
        Users.logout();
        user.setPassword(user.getPassword() + "a");
        Users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void usernameAndPasswordNotExistShouldFailLogin() throws Exception {
        String username = randomAlphanumeric(8);
        String password = randomAlphanumeric(9);
        Users.signIn(new User(username, password));
    }

    @Test(groups = "smoke")
    public void usernameIsCaseInsensitiveShouldLogin() throws Exception {
        User user = Users.signUp();
        Users.activate(user);
        Users.logout();
        user.setUsername(user.getUsername().toUpperCase());
        Users.signIn(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void passwordIsCaseInsensitiveShouldFailLogin() throws Exception {
        User user = Users.signUp();
        Users.activate(user);
        Users.logout();
        user.setPassword(user.getPassword().toUpperCase());
        Users.signIn(user);
    }

    @Test
    public void usernameContainsSlashShouldLogin() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomAlphanumeric(10) + "/");
        User registeredUser = Users.signUp(user);
        Users.activate(user);
        Users.logout();
        Users.signIn(registeredUser);
    }

    @Test(enabled = false)
    public void userGoFromAnyPageAndSignInViaJDialogWindow() throws Exception {
        Branch branch = Branches.userIsViewingRandomBranch();
        Users.signIn(User.admin());
        Branches.assertUserBrowsesBranch(branch);
    }

    @Test(enabled = false)
    public void userGoToPageButNeedLogIn() throws Exception {
        String referer = Users.redirectToLogIn();
        Users.fillAndSendLoginForm(User.admin());
        Users.isRedirectedToReferer(referer);
    }
}
