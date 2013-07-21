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
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.entity.user.UserForRegistration;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.TimeoutException;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.SignInPage;
import org.jtalks.tests.jcommune.webdriver.page.SignUpPage;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.webdriverType;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;

/**
 * Contain user actions like sign in, sign out etc.
 *
 * @author Guram Savinov
 */
public class Users {

    private static final String EMAIL_ACTIVATION_INFO = "На указанный e-mail отправлено письмо со ссылкой для " +
            "подтверждения регистрации.";
    private static final Logger LOGGER = LoggerFactory.getLogger(Users.class);
    private static final int WAIT_FOR_DIALOG_TO_OPEN_SECONDS = 60;

    /**
     * Sign in user by dialog. Action can by started from any page of JCommune.
     *
     * @param user the {@code User} instance with sign in form data
     * @throws ValidationException
     */
    public static void signIn(User user) throws ValidationException {
        openAndFillSignInDialog(user);
        checkFormValidation(signInPage.getErrorFormElements());

        // Check that link to the user profile present on the page
        if (!mainPage.userIsLoggedIn()) {
            throw new CouldNotOpenPageException("profile page for the user: " + user.getEmail());
        }
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
        LOGGER.info("Sign in {}", user);
        signInPage.getUsernameField().sendKeys(user.getUsername());
        signInPage.getPasswordField().sendKeys(user.getPassword());
        signInPage.getSubmitButton().click();
    }

    private static void checkFormValidation(List<WebElement> errorElements) throws ValidationException {
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
            throw new ValidationException(failedFields);
        }
    }

    /**
     * Sign up new user with random data. Action can be started from any page of JCommune.
     *
     * @return the {@code User} instance that contains registered user data
     * @throws ValidationException
     */
    public static User signUp() throws ValidationException {
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
     *
     */
    public static User signUpWithoutActivation() throws ValidationException {
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
        openAndFillSignUpDialog(userForRegistration);
        checkFormValidation(signUpPage.getErrorFormElements());
        waitForEmailActivationInfoShowsUp();
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

        // JCommune add captcha value to the session on image request. Because HtmlUnit doesn't load images, captcha
        // image should be requested manually.
        if ("htmlunit".equalsIgnoreCase(webdriverType)) {
            driver.get(signUpPage.getCaptchaImage().getAttribute("src"));
            driver.navigate().back();
            signUpPage.getSignUpButton().click();
        }

        // Fill form values and submit
        LOGGER.info("Sign Up {}", userForRegistration);
        signUpPage.getUsernameField().sendKeys(userForRegistration.getUsername());
        signUpPage.getEmailField().sendKeys(userForRegistration.getEmail());
        signUpPage.getPasswordField().sendKeys(userForRegistration.getPassword());
        signUpPage.getPasswordConfirmField().sendKeys(userForRegistration.getPasswordConfirmation());
        signUpPage.getCaptchaField().sendKeys(SignUpPage.VALID_CAPTCHA_VALUE);
        signUpPage.getSubmitButton().click();
    }

    private static void waitForEmailActivationInfoShowsUp() {
        try {
            new WebDriverWait(driver, WAIT_FOR_DIALOG_TO_OPEN_SECONDS).until(
                    ExpectedConditions.textToBePresentInElement(By.className("modal-body"), EMAIL_ACTIVATION_INFO));
        } catch (org.openqa.selenium.TimeoutException e) {
            throw new TimeoutException("Waiting for email activation confirmation dialog.", e);
        }
        signUpPage.getOkButtonOnInfoWindow().click();
    }

    /**
     * Open activation link from message sent by JCommune to confirm user registration
     *
     * @param email the user email
     */
    public static void activateUserByMail(String email) {
        MailtrapMail mailtrapMail = new MailtrapMail();
        driver.get(mailtrapMail.getActivationLink(email));
        mainPage.getIconLinkToMainPage().click();
    }
}
