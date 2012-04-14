package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author masyan
 */
public class SignUpPage {

	public static final String signUpButtonSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/user/new']";

	public static final String passwordFieldSel = "password";

	public static final String passwordErrorMessageSel = "password.errors";


	public static final String passwordConfirmFieldSel = "passwordConfirm";

	public static final String passwordConfirmErrorMessageSel = "passwordConfirm.errors";


	public static final String usernameFieldSel = "username";

	public static final String usernameErrorMessageSel = "username.errors";


	public static final String emailFieldSel = "email";

	public static final String emailErrorMessageSel = "email.errors";


	public static final String submitButtonSel = "jqi_state0_buttonOK";


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

	@FindBy(id = usernameErrorMessageSel)
	WebElement usernameErrorMessage;

	@FindBy(id = emailFieldSel)
	WebElement emailField;

	@FindBy(id = emailErrorMessageSel)
	WebElement emailErrorMessage;

	@FindBy(id = submitButtonSel)
	WebElement submitButton;


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
}
