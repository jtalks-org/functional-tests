package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * @author masyan
 */
public class ProfilePage {

	public static final String currentUserLinkSel = "//a[@class='currentusername']";

	public static final String userDetailsFormSel = "userdetails";

	public static final String profileLinkFromPMInpoxPageSel = "//a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/users')]";

	public static final String profileLinkFromTopicPageSel = "//a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/users') and @class='username']";

	public static final String profileLinkFromMainPageSel = "//a[@href='" + JCommuneSeleniumTest.contextPath + "/user']";

	public static final String editProfileButtonSel = "//a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/users/edit') and @class='button']";

	public static final String emailEditFieldIdSel = "email";

	public static final String saveEditButtonIdSel = "saveChanges";

	public static final String errorEmailMessageIdSel = "email.errors";

	public static final String emailSel = "//ul[@id='stylized']/li[5]/span";

	public static final String currentPasswordFieldIdSel = "currentUserPassword";

	public static final String newPasswordFieldIdSel = "newUserPassword";

	public static final String confirmNewPasswordFieldIdSel = "newUserPasswordConfirm";

	public static final String errorCurrentUserPasswordMessageIdSel = "currentUserPassword.errors";

	public static final String errorNewUserPasswordMessageIdSel = "newUserPassword.errors";

	public static final String errorNewUserPasswordConfirmMessageIdSel = "newUserPasswordConfirm.errors";

	public static final String backButtonSel = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/user') and @class='button']";

	public static final String profileLinkFromLastColumnSel = "//a[@class = 'last_message_user']";

	public static final String signatureFieldSel = "signature";

	public static final String signatureTextSel = "//span[@class='signature']";

	public static final String postListButtonSel = "//a[contains(@href, 'postList') and @class='button']";

	public static final String emptyMessageInPosListSel = "//span[@class='empty' and contains(text(), *)]";

	public static final String newPrivateMessageButtonSel = "//a[contains(@href, '" + JCommuneSeleniumTest.contextPath + "/pm/new')]";

	public static final String usernameTableFieldSel = "//li[@class='forum_row']//label[text()='Username']";

	public static final String firstNameTableFieldSel = "//li[@class='forum_row']//label[text()='First name']";

	public static final String lastNameTableFieldSel = "//li[@class='forum_row']//label[text()='Last name']";

	public static final String emailTableFieldSel = "//li[@class='forum_row']//label[text()='Email']";

	public static final String languageTableFieldSel = "//li[@class='forum_row']//label[text()='Language']";

	public static final String pageSizeTableFieldSel = "//li[@class='forum_row']//label[text()='Page size']";

	public static final String signatureTableFieldSel = "//li[@class='forum_row']//label[text()='Your signature']";

	public static final String locationTableFieldSel = "//li[@class='forum_row']//label[text()='Location']";

	public static final String lastLoginTableFieldSel = "//li[@class='forum_row']//label[text()='Last login']";

	public static final String registrationDateTableFieldSel = "//li[@class='forum_row']//label[text()='Registration date']";

	public static final String avatarDateTableFieldSel = "//li[@class='forum_row']//label[text()='Avatar']";

	public static final String postCountDateTableFieldSel = "//li[@class='forum_row']//label[text()='Post count']";


	@FindBy(xpath = emptyMessageInPosListSel)
	private WebElement emptyMessageInPosList;

	@FindBy(xpath = postListButtonSel)
	private WebElement postListButton;

	@FindBy(xpath = backButtonSel)
	private WebElement backButton;

	@FindBy(id = errorNewUserPasswordConfirmMessageIdSel)
	private WebElement errorNewUserPasswordConfirmMessage;

	@FindBy(id = errorNewUserPasswordMessageIdSel)
	private WebElement errorNewUserPasswordMessage;

	@FindBy(id = errorCurrentUserPasswordMessageIdSel)
	private WebElement errorCurrentUserPasswordMessage;

	@FindBy(id = currentPasswordFieldIdSel)
	private WebElement currentPasswordField;

	@FindBy(id = newPasswordFieldIdSel)
	private WebElement newPasswordField;

	@FindBy(id = confirmNewPasswordFieldIdSel)
	private WebElement confirmNewPasswordField;

	@FindBy(xpath = emailSel)
	private WebElement email;

	@FindBy(xpath = currentUserLinkSel)
	private WebElement currentUserLink;

	@FindBy(id = userDetailsFormSel)
	private WebElement userDetailsForm;

	@FindBy(xpath = profileLinkFromPMInpoxPageSel)
	private WebElement profileLinkFromPMInpoxPage;

	@FindBy(xpath = profileLinkFromTopicPageSel)
	private WebElement profileLinkFromTopicPage;

	@FindBy(xpath = profileLinkFromMainPageSel)
	private WebElement profileLinkFromMainPage;

	@FindBy(xpath = editProfileButtonSel)
	private WebElement editProfileButton;

	@FindBy(id = emailEditFieldIdSel)
	private WebElement emailEditField;

	@FindBy(id = saveEditButtonIdSel)
	private WebElement saveEditButton;

	@FindBy(xpath = profileLinkFromLastColumnSel)
	private WebElement profileLinkFromLastColumn;

	@FindBy(id = signatureFieldSel)
	private WebElement signatureField;

	@FindBy(xpath = signatureTextSel)
	private WebElement signatureText;

	@FindBy(xpath = newPrivateMessageButtonSel)
	private WebElement newPrivateMessageButton;

	@FindBy(xpath = usernameTableFieldSel)
	private WebElement usernameTableField;

	@FindBy(xpath = firstNameTableFieldSel)
	private WebElement firstNameTableField;

	@FindBy(xpath = lastNameTableFieldSel)
	private WebElement lastNameTableField;

	@FindBy(xpath = emailTableFieldSel)
	private WebElement emailTableField;

	@FindBy(xpath = languageTableFieldSel)
	private WebElement languageTableField;

	@FindBy(xpath = pageSizeTableFieldSel)
	private WebElement pageSizeTableField;

	@FindBy(xpath = signatureTableFieldSel)
	private WebElement signatureTableField;

	@FindBy(xpath = locationTableFieldSel)
	private WebElement locationTableField;

	@FindBy(xpath = lastLoginTableFieldSel)
	private WebElement lastLoginTableField;

	@FindBy(xpath = registrationDateTableFieldSel)
	private WebElement registrationDateTableField;

	@FindBy(xpath = avatarDateTableFieldSel)
	private WebElement avatarDateTableField;

	@FindBy(xpath = postCountDateTableFieldSel)
	private WebElement postCountDateTableField;

	public ProfilePage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	//Getters


	public WebElement getPostListButton() {
		return postListButton;
	}

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

	public WebElement getProfileLinkFromMainPage() {
		return profileLinkFromMainPage;
	}

	public WebElement getEditProfileButton() {
		return editProfileButton;
	}

	public WebElement getEmailEditField() {
		return emailEditField;
	}

	public WebElement getSaveEditButton() {
		return saveEditButton;
	}

	public WebElement getEmail() {
		return email;
	}

	public WebElement getCurrentPasswordField() {
		return currentPasswordField;
	}

	public WebElement getNewPasswordField() {
		return newPasswordField;
	}

	public WebElement getConfirmNewPasswordField() {
		return confirmNewPasswordField;
	}

	public WebElement getErrorCurrentUserPasswordMessage() {
		return errorCurrentUserPasswordMessage;
	}

	public WebElement getErrorNewUserPasswordMessage() {
		return errorNewUserPasswordMessage;
	}

	public WebElement getErrorNewUserPasswordConfirmMessage() {
		return errorNewUserPasswordConfirmMessage;
	}

	public WebElement getBackButton() {
		return backButton;
	}

	public WebElement getProfileLinkFromLastColumn() {
		return profileLinkFromLastColumn;
	}

	public WebElement getSignatureField() {
		return signatureField;
	}

	public WebElement getSignatureText() {
		return signatureText;
	}

	public WebElement getNewPrivateMessageButton() {
		return newPrivateMessageButton;
	}

	public WebElement getEmptyMessageInPosList() {
		return emptyMessageInPosList;
	}

	public WebElement getUsernameTableField() {
		return usernameTableField;
	}

	public WebElement getFirstNameTableField() {
		return firstNameTableField;
	}

	public WebElement getLastNameTableField() {
		return lastNameTableField;
	}

	public WebElement getEmailTableField() {
		return emailTableField;
	}

	public WebElement getLanguageTableField() {
		return languageTableField;
	}

	public WebElement getPageSizeTableField() {
		return pageSizeTableField;
	}

	public WebElement getSignatureTableField() {
		return signatureTableField;
	}

	public WebElement getLocationTableField() {
		return locationTableField;
	}

	public WebElement getLastLoginTableField() {
		return lastLoginTableField;
	}

	public WebElement getRegistrationDateTableField() {
		return registrationDateTableField;
	}

	public WebElement getAvatarDateTableField() {
		return avatarDateTableField;
	}

	public WebElement getPostCountDateTableField() {
		return postCountDateTableField;
	}
}
