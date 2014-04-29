package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.exceptions.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.signUpPage;

/**
 * @author masyan
 */
public class SignUpPage {
    /**
     * JCommune instance for functional should be configured to use only 0000 as captcha so that we can actually
     * register users without writing a tool to break our own captcha
     */

    public static final String signUpDialogFormSel = "signup-modal-dialog";
    public static final String passwordFieldSel = "password";
    public static final String passwordErrorMessageSel = "//input[@id='password']/following-sibling::span[@class='help-block _error']";
    public static final String passwordConfirmFieldSel = "passwordConfirm";
    public static final String passwordConfirmErrorMessageSel = "//input[@id='passwordConfirm']/following-sibling::span[@class='help-block _error']";
    public static final String usernameFieldSel = "username";
    public static final String usernameErrorMessageSel = "//input[@id='username']/following-sibling::span[@class='help-block _error']";
    public static final String emailFieldSel = "email";
    public static final String emailErrorMessageSel = "//input[@id='email']/following-sibling::span[@class='help-block _error']";
    public static final String submitButtonSel = "signup-submit-button";
    public static final String okButtonOnInfoWindowSel = "btn-primary";
    public static final String errorFormElementsSel = "div.control-group.error";
    public static final String EMPTY_LOGIN_ERROR = "Длина имени пользователя должна быть между 1 и 25 символами\n";
    public static final String WRONG_PASSWORD_CONFIRMATION_ERROR = "Пароль и подтверждение пароля не совпадают\n";
    public static final String EMPTY_PASSWORD_ERROR = "Длина пароля должна быть между 1 и 50 символами\n" +
            WRONG_PASSWORD_CONFIRMATION_ERROR;
    public static final String TOO_LONG_LOGIN_ERROR = "Длина имени пользователя должна быть между 1 и 25 символами\n";
    public static final String TOO_LONG_PASSWORD_ERROR = "Длина пароля должна быть между 1 и 50 символами\n";
    public static final String EMPTY_EMAIL_ERROR = "Не может быть пустым\n";
    public static final String NOT_UNIQUE_USERNAME_ERROR = "Пользователь с таким именем уже существует\n";
    public static final String NOT_UNIQUE_EMAIL_ERROR = "Пользователь с таким email уже существует\n";
    public static final String WRONG_EMAIL_FORMAT_ERROR = "Допустимый формат email: mail@mail.ru\n";
    @FindBy(id = signUpDialogFormSel)
    WebElement signUpDialogForm;
    @FindBy(id = "signup")
    WebElement signUpButton;
    @FindBy(id = "registration-successful-dialog")
    WebElement signUpWasSuccessfulDialog;
    @FindBy(id = passwordFieldSel)
    WebElement passwordField;
    @FindBy(id = passwordErrorMessageSel)
    WebElement passwordErrorMessage;
    @FindBy(id = passwordConfirmFieldSel)
    WebElement passwordConfirmField;
    @FindBy(id = passwordConfirmErrorMessageSel)
    WebElement passwordConfirmErrorMessage;
    @FindBy(id = usernameFieldSel)
    WebElement usernameField;
    @FindBy(xpath = usernameErrorMessageSel)
    WebElement usernameErrorMessage;
    @FindBy(id = emailFieldSel)
    WebElement emailField;
    @FindBy(id = emailErrorMessageSel)
    WebElement emailErrorMessage;
    @FindBy(id = submitButtonSel)
    WebElement submitButton;
    @FindBy(className = okButtonOnInfoWindowSel)
    WebElement okButtonOnInfoWindow;
    @FindBy(css = errorFormElementsSel)
    List<WebElement> errorFormElements;


    public SignUpPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Getters
    public WebElement getSignUpButton() {
        return signUpButton;
    }

    public void fillUsername(String username) {
        info("Fill username: " + username);
        usernameField.sendKeys(username);
    }

    public void fillEmail(String email) {
        info("Fill email: " + email);
        emailField.sendKeys(email);
    }

    public void fillPassword(String password) {
        info("Fill password: " + password);
        passwordField.sendKeys(password);
    }

    public void fillPasswordConfirmation(String passwordConfirmation) {
        info("Fill password confirmation: " + passwordConfirmation);
        passwordConfirmField.sendKeys(passwordConfirmation);
    }

    public void submitForm() {
        info("Clicking submit button...");
        submitButton.click();
    }

    public void closeRegistrationWasSuccessfulDialog() {
        info("Waiting for dialog that says check your mailbox to open...");
        try {
            signUpWasSuccessfulDialog.isDisplayed();
            info("The dialog showed up!");
        } catch (org.openqa.selenium.TimeoutException e) {
            info("The dialog asked to check the mailbox");
            throw new TimeoutException("Waiting for email activation confirmation dialog.", e);
        }
        signUpPage.getOkButtonOnInfoWindow().click();
    }

    public WebElement getOkButtonOnInfoWindow() {
        return okButtonOnInfoWindow;
    }


    public List<WebElement> getErrorFormElements() {
        return errorFormElements;
    }
}
