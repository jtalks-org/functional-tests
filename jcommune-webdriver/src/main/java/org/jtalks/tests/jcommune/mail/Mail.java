package org.jtalks.tests.jcommune.mail;

import org.openqa.selenium.WebElement;

/**
 * Iterface to mail server
 *
 * @autor masyan
 */
public interface Mail {

	/**
	 * Method for authorize in mail server
	 *
	 * @param username username of mail server
	 * @param password password of mail server
	 */
	public void signIn(String username, String password);

	public String getMailServerUrl();

	public void goToMailServer();

	public void openFirstMessage();

	public String getTextOfMessage();

	//return new password from message/ Used for recovery password
	public String getPasswdFromMessage();

	//for activation
	public WebElement getFirstLinkInMessageText();
}
