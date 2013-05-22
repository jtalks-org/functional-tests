package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author masyan
 */
public class SignUpPage {
    /**
     * JCommune instance for functional should be configured to use only 0000 as captcha so that we can actually
     * register users without writing a tool to break our own captcha
     */
    public static final String VALID_CAPTCHA_VALUE = "0000";
    public static final String signUpFormSel = "signup-modal-dialog";

    public static final String signUpButtonSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user/new']";

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

    public static final String captchaFieldSel = "captcha";

    public static final String captchaImageSel = "captcha-img";


    @FindBy(id = signUpFormSel)
    WebElement signUpForm;

    @FindBy(xpath = signUpButtonSel)
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

    @FindBy(id = captchaFieldSel)
    WebElement captchaField;

    @FindBy(id = captchaImageSel)
    WebElement captchaImage;


    public SignUpPage(WebDriver driver) {
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

    public WebElement getCaptchaField() {
        return captchaField;
    }

    public WebElement getSignUpForm() {
        return signUpForm;
    }

    public WebElement getCaptchaImage() {
        return captchaImage;
    }
}
