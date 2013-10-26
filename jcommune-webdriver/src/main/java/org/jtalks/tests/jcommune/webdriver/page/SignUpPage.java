package org.jtalks.tests.jcommune.webdriver.page;


import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @author masyan
 */
public class SignUpPage extends PageObject {
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

    public static final String EMPTY_LOGIN_ERROR = "Имя пользователя: Не может быть пустым\n";

    public static final String WRONG_PASSWORD_CONFIRMATION_ERROR =
            "Подтвердите пароль: Пароль и подтверждение пароля не совпадают\n";

    public static final String EMPTY_PASSWORD_ERROR = "Пароль: Не может быть пустым\n" +
            WRONG_PASSWORD_CONFIRMATION_ERROR;

    public static final String TOO_LONG_LOGIN_ERROR = "Имя пользователя: Длина имени пользователя должна быть между 1 и 25 символами\n";

    public static final String TOO_LONG_PASSWORD_ERROR = "Пароль: Размер должен быть между 1 и 50\n";

    public static final String EMPTY_EMAIL_ERROR = "Адрес электронной почты: Не может быть пустым\n";

    public static final String NOT_UNIQUE_USERNAME_ERROR =
            "Имя пользователя: Пользователь с таким именем пользователя уже существует\n";

    public static final String NOT_UNIQUE_EMAIL_ERROR = "Адрес электронной почты: Пользователь с таким email уже существует\n";

    public static final String WRONG_EMAIL_FORMAT_ERROR = "Адрес электронной почты: Допустимый формат email- mail@mail.ru\n";

    @FindBy(id = signUpDialogFormSel)
    WebElement signUpDialogForm;

    @FindBy(id = "signup")
    WebElement signUpButton;

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
        super(driver);
        PageFactory.initElements(driver, this);
    }

    //Getters
    public WebElement getSignUpButton() {
        return signUpButton;
    }

    public WebElement getPasswordField() {
        return passwordField;
    }

    public WebElement getPasswordErrorMessage() {
        return passwordErrorMessage;
    }

    public WebElement getPasswordConfirmField() {
        return passwordConfirmField;
    }

    public WebElement getPasswordConfirmErrorMessage() {
        return passwordConfirmErrorMessage;
    }

    public WebElement getUsernameField() {
        return usernameField;
    }

    public WebElement getUsernameErrorMessage() {
        return usernameErrorMessage;
    }

    public WebElement getEmailField() {
        return emailField;
    }

    public WebElement getEmailErrorMessage() {
        return emailErrorMessage;
    }

    public WebElement getSubmitButton() {
        return submitButton;
    }

    public WebElement getOkButtonOnInfoWindow() {
        return okButtonOnInfoWindow;
    }

    public WebElement getSignUpDialogForm() {
        return signUpDialogForm;
    }

    public List<WebElement> getErrorFormElements() {
        return errorFormElements;
    }
}
