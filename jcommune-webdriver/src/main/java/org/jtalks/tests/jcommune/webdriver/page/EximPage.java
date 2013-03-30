package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @author masyan
 */
public class EximPage {

	public static final String loginFieldSel = "rcmloginuser";

	public static final String passwdFieldSel = "rcmloginpwd";

	public static final String signInButSel = "//input[@type='submit']";

	public static final String listMessagesSel = "//td[@class='subject']/a";

	public static final String textOfMessageSel = "//div[@class='rcmBody']";

	public static final String passwdFromRecoveryMsgSel = "password";

	public static final String firstUnreadMessageSel = "//tr[contains(@class,'unread')]//td[@class='subject']/a";

	public static final String refreshMailsButtonSel = "rcmbtn116";

	public static final String linkFromTextOfMessageSel = "//div[@class='rcmBody']//a";

	@FindBy(id = loginFieldSel)
	private WebElement loginField;

	@FindBy(id = passwdFieldSel)
	private WebElement passwdField;

	@FindBy(xpath = listMessagesSel)
	private List<WebElement> listMessages;

	@FindBy(xpath = signInButSel)
	private WebElement signInBut;

	@FindBy(xpath = textOfMessageSel)
	private WebElement textOfMessage;

	@FindBy(id = passwdFromRecoveryMsgSel)
	private WebElement passwdFromRecoveryMsg;

	@FindBy(xpath = firstUnreadMessageSel)
	private WebElement firstUnreadMessage;

	@FindBy(id = refreshMailsButtonSel)
	private WebElement refreshMailsButton;

	@FindBy(xpath = linkFromTextOfMessageSel)
	private WebElement linkFromTextOfMessage;


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

	public List<WebElement> getListMessages() {
		return listMessages;
	}

	public WebElement getTextOfMessage() {
		return textOfMessage;
	}

	public WebElement getPasswdFromRecoveryMsg() {
		return passwdFromRecoveryMsg;
	}

	public WebElement getFirstUnreadMessage() {
		return firstUnreadMessage;
	}

	public WebElement getRefreshMailsButton() {
		return refreshMailsButton;
	}

	public WebElement getLinkFromTextOfMessage() {
		return linkFromTextOfMessage;
	}
}
