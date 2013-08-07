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

import static org.jtalks.tests.jcommune.utils.StringHelp.getRandomString;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/** @author Guram Savinov */
public class SignUp {
    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test
    public void registrationWithActivationSignInValidShouldLogin_JC_112() throws Exception {
        User user = Users.signUp();
        Users.signIn(user);
    }

    @Test
    public void registrationValidShouldPassRegistration_JC_1() throws Exception {
        UserForRegistration user = new UserForRegistration();
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class)
    public void dataEmptyShouldFailRegistration_JC_2() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("");
        user.setPassword("");
        user.setPasswordConfirmation("");
        user.setEmail("");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_LOGIN_ERROR)
    public void usernameAsSpaceShouldFailRegistration_JC_3() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(" ");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.TOO_LONG_LOGIN_ERROR)
    public void usernameTooLongShouldFailRegistration_JC_3() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(getRandomString((26)));
        Users.signUp(user);
    }

    @Test
    public void usernameBetween1and25CharsShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(getRandomString(24));
        Users.signUp(user);
    }

    @Test
    public void usernameWithSpacesShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(getRandomString(8) + " " + getRandomString(8));
        Users.signUp(user);
    }

    @Test
    public void usernameMaxAllowCharsShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(getRandomString(25));
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_EMAIL_FORMAT_ERROR)
    public void emailInvalidFormatShouldFailRegistration_JC_5() throws Exception {
        UserForRegistration user = UserForRegistration.withEmail(getRandomString(8) + "@" + "jtalks");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_EMAIL_ERROR)
    public void emailEmptyShouldFailRegistration_JC_5() throws ValidationException {
        UserForRegistration user = UserForRegistration.withEmail("");
        Users.signUp(user);
    }

    @Test
    public void emailValidShouldPassRegistration_JC_6() throws Exception {
        UserForRegistration user = new UserForRegistration();
        Users.signUp(user);
    }

    @Test
    public void passwordValidShouldPassRegistration_JC_8() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword(getRandomString(49));
        user.setPasswordConfirmation(user.getPassword());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_PASSWORD_ERROR)
    public void passwordEmptyShouldFailRegistration_JC_7() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword("");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.TOO_LONG_PASSWORD_ERROR)
    public void passwordTooLongShouldFailRegistration_JC_7() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword(getRandomString(51));
        user.setPasswordConfirmation(user.getPassword());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationEmptyShouldFailRegistration_JC_9() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation("");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationWrongLetterCaseInShouldFailRegistration_JC_9() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(user.getPassword().toUpperCase());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationSpaceAtTheBeginShouldFailRegistration_JC_9() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(" " + user.getPassword());
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationSpaceAtTheEndShouldFailRegistration_JC_9()  throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(user.getPassword() + " ");
        Users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.NOT_UNIQUE_USERNAME_ERROR)
    public void  usernameNotUniqueShouldFailRegistration_JC_11() throws Exception {
        UserForRegistration uniqueUser = new UserForRegistration();
        Users.signUp(uniqueUser);
        UserForRegistration duplicatedUser = UserForRegistration.withUsername(uniqueUser.getUsername());
        Users.signUp(duplicatedUser);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = SignUpPage.NOT_UNIQUE_EMAIL_ERROR)
    public void emailNotUniqueShouldFailRegistration_JC_11() throws Exception {
        UserForRegistration uniqueUser = new UserForRegistration();
        Users.signUp(uniqueUser);
        UserForRegistration duplicatedUser = UserForRegistration.withEmail(uniqueUser.getEmail());
        Users.signUp(duplicatedUser);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = SignUpPage.EMPTY_LOGIN_ERROR)
    public void loginEmptyShouldFailRegistration() throws ValidationException {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("");
        Users.signUp(user);
    }

    @Test
    public void usernameContainsSlashShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("/" + getRandomString(8));
        Users.signUp(user);
    }

    @Test
    public void usernameContainsBackslashShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("\\" + getRandomString(8));
        Users.signUp(user);
    }

}
