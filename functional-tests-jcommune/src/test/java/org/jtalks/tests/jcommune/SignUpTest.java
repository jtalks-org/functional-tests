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

import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.entity.user.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.SignUpPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author Guram Savinov
 */
public class SignUpTest {
    @BeforeMethod(alwaysRun = true)
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test(groups = "smoke")
    public void registrationWithActivation_shouldPass() throws Exception {
        User user = Users.signUp();
        Users.activate(user);
    }

    @Test(groups = "smoke")
    public void registrationValid_shouldPass() throws Exception {
        UserForRegistration user = new UserForRegistration();
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void dataEmpty_shouldFail() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("");
        user.setPassword("");
        user.setPasswordConfirmation("");
        user.setEmail("");
        Users.signUp(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_LOGIN_ERROR)
    public void usernameAsSpace_shouldFail() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(" ");
        Users.signUp(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.TOO_LONG_LOGIN_ERROR)
    public void usernameTooLong_shouldFail() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomAlphanumeric((26)));
        Users.signUp(user);
    }

    @Test
    public void usernameBetween1and25Chars_shouldPass() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomAlphanumeric(24));
        Users.signUp(user);
    }

    @Test
    public void usernameWithSpaces_shouldPass() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomAlphanumeric(8) + " " + randomAlphanumeric(8));
        Users.signUp(user);
    }

    @Test
    public void usernameMaxAllowChars_shouldPass() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomAlphanumeric(25));
        Users.signUp(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_EMAIL_FORMAT_ERROR)
    public void emailInvalidFormat_shouldFail() throws Exception {
        UserForRegistration user = UserForRegistration.withEmail(randomAlphanumeric(8) + "@" + "jtalks");
        Users.signUp(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_EMAIL_ERROR)
    public void emailEmpty_shouldFail() throws ValidationException {
        UserForRegistration user = UserForRegistration.withEmail("");
        Users.signUp(user);
    }

    @Test
    public void passwordValid_shouldPass() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword(randomAlphanumeric(49));
        user.setPasswordConfirmation(user.getPassword());
        Users.signUp(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_PASSWORD_ERROR)
    public void passwordEmpty_shouldFail() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword("");
        Users.signUp(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.TOO_LONG_PASSWORD_ERROR)
    public void passwordTooLong_shouldFail() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword(randomAlphanumeric(51));
        user.setPasswordConfirmation(user.getPassword());
        Users.signUp(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationEmpty_shouldFail() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation("");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationWrongLetterCaseIn_shouldFail() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(user.getPassword().toUpperCase());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationSpaceAtTheBegin_shouldFail() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(" " + user.getPassword());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationSpaceAtTheEnd_shouldFail() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(user.getPassword() + " ");
        Users.signUp(user);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.NOT_UNIQUE_USERNAME_ERROR)
    public void usernameNotUnique_shouldFail() throws Exception {
        UserForRegistration uniqueUser = new UserForRegistration();
        Users.signUp(uniqueUser);
        UserForRegistration duplicatedUser = UserForRegistration.withUsername(uniqueUser.getUsername());
        Users.signUp(duplicatedUser);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = SignUpPage.NOT_UNIQUE_EMAIL_ERROR)
    public void emailNotUnique_shouldFail() throws Exception {
        UserForRegistration uniqueUser = new UserForRegistration();
        Users.signUp(uniqueUser);
        UserForRegistration duplicatedUser = UserForRegistration.withEmail(uniqueUser.getEmail());
        Users.signUp(duplicatedUser);
    }

    @Test(groups = "smoke", expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = SignUpPage.EMPTY_LOGIN_ERROR)
    public void loginEmpty_shouldFail() throws ValidationException {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("");
        Users.signUp(user);
    }

    @Test
    public void usernameContainsSlash_shouldPass() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("/" + randomAlphanumeric(8));
        Users.signUp(user);
    }

    @Test
    public void usernameContainsBackSlash_shouldPass() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("\\" + randomAlphanumeric(8));
        Users.signUp(user);
    }
}
