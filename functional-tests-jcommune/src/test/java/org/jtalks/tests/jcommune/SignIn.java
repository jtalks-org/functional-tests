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

import org.jtalks.tests.jcommune.utils.StringHelp;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.entity.user.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.testng.annotations.*;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Guram Savinov
 */
public class SignIn {
    @BeforeMethod
    @Parameters("appUrl")
    public void setup(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn();
    }

    @Test(expectedExceptions = ValidationException.class)
    public void signUpWithoutActivationCausesError_JC_564() throws Exception {
        User user = Users.signUpWithoutActivation();
        Users.signIn(user);
    }

    @Test
    public void correctUsernameAndPassword_JC_20() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void emptyUsernameCausesError_JC_21() throws Exception {
        String password = StringHelp.getRandomString(9);
        Users.signIn(new User("", password));
    }

    @Test(expectedExceptions = ValidationException.class)
    public void incorrectUsernameCausesError_JC_21() throws Exception {
        User user = Users.signUp();
        user.setUsername(StringHelp.getRandomString(8));
        Users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void incorrectPassword_JC_22() throws Exception {
        User user = Users.signUp();
        user.setPassword(user.getPassword() + "a");
        Users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void incorrectUsernameAndPassword() throws Exception {
        String username = StringHelp.getRandomString(8);
        String password = StringHelp.getRandomString(9);
        Users.signIn(new User(username, password));
    }

    @Test
    public void checkUsernameIsCaseInsensitive() throws Exception {
        User user = Users.signUp();
        user.setUsername(user.getUsername().toUpperCase());
        Users.signIn(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void checkPasswordIsnotCaseInsensitive() throws Exception {
        User user = Users.signUp();
        user.setPassword(user.getPassword().toUpperCase());
        Users.signIn(user);
    }

    @Test
    public void shouldLoginIfUsernameContainsSlash() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername(StringHelp.getRandomString(10) + "/");
        User registeredUser = Users.signUp(user);
        Users.signIn(registeredUser);
    }
}
