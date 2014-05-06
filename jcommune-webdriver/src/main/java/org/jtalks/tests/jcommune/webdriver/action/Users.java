/**
 * Copyright (C) 2011 JTalks.org Team
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

package org.jtalks.tests.jcommune.webdriver.action;


import org.jtalks.tests.jcommune.mail.pochta.PochtaMail;
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
import static junit.framework.Assert.assertTrue;
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

    public static void logout() {
        mainPage.clickLogout();
    }

    public static User signUpAndSignIn() {
        User user;
        try {
            user = Users.signUp();
            Users.activate(user);
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
                // failedFields += element.findElement(By.tagName("input")).getAttribute("placeholder") + ": ";
                // Add validator text if it present for this failed field
                try {
                    failedFields += element.findElement(By.className("help-inline")).getText() + "\n";
                } catch (NoSuchElementException e) {
                    // failedFields += "\n";
                }
            }
            info("Validation errors found");
            throw new ValidationException(failedFields);
        }
        info("No validation errors were found");
    }

    /**
     * Sign up new user without activation. Form fields will be filled by random valid values. Action can be started
     * from any page of JCommune.
     *
     * @return the {@code User} instance that contains registered user data
     * @throws ValidationException
     */
    public static User signUp() throws ValidationException {
        info("Sign up a random user");
        return signUp(new UserForRegistration());
    }

    /**
     * Sign up new user without activation. Properties with null value will be set by random valid values.
     * Action can be started from any page of JCommune.
     *
     * @param userForRegistration the {code UserForRegistration} instance with data for sign up form
     * @return the {@code User} instance that contains registered user data
     * @throws ValidationException
     */
    public static User signUp(UserForRegistration userForRegistration) throws ValidationException {
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
     * Open activation link from message sent by JCommune to confirm user registration. As a side effect - this signs
     * in the user.
     *
     * @param user - a user to activate its just registered account
     */
    public static void activate(User user) {
        info("Looking up email at mailtrap. Activating a user by following activation link..");
        PochtaMail mailtrapMail = new PochtaMail();
        String activationLink = mailtrapMail.getActivationLink(user.getEmail());
        info("Clicking on activation link..");
        driver.get(activationLink);
        if (mainPage.userIsLoggedIn()) {
            info("User was activated");
        } else {
            info("User was not activated");
            throw new IllegalStateException("User " + user.getUsername() + " was not activated some why!");
        }
    }

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

    public static void viewProfile(User user) {
        profilePage.clickOnDropDownMenuForUserOnMainPage();
        profilePage.clickOnProfileInDropDownMenu();
        profilePage.openContactsTab();
        profilePage.openNotificationsTab();
        profilePage.openSecurityTab();
    }

    public static void editMainUserInfo(User user) throws ValidationException{
        profilePage.clickOnDropDownMenuForUserOnMainPage();
        profilePage.clickOnProfileInDropDownMenu();
        profilePage.getFirstNameField();
        profilePage.fillFirstName(user.getFirstName());
        profilePage.getLastNameField();
        profilePage.fillLastName(user.getLastName());
        profilePage.getSignatureField();
        profilePage.fillSignature(user.getSignature());
        profilePage.getEmailEditField();
        profilePage.fillEmail(user.getEmail());
        profilePage.selectPageSizeByValue(user.getPageSize());
        profilePage.getLocationField();
        profilePage.fillLocation(user.getLocation());
        profilePage.clickOnSaveButtonInProfile();
        checkFormValidation(profilePage.getErrorFormElements());
    }

    public static void editNotifications(User user) {
        profilePage.clickOnDropDownMenuForUserOnMainPage();
        profilePage.clickOnProfileInDropDownMenu();
        profilePage.openNotificationsTab();
        profilePage.selectAutoSubscribe(user.getAutoSubscribe());
        profilePage.selectNotifyIfSomeoneMentionsYou(user.getNotifyIfSomeoneMentionsYou());
        profilePage.selectNotifyIfPrivateMessageIsReceived(user.getNotifyIfPrivateMessageIsReceived());
    }

    public static void editPasswordInfo(User user) throws ValidationException{
        profilePage.clickOnDropDownMenuForUserOnMainPage();
        profilePage.clickOnProfileInDropDownMenu();
        profilePage.openSecurityTab();
        profilePage.fillCurrentPassword(user.getCurrentPassword());
        profilePage.fillNewPassword(user.getNewPassword());
        profilePage.fillConfirmNewPassword(user.getConfirmPassword());
        profilePage.clickOnSaveButtonInProfile();
        checkFormValidation(profilePage.getErrorFormElements());
    }

    public static void assertMainUserInfo(User user) {
        info("Begins assert the test profile fields");
        assertTrue("FirstName wasn't changed" ,user.getFirstName().equals(profilePage.getFirstNameField().getAttribute("value")));
        info("FirstName was changed");
        assertTrue("LastName wasn't changed" ,user.getLastName().equals(profilePage.getLastNameField().getAttribute("value")));
        info("LastName was changed successful");
        assertTrue("Signature wasn't changed successful", user.getSignature().equals(profilePage.getSignatureField().getAttribute("value")));
        info("Signature was changed successful");
        assertTrue("Email wasn't changed successful" , user.getEmail().equals(profilePage.getEmailEditField().getAttribute("value")));
        info("Email was changed successful");
        assertEquals("PageSize wasn't changed successful", user.getPageSize(), Integer.parseInt(profilePage.getSelectedValueInList().getText()));
        info("PageSize was changed successful");
        assertTrue("Location wasn't changed successful", user.getLocation().equals(profilePage.getLocationField().getAttribute("value")));
        info("Location was changed successful");
    }

    public static void assertNotification(User user) {
        assertEquals(user.getAutoSubscribe(), profilePage.getAutoSubscribeCheckbox().isSelected());
        assertEquals(user.getNotifyIfSomeoneMentionsYou(), profilePage.getNotifyIfSomeoneMentionsYouCheckbox().isSelected());
        assertEquals(user.getNotifyIfPrivateMessageIsReceived(), profilePage.getNotifyIfPrivateMessageIsReceivedCheckbox().isSelected());
    }

    public static void assertSecurity(User user) {
        assertEquals(user.getCurrentPassword(), user.getNewPassword());
    }
}