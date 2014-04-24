package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

public class SignInPage {
    public static final String restorePasswordLinkSel = "//div[@class='form_controls']/a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/password/restore']";
    public static final String signInDialogFormSel = "signin-modal-dialog";
    public static final String signInPageFormSel = "login-form";
    public static final String errorMessageSel = "//span[@class='help-inline']";
    public static final String emailFieldToRestoreSel = "userEmail";
    public static final String sendButtonToRestoreSel = "//button[@type='submit']";
    public static final String notValidEmailErrorMessageSel = "userEmail.errors";
    public static final String closeSignInWindowButtonSel = "//div[@class='jqiclose']";
    public static final String errorFormElementsSel = "div.control-group.error";
    public static final String LOGIN_ERROR = "Попытка выполнить вход не удалась, попробуйте еще раз\n";
    @FindBy(id = "j_username")
    WebElement usernameField;
    @FindBy(id = "j_password")
    WebElement passwordField;
    @FindBy(id = "signin-submit-button")
    WebElement submitButton;
    @FindBy(xpath = "//input[@type='submit']")
    WebElement submitButtonAfterRegistration;
    @FindBy(xpath = "//input[@name='_spring_security_remember_me']")
    WebElement rememberMeCheckBox;
    @FindBy(xpath = restorePasswordLinkSel)
    WebElement restorePasswordLink;
    @FindBy(id = signInDialogFormSel)
    WebElement signInDialogForm;
    @FindBy(id = signInPageFormSel)
    WebElement signInPageForm;
    @FindBy(xpath = errorMessageSel)
    WebElement errorMessage;
    @FindBy(id = emailFieldToRestoreSel)
    WebElement emailFieldToRestore;
    @FindBy(xpath = sendButtonToRestoreSel)
    WebElement sendButtonToRestore;
    @FindBy(id = notValidEmailErrorMessageSel)
    WebElement notValidEmailErrorMessage;
    @FindBy(xpath = closeSignInWindowButtonSel)
    WebElement closeSignInWindowButton;
    @FindBy(css = errorFormElementsSel)
    List<WebElement> errorFormElements;

    public SignInPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void fillUsernameField(String username) {
        info("Fill username: " + username);
        usernameField.sendKeys(username);
    }

    public void fillPasswordField(String password) {
        info("Fill password: " + password);
        passwordField.sendKeys(password);
    }

    public void clickSubmitButton() {
        info("Clicking submit button");
        submitButton.click();
    }

    public List<WebElement> getErrorFormElements() {
        return errorFormElements;
    }

    public WebElement getSubmitButtonAfterRegistration() {
        return submitButtonAfterRegistration;
    }
}
