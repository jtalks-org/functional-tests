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

package org.jtalks.tests.jcommune.webdriver;


import org.jtalks.tests.jcommune.mail.mailtrap.MailtrapMail;
import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.CouldNotGetMessageException;
import org.jtalks.tests.jcommune.mail.mailtrap.exceptions.CouldNotGetMessagesException;
import org.jtalks.tests.jcommune.utils.StringHelp;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignInUserException;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotSignUpUserException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumTest.*;

/**
 * Contain user actions like sign in, sign out etc.
 *
 * @author Guram Savinov
 */
public class Users {

    private static final String EMAIL_ACTIVATION_INFO = "На указанный e-mail отправлено письмо со ссылкой для " +
            "подтверждения регистрации.";
    private static final Logger LOGGER = LoggerFactory.getLogger(Users.class);

    /**
     * Sign in user by dialog. Action should by started from any page of JCommune.
     *
     * @param user the {@code User} instance with sign in form data
     * @throws CouldNotOpenPageException
     * @throws CouldNotSignInUserException
     */
    public static void signIn(User user) throws CouldNotOpenPageException, CouldNotSignInUserException {
        mainPage.clickLogin();
        try {
            driver.findElement(By.id(signInPage.signInDialogFormSel));
        } catch (NoSuchElementException e) {
            throw new CouldNotOpenPageException("sign in dialog form", e);
        }

        LOGGER.info("Trying to log in {}", user);
        signInPage.getUsernameField().sendKeys(user.getUsername());
        signInPage.getPasswordField().sendKeys(user.getPassword());
        signInPage.getSubmitButton().click();
        if (!mainPage.userIsLoggedIn()) {
            throw new CouldNotSignInUserException(user, driver.getPageSource());
        }
    }

    /**
     * Sign in user by page. Action should be started from sign in page of JCommune.
     *
     * @param user the {@code User} instance with sign in form data
     * @throws CouldNotOpenPageException
     * @throws CouldNotSignInUserException
     */
    public static void signInByPage(User user) throws CouldNotOpenPageException, CouldNotSignInUserException {
        try {
            driver.findElement(By.id(signInPage.signInPageFormSel));
        } catch (NoSuchElementException e) {
            throw new CouldNotOpenPageException("sign in page form", e);
        }

        LOGGER.info("Trying to log in {}", user);
        signInPage.getUsernameField().sendKeys(user.getUsername());
        signInPage.getPasswordField().sendKeys(user.getPassword());
        signInPage.getSubmitButtonAfterRegistration().click();
        if (!mainPage.userIsLoggedIn()) {
            throw new CouldNotSignInUserException(user, driver.getPageSource());
        }
    }

    /**
     * Sign up new user with random data by dialog. Action should be started from any page of JCommune.
     *
     * @throws CouldNotOpenPageException
     * @throws CouldNotSignUpUserException
     * @return the {@code User} instance that contains registered user data
     */
    public static User signUp() throws CouldNotOpenPageException, CouldNotSignUpUserException,
            CouldNotGetMessageException, CouldNotGetMessagesException {
        User user = signUpWithoutActivation();
        activateUserByMail(user.getEmail());
        return user;
    }

    /**
     * Sign up new user by dialog. Action should be started from any page of JCommune.
     *
     * @param userForRegistration the {code UserForRegistration} instance with data for sign up form
     * @return the {@code User} instance that contains registered user data
     * @throws CouldNotOpenPageException
     * @throws CouldNotSignUpUserException
     * @throws CouldNotGetMessageException
     * @throws CouldNotGetMessagesException
     */
    public static User signUp(UserForRegistration userForRegistration) throws CouldNotOpenPageException,
            CouldNotSignUpUserException,CouldNotGetMessageException, CouldNotGetMessagesException {
        User user = signUpWithoutActivation(userForRegistration);
        activateUserByMail(user.getEmail());
        return user;
    }

    /**
     * Sign up new user without activation by dialog. Form data will be filled randomly.
     * Action should be started from any page of JCommune.
     *
     * @throws CouldNotOpenPageException
     * @throws CouldNotSignUpUserException
     * @return the {@code User} instance that contains registered user data
     */
    public static User signUpWithoutActivation() throws CouldNotOpenPageException, CouldNotSignUpUserException,
            CouldNotGetMessageException, CouldNotGetMessagesException {
        String password = StringHelp.getRandomString(9);
        return signUpWithoutActivation(new UserForRegistration(StringHelp.getRandomString(8), password, password,
                StringHelp.getRandomEmail()));
    }

    /**
     *  Sign up new user by dialog without activation. Action should be started from any page of JCommune.
     *
     * @param userForRegistration the {code UserForRegistration} instance with data for sign up form
     * @return the {@code User} instance that contains registered user data
     * @throws CouldNotOpenPageException
     * @throws CouldNotSignUpUserException
     * @throws CouldNotGetMessageException
     * @throws CouldNotGetMessagesException
     */
    public static User signUpWithoutActivation(UserForRegistration userForRegistration)
            throws CouldNotOpenPageException, CouldNotSignUpUserException, CouldNotGetMessageException,
            CouldNotGetMessagesException {
        // Check opening sign up form
        signUpPage.getSignUpButton().click();
        try {
            driver.findElement(By.id(signUpPage.signUpFormSel));
        } catch (NoSuchElementException e) {
            throw new CouldNotOpenPageException("sign up form", e);
        }

        // JCommune add captcha value to session on image request. Because HtmlUnit doesn't load images, captcha image
        // should be requested manually.
        if (selDriverType.equals("html")) {
            driver.get(signUpPage.getCaptchaImage().getAttribute("src"));
            driver.navigate().back();
            signUpPage.getSignUpButton().click();
        }

        // Fill sign up form and submit
        LOGGER.info("Registering user {}", userForRegistration);
        signUpPage.getUsernameField().sendKeys(userForRegistration.getUsername());
        signUpPage.getEmailField().sendKeys(userForRegistration.getEmail());
        signUpPage.getPasswordField().sendKeys(userForRegistration.getPassword());
        signUpPage.getPasswordConfirmField().sendKeys(userForRegistration.getPasswordConfirmation());
        signUpPage.getCaptchaField().sendKeys(validCaptchaValue);
        signUpPage.getSubmitButton().submit();
        new WebDriverWait(driver, 10).until(ExpectedConditions
                .textToBePresentInElement(By.className("modal-body"), EMAIL_ACTIVATION_INFO));
        signUpPage.getOkButtonOnInfoWindow().click();

        // Waiting for message with activation link
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        return new User(userForRegistration.getUsername(), userForRegistration.getPassword(),
                userForRegistration.getEmail());
    }

    /**
     * Open activation link from message sent by JCommune to confirm user registration
     *
     * @param email the user email
     * @throws CouldNotGetMessagesException
     * @throws CouldNotGetMessageException
     */
    public static void activateUserByMail(String email) throws CouldNotGetMessagesException,
            CouldNotGetMessageException {
        try {
            TimeUnit.SECONDS.sleep(5);//waiting since Mailtrap looks to work slower than expected
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String activationLink = MailtrapMail.getActivationLink(email);
        driver.get(activationLink);
        mainPage.getIconLinkToMainPage().click();
    }
}
