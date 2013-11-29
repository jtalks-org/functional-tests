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

package org.jtalks.tests.jcommune.webdriver.action;


import org.jtalks.tests.jcommune.mail.mailtrap.MailtrapMail;
import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.entity.user.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.MainPage;
import org.jtalks.tests.jcommune.webdriver.page.SignInPage;
import org.jtalks.tests.jcommune.webdriver.page.SignUpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;

/**
 * Contain user actions like sign in, sign out etc.
 *
 * @author Guram Savinov
 */
public class Users {
    private static final Logger LOGGER = LoggerFactory.getLogger(Users.class);

    /**
     * Sign in user by dialog. Action can by started from any page of JCommune.
     *
     * @param user the {@code User} instance with sign in form data
     * @throws ValidationException
     */
    public static void signIn(User user) throws ValidationException {
        info("Sign in a User: " + user);
        openAndFillSignInDialog(user);
        checkFormValidation(signInPage.getErrorFormElements());

        // Check that link to the user profile present on the page
        if (!mainPage.userIsLoggedIn()) {
            LOGGER.error("Could not find username in top right corner: {}", driver.getPageSource());
            throw new CouldNotOpenPageException("User does not appear to be logged on: " + user.getUsername());
        }
    }

    public static User signUpAndSignIn() {
        User user;
        try {
            user = Users.signUp();
            Users.signIn(user);
        } catch (ValidationException e) {
            throw new IllegalStateException("Can't sign up new user.", e);
        }
        return user;
    }

    private static void openAndFillSignInDialog(User user) {
        mainPage.clickLogin();
        // Check that sign-in dialog have been opened (JCommune open sign-in page instead dialog if JavaScript disabled)
        try {
            driver.findElement(By.id(SignInPage.signInDialogFormSel));
        } catch (NoSuchElementException e) {
            throw new CouldNotOpenPageException(
                    "sign-in dialog form; may be JavaScript disabled in browser settings", e);
        }

        // Fill form values and submit
        signInPage.fillUsernameField(user.getUsername());
        signInPage.fillPasswordField(user.getPassword());
        signInPage.clickSubmitButton();
    }

    private static void checkFormValidation(List<WebElement> errorElements) throws ValidationException {
        info("Check there are no validation errors");
        if (!errorElements.isEmpty()) {
            String failedFields = "";
            for (WebElement element : errorElements) {
                // Failed form field name
                failedFields += element.findElement(By.tagName("input")).getAttribute("placeholder") + ": ";
                // Add validator text if it present for this failed field
                try {
                    failedFields += element.findElement(By.className("help-inline")).getText() + "\n";
                } catch (NoSuchElementException e) {
                    failedFields += "\n";
                }
            }
            info("Validation errors found");
            throw new ValidationException(failedFields);
        }
        info("No validation errors were found");
    }

    /**
     * Sign up new user with random data. Action can be started from any page of JCommune.
     *
     * @return the {@code User} instance that contains registered user data
     * @throws ValidationException
     */
    public static User signUp() throws ValidationException {
        info("Creating and activating an account for random user");
        User user = signUpWithoutActivation();
        activateUserByMail(user.getEmail());
        return user;
    }

    /**
     * Sign up new user. Action can be started from any page of JCommune.
     *
     * @param userForRegistration the {code UserForRegistration} instance with data for sign up form
     * @return the {@code User} instance that contains registered user data
     * @throws ValidationException
     */
    public static User signUp(UserForRegistration userForRegistration) throws ValidationException {
        User user = signUpWithoutActivation(userForRegistration);
        activateUserByMail(user.getEmail());
        return user;
    }

    /**
     * Sign up new user without activation. Form fields will be filled by random valid values. Action can be started
     * from any page of JCommune.
     *
     * @return the {@code User} instance that contains registered user data
     * @throws ValidationException
     */
    public static User signUpWithoutActivation() throws ValidationException {
        info("Sign up a random user");
        return signUpWithoutActivation(new UserForRegistration());
    }

    /**
     * Sign up new user without activation. Properties with null value will be set by random valid values.
     * Action can be started from any page of JCommune.
     *
     * @param userForRegistration the {code UserForRegistration} instance with data for sign up form
     * @return the {@code User} instance that contains registered user data
     * @throws ValidationException
     */
    public static User signUpWithoutActivation(UserForRegistration userForRegistration) throws ValidationException {
        info("Sign Up a user: " + userForRegistration);
        mainPage.logOutIfLoggedIn(driver);
        openAndFillSignUpDialog(userForRegistration);
        checkFormValidation(signUpPage.getErrorFormElements());
        signUpPage.closeRegistrationWasSuccessfulDialog();
        return userForRegistration;
    }

    private static void openAndFillSignUpDialog(UserForRegistration userForRegistration) {
        signUpPage.getSignUpButton().click();
        // Check that sign-up dialog have been opened (JCommune open sign-up page instead dialog if JavaScript disabled)
        try {
            driver.findElement(By.id(SignUpPage.signUpDialogFormSel));
        } catch (NoSuchElementException e) {
            throw new CouldNotOpenPageException(
                    "sign-up dialog form; may be JavaScript disabled in browser settings", e);
        }

        // Fill form values and submit
        info("Sign Up " + userForRegistration);
        signUpPage.fillUsername(userForRegistration.getUsername());
        signUpPage.fillEmail(userForRegistration.getEmail());
        signUpPage.fillPassword(userForRegistration.getPassword());
        signUpPage.fillPasswordConfirmation(userForRegistration.getPasswordConfirmation());
        signUpPage.submitForm();
    }

    /**
     * Open activation link from message sent by JCommune to confirm user registration
     *
     * @param email the user email
     */
    public static void activateUserByMail(String email) {
        info("Looking up email at mailtrap. Activating a user by following activation link..");
        MailtrapMail mailtrapMail = new MailtrapMail();
        String activationLink = mailtrapMail.getActivationLink(email);
        driver.get(activationLink);
        info("Clicking on activation link..");
        mainPage.getIconLinkToMainPage().click();
        info("User was activated");
    }

    /**
     * Fill and send login form
     *
     * @param user
     */
    public static void fillAndSendLoginForm(User user) throws ValidationException {
        info("Sign in a User: " + user);
        checkFormValidation(signInPage.getErrorFormElements());
        //Fill form values and submit
        signInPage.fillUsernameField(user.getUsername());
        signInPage.fillPasswordField(user.getPassword());
        signInPage.getSubmitButtonAfterRegistration().click();
    }

    public static String redirectToLogIn() {
        WebElement lastAuthorButton = new MainPage(driver).getLastPostAuthor();
        String referer = lastAuthorButton.getAttribute("href");
        info("Clicking on last author link..");
        lastAuthorButton.click();
        info("User is redirected to login page");
        return referer;
    }

    public static void isRedirectedToReferer(String referer) {
        assertEquals(referer, JCommuneSeleniumConfig.driver.getCurrentUrl());
    }
}
