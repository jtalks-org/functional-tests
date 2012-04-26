package org.jtalks.tests.jcommune.pages.mail;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author masyan
 */
public class EximPage {

	public static final String loginFieldSel = "rcmloginuser";

	public static final String passwdFieldSel = "rcmloginpwd";

	public static final String signInButSel = "//input[@type='submit']";

	@FindBy(id = loginFieldSel)
	private WebElement loginField;

	@FindBy(id = passwdFieldSel)
	private WebElement passwdField;

	@FindBy(xpath = signInButSel)
	private WebElement signInBut;


	public EximPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	//Getters

	public WebElement getLoginField() {
		return loginField;
	}

	public WebElement getPasswdField() {
		return passwdField;
	}

	public WebElement getSignInBut() {
		return signInBut;
	}
}
