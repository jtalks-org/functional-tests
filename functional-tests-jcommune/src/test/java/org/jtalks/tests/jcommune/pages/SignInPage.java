package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: erik
 * Date: 03.03.12
 * Time: 13:06
 * To change this template use File | Settings | File Templates.
 */
public class SignInPage {

	public static final String usernameFieldSel = "j_username";

	public static final String passwordFieldSel = "j_password";

	public static final String submitButtonSel = "jqi_state0_buttonOK";

	public static final String rememberMeCheckBoxSel = "//input[@name='_spring_security_remember_me']";

	public static final String restorePasswordLinkSel = "//div[@class='form_controls']/a[@href='" + JCommuneSeleniumTest.contextPath + "/password/restore']";

	public static final String signInFormSel = "form";

	public static final String errorMessageSel = "//span[@class='error']";

	public static final String emailFieldToRestoreSel = "email";

	public static final String sendButtonToRestoreSel = "//button[@type='submit']";

	public static final String notValidEmailErrorMessageSel = "email.errors";

	public static final String closeSignInWindowButtonSel = "//div[@class='jqiclose']";


	@FindBy(id = usernameFieldSel)
	WebElement usernameField;

	@FindBy(id = passwordFieldSel)
	WebElement passwordField;

	@FindBy(id = submitButtonSel)
	WebElement submitButton;

	@FindBy(xpath = rememberMeCheckBoxSel)
	WebElement rememberMeCheckBox;

	@FindBy(xpath = restorePasswordLinkSel)
	WebElement restorePasswordLink;

	@FindBy(id = signInFormSel)
	WebElement signInForm;

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

	public SignInPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public WebElement getUsernameField() {
		return usernameField;
	}

	public WebElement getPasswordField() {
		return passwordField;
	}

	public WebElement getSubmitButton() {
		return submitButton;
	}

	public WebElement getRememberMeCheckBox() {
		return rememberMeCheckBox;
	}

	public WebElement getRestorePasswordLink() {
		return restorePasswordLink;
	}

	public WebElement getSignInForm() {
		return signInForm;
	}

	public WebElement getErrorMessage() {
		return errorMessage;
	}

	public WebElement getEmailFieldToRestore() {
		return emailFieldToRestore;
	}

	public WebElement getSendButtonToRestore() {
		return sendButtonToRestore;
	}

	public WebElement getNotValidEmailErrorMessage() {
		return notValidEmailErrorMessage;
	}

	public WebElement getCloseSignInWindowButton() {
		return closeSignInWindowButton;
	}
}
