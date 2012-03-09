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
 * Time: 13:44
 * To change this template use File | Settings | File Templates.
 */
public class MainPage {

	private static final String loginLinkSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/login']";

	public static final String currentUsernameLinkSel = "//a[@class='currentusername']";

	public static final String errorPageSel = "//span[@class='error_errorpage']";

	@FindBy(xpath = loginLinkSel)
	private WebElement loginLink;

	@FindBy(xpath = currentUsernameLinkSel)
	private WebElement currentUsernameLink;

	@FindBy(xpath = errorPageSel)
	private WebElement errorPage;

	public MainPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	//Getters
	public WebElement getLoginLink() {
		return loginLink;
	}

	public WebElement getCurrentUsernameLink() {
		return currentUsernameLink;
	}

	public WebElement getErrorPage() {
		return errorPage;
	}
}