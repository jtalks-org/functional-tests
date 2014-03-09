package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

/**
 * @author masyan
 */
public class ProfilePage {

    public static final String currentUserLinkSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user']";

    public static final String userDetailsFormSel = "userdetails";

    public static final String profileLinkFromPMInpoxPageSel = "//a[contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/users')]";

    public static final String profileLinkFromMainPageSel = "//a[@href='" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user']";

    public static final String editProfileButtonSel = "//a[contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/users/edit') and @type='submit']";

    public static final String emailEditFieldIdSel = "email";

    public static final String saveEditButtonIdSel = "saveChanges";

    public static final String errorEmailMessageIdSel = "email.errors";

    public static final String emailSel = "//label[contains(@class,'test-mail')]";

    public static final String currentPasswordFieldIdSel = "currentUserPassword";

    public static final String newPasswordFieldIdSel = "newUserPassword";

    public static final String confirmNewPasswordFieldIdSel = "newUserPasswordConfirm";

    public static final String errorCurrentUserPasswordMessageIdSel = "currentUserPassword.errors";

    public static final String errorNewUserPasswordMessageIdSel = "newUserPassword.errors";

    public static final String errorNewUserPasswordConfirmMessageIdSel = "newUserPasswordConfirm.errors";

    public static final String backButtonSel = "//a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user') and @class='button']";

    public static final String profileLinkFromLastColumnSel = "//table[@id='topics-table']/tbody/tr/td[contains(@class,'latest-by')]/p/a";

    public static final String signatureFieldSel = "signature";

    public static final String signatureTextSel = "//label[contains(@class,'test-signature')]";

    public static final String postListButtonSel = "//a[@class='btn btn-mini pull-right user-profile-buttons-posts']";

    public static final String emptyMessageInPosListSel = "//div[@class='post']//td[contains(text(), 'There are no posts yet')]";

    public static final String newPrivateMessageButtonSel = "//a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/pm/new')]";

    public static final String usernameTableFieldSel = "//h2[@class='pull-right user-profile-username']";

    public static final String firstNameTableFieldSel = "//label[contains(text(),'First name')]";

    public static final String lastNameTableFieldSel = "//label[contains(text(),'Last name')]";

    public static final String emailTableFieldSel = "//label[contains(text(),'Email')]";

    public static final String languageTableFieldSel = "//label[contains(text(),'Language')]";

    public static final String pageSizeTableFieldSel = "//label[contains(text(), 'Page size')]";

    public static final String signatureTableFieldSel = "//label[contains(text(),'Your signature')]";

    public static final String locationTableFieldSel = "//label[contains(text(),'Location')]";

    public static final String lastLoginTableFieldSel = "//label[contains(text(),'Last login')]";

    public static final String registrationDateTableFieldSel = "//label[contains(text(),'Registration date')]";

    public static final String postCountDateTableFieldSel = "//label[contains(text(),'Post count')]";

    public static final String pageSizeFieldSel = "pageSize";

    public static final String pmLinksFromLastColumnSel = "//td[contains(@class, 'latest-by')]/p/a[contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/users/')]";

    public static final String WRONG_CURRENT_PASSWORD = "Password does not match to the current password";

    public static final String WRONG_CONFIRMATION_PASSWORD = "Password and confirmation password do not match";

    public static final String TOO_LONG_PASSWORD = "size must be between 1 and 50";

    public static final String TOO_LONG_FIRST_NAME = "should be 0 - 45 characters";

    public static final String TOO_LONG_LAST_NAME = "should be 0 - 255 characters";

    public static final String TOO_LONG_LOCATION = "length must be between 0 and 30";

    public static final String TOO_LONG_SIGNATURE = "Signature length must be no more 255 characters";

    public static final String TOO_LONG_EMAIL = "Email field should not contain more than 50 symbols";

    public static final String INVALID_EMAIL = "An email format should be like mail@mail.ru";

    public static final String EMPTY_EMAIL = "Must not be empty";

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

    @FindBy(xpath = postCountDateTableFieldSel)
    private WebElement postCountDateTableField;

    @FindBy(id = pageSizeFieldSel)
    private WebElement pageSizeField;

    @FindBy(xpath = pmLinksFromLastColumnSel)
    private List<WebElement> pmLinksFromLastColumn;

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

    public WebElement getPostCountDateTableField() {
        return postCountDateTableField;
    }

    public WebElement getPageSizeField() {
        return pageSizeField;
    }

    public List<WebElement> getPmLinksFromLastColumn() {
        return pmLinksFromLastColumn;
    }


    //setters

    public void selectPageSizeByValue(int num) {
        String sNum = Integer.toString(num);
        new Select(getPageSizeField()).selectByValue(sNum);
    }

}
