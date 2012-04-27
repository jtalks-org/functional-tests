package utils.mail.exim;

import org.jtalks.tests.jcommune.pages.mail.EximPage;
import org.openqa.selenium.WebElement;
import utils.CollectionHelp;
import utils.mail.Mail;

import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.driver;

/**
 * @autor masyan
 */
public class EximMailImpl implements Mail {

	EximPage page;

	String serverUrl = "http://mail.jtalks.org/webmail";

	public EximMailImpl() {
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

	public void openFirstMessage() {
		WebElement firstMsg = CollectionHelp.getFirstWebElementFromCollection(page.getListMessages());
		//double click
		firstMsg.click();
		firstMsg.click();
	}

	public String getTextOfMessage() {
		return page.getTextOfMessage().getText();
	}

	public String getPasswdFromMessage() {
		return page.getPasswdFromRecoveryMsg().getText();
	}
}
