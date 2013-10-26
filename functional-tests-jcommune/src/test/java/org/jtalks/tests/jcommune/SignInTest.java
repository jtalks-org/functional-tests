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

import net.thucydides.core.annotations.Steps;
import org.jtalks.tests.jcommune.utils.TestStringUtils;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.entity.user.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.SignInPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Guram Savinov
 */
public class SignInTest {
    @Steps
    private Users users;

    @BeforeMethod
    @Parameters("appUrl")
    public void setup(String appUrl) {
        driver.get(appUrl);
        users.logOutIfLoggedIn();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void signInWithoutActivationRegistrationShouldFailLogin_JC_564() throws Exception {
        User user = users.signUpWithoutActivation();
        users.signIn(user);
    }

    @Test
    public void usernameAndPasswordCorrectShouldLogin_JC_20() throws Exception {
        User user = users.signUp();
        users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void usernameEmptyShouldFailLogin_JC_21() throws Exception {
        String password = TestStringUtils.randomString(9);
        users.signIn(new User("", password));
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void usernameNotExistShouldFailLogin_JC_21() throws Exception {
        User user = users.signUp();
        user.setUsername(TestStringUtils.randomString(8));
        users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void passwordIncorrectShouldFailLogIn_JC_22() throws Exception {
        User user = users.signUp();
        user.setPassword(user.getPassword() + "a");
        users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void usernameAndPasswordNotExistShouldFailLogin() throws Exception {
        String username = TestStringUtils.randomString(8);
        String password = TestStringUtils.randomString(9);
        users.signIn(new User(username, password));
    }

    @Test
    public void usernameIsCaseInsensitiveShouldLogin() throws Exception {
        User user = users.signUp();
        user.setUsername(user.getUsername().toUpperCase());
        users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignInPage.LOGIN_ERROR)
    public void passwordIsCaseInsensitiveShouldFailLogin() throws Exception {
        User user = users.signUp();
        user.setPassword(user.getPassword().toUpperCase());
        users.signIn(user);
    }

    @Test
    public void usernameContainsSlashShouldLogin() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername(TestStringUtils.randomString(10) + "/");
        User registeredUser = users.signUp(user);
        users.signIn(registeredUser);
    }
}
