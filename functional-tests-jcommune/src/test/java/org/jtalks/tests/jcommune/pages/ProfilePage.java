package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @autor masyan
 */
public class ProfilePage {

	public static final String currentUserLinkSel = "//a[@class='currentusername']";

	public static final String userDetailsFormSel = "userdetails";

	public static final String profileLinkFromPMInpoxPageSel = "//a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/users')]";

	public static final String profileLinkFromTopicPageSel = "//a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/users') and @class='username']";


	@FindBy(xpath = currentUserLinkSel)
	private WebElement currentUserLink;

	@FindBy(id = userDetailsFormSel)
	private WebElement userDetailsForm;

	@FindBy(xpath = profileLinkFromPMInpoxPageSel)
	private WebElement profileLinkFromPMInpoxPage;

	@FindBy(xpath = profileLinkFromTopicPageSel)
	private WebElement profileLinkFromTopicPage;

	public ProfilePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	//Getters
	public WebElement getCurrentUserLink() {
		return currentUserLink;
	}

	public WebElement getUserDetailsForm() {
		return userDetailsForm;
	}

	public WebElement getProfileLinkFromPMInpoxPage() {
		return profileLinkFromPMInpoxPage;
	}

	public WebElement getProfileLinkFromTopicPage() {
		return profileLinkFromTopicPage;
	}
}
