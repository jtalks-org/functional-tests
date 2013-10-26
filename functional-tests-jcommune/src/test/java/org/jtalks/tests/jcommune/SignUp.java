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
import net.thucydides.core.annotations.Story;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.entity.user.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.SignUpPage;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.utils.TestStringUtils.randomString;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;

/**
 * @author Guram Savinov
 */
@Story(SignUp.class)
public class SignUp {
    @Steps
    public Users users;

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
        users.logOutIfLoggedIn();
    }

    @Test
    public void registrationWithActivationSignInValidShouldLogin_JC_112() throws Exception {
        User user = users.signUp();
        users.signIn(user);
    }

    @Test(description = "Registration and activation with valid inputs should pass")
    public void registrationValidShouldPassRegistration_JC_1() throws Exception {
        UserForRegistration user = new UserForRegistration();
        users.signUp(user);
    }

    @Test(description = "Registering a user with empty data should fail",
            expectedExceptions = ValidationException.class)
    public void dataEmptyShouldFailRegistration_JC_2() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("");
        user.setPassword("");
        user.setPasswordConfirmation("");
        user.setEmail("");
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_LOGIN_ERROR)
    public void usernameAsSpaceShouldFailRegistration_JC_3() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(" ");
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.TOO_LONG_LOGIN_ERROR)
    public void usernameTooLongShouldFailRegistration_JC_3() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomString((26)));
        users.signUp(user);
    }

    @Test
    public void usernameBetween1and25CharsShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomString(24));
        users.signUp(user);
    }

    @Test
    public void usernameWithSpacesShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomString(8) + " " + randomString(8));
        users.signUp(user);
    }

    @Test
    public void usernameMaxAllowCharsShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = UserForRegistration.withUsername(randomString(25));
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_EMAIL_FORMAT_ERROR)
    public void emailInvalidFormatShouldFailRegistration_JC_5() throws Exception {
        UserForRegistration user = UserForRegistration.withEmail(randomString(8) + "@" + "jtalks");
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_EMAIL_ERROR)
    public void emailEmptyShouldFailRegistration_JC_5() throws ValidationException {
        UserForRegistration user = UserForRegistration.withEmail("");
        users.signUp(user);
    }

    @Test
    public void emailValidShouldPassRegistration_JC_6() throws Exception {
        UserForRegistration user = new UserForRegistration();
        users.signUp(user);
    }

    @Test
    public void passwordValidShouldPassRegistration_JC_8() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword(randomString(49));
        user.setPasswordConfirmation(user.getPassword());
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.EMPTY_PASSWORD_ERROR)
    public void passwordEmptyShouldFailRegistration_JC_7() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword("");
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.TOO_LONG_PASSWORD_ERROR)
    public void passwordTooLongShouldFailRegistration_JC_7() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPassword(randomString(51));
        user.setPasswordConfirmation(user.getPassword());
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationEmptyShouldFailRegistration_JC_9() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation("");
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationWrongLetterCaseInShouldFailRegistration_JC_9() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(user.getPassword().toUpperCase());
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationSpaceAtTheBeginShouldFailRegistration_JC_9() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(" " + user.getPassword());
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.WRONG_PASSWORD_CONFIRMATION_ERROR)
    public void passwordConfirmationSpaceAtTheEndShouldFailRegistration_JC_9() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setPasswordConfirmation(user.getPassword() + " ");
        users.signUp(user);
    }

    @Test(expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = SignUpPage.NOT_UNIQUE_USERNAME_ERROR)
    public void usernameNotUniqueShouldFailRegistration_JC_11() throws Exception {
        UserForRegistration uniqueUser = new UserForRegistration();
        users.signUp(uniqueUser);
        UserForRegistration duplicatedUser = UserForRegistration.withUsername(uniqueUser.getUsername());
        users.signUp(duplicatedUser);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = SignUpPage.NOT_UNIQUE_EMAIL_ERROR)
    public void emailNotUniqueShouldFailRegistration_JC_11() throws Exception {
        UserForRegistration uniqueUser = new UserForRegistration();
        users.signUp(uniqueUser);
        UserForRegistration duplicatedUser = UserForRegistration.withEmail(uniqueUser.getEmail());
        users.signUp(duplicatedUser);
    }

    @Test(expectedExceptions = ValidationException.class, expectedExceptionsMessageRegExp = SignUpPage.EMPTY_LOGIN_ERROR)
    public void loginEmptyShouldFailRegistration() throws ValidationException {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("");
        users.signUp(user);
    }

    @Test
    public void usernameContainsSlashShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("/" + randomString(8));
        users.signUp(user);
    }

    @Test
    public void usernameContainsBackSlashShouldPassRegistration_JC_4() throws Exception {
        UserForRegistration user = new UserForRegistration();
        user.setUsername("\\" + randomString(8));
        users.signUp(user);
    }
}
