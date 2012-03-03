package org.jtalks.tests.jcommune.pages;

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

	@FindBy(id = "j_username")
	WebElement usernameField;

	@FindBy(id = "j_password")
	WebElement passwordField;

	@FindBy(xpath = "//input[@class='button']")
	WebElement submitButton;

	@FindBy(xpath = "//input[@name='_spring_security_remember_me']")
	WebElement rememberMeCheckBox;

	@FindBy(xpath = "//div[@class='form_controls']/a[@href='/test-jcommune/password/restore']")
	WebElement restorePasswordLink;


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
}
