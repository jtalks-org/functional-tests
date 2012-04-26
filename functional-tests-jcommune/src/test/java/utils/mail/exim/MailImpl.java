package utils.mail.exim;

import org.jtalks.tests.jcommune.pages.mail.EximPage;
import utils.mail.Mail;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 */
public class MailImpl implements Mail {

	EximPage page;

	String serverUrl = "http://mail.jtalks.org/webmail";

	public MailImpl() {
		page = new EximPage(driver);
	}

	public void signIn(String username, String password) {
		page.getLoginField().sendKeys(username);
		page.getPasswdField().sendKeys(password);
		page.getSignInBut().click();
	}

	public String getMailServerUrl() {
		return this.serverUrl;
	}

	public void goToMailServer() {
		driver.get(this.serverUrl);
	}
}
