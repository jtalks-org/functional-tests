package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
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

    public static final String saveEditButtonIdSel = "saveChanges";

    public static final String errorEmailMessageIdSel = "email.errors";

    public static final String emailSel = "//label[contains(@class,'test-mail')]";

    public static final String errorCurrentUserPasswordMessageIdSel = "currentUserPassword.errors";

    public static final String errorNewUserPasswordMessageIdSel = "newUserPassword.errors";

    public static final String errorNewUserPasswordConfirmMessageIdSel = "newUserPasswordConfirm.errors";

    public static final String backButtonSel = "//a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/user') and @class='button']";

    public static final String profileLinkFromLastColumnSel = "//table[@id='topics-table']/tbody/tr/td[contains(@class,'latest-by')]/p/a";

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

    public static final String pmLinksFromLastColumnSel = "//td[contains(@class, 'latest-by')]/p/a[contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/users/')]";

    public static final String WRONG_CURRENT_PASSWORD = "Пароль не совпадает с текущим паролем\n";

    public static final String WRONG_CONFIRMATION_PASSWORD = "Пароль и подтверждение пароля не совпадают\n";

    public static final String TOO_LONG_PASSWORD = "Размер должен быть между 1 и 50\n";

    public static final String TOO_LONG_FIRST_NAME = "Должно быть 0 - 45 символов\n";

    public static final String TOO_LONG_LAST_NAME = "Должно быть 0 - 255 символов\n";

    public static final String TOO_LONG_LOCATION = "Длина должна быть между 0 и 30\n";

    public static final String TOO_LONG_SIGNATURE = "Длина подписи не должна превышать 255 символов\n";

    public static final String TOO_LONG_EMAIL = "Поле Email не может содержать более 50 символов\n";

    public static final String INVALID_EMAIL = "Допустимый формат email: mail@mail.ru\n";

    public static final String EMPTY_EMAIL = "Не может быть пустым\n";

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

    @FindBy(id = "userSecurityDto.currentUserPassword")
    private WebElement currentPasswordField;

    @FindBy(id = "userSecurityDto.newUserPassword")
    private WebElement newPasswordField;

    @FindBy(id = "userSecurityDto.newUserPasswordConfirm")
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

    @FindBy(id = "userProfileDto.email")
    private WebElement emailEditField;

    @FindBy(id = saveEditButtonIdSel)
    private WebElement saveEditButton;

    @FindBy(xpath = profileLinkFromLastColumnSel)
    private WebElement profileLinkFromLastColumn;

    @FindBy(id = "userProfileDto.signature")
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

    @FindBy(id = "userProfileDto.pageSize")
    private WebElement pageSizeField;

    @FindBy(xpath = pmLinksFromLastColumnSel)
    private List<WebElement> pmLinksFromLastColumn;

    @FindBy(id = "userNotificationsDto.autosubscribe1")
    private WebElement autoSubscribeCheckbox;

    @FindBy(id = "userNotificationsDto.mentioningNotificationsEnabled1")
    private WebElement notifyIfSomeoneMentionsYouCheckbox;

    @FindBy(id = "userNotificationsDto.sendPmNotification1")
    private WebElement notifyIfPrivateMessageIsReceivedCheckbox;

    @FindBy(id = "userProfileDto.location")
    private WebElement locationField;

    @FindBy(id = "profileBtn")
    private WebElement profileTabButton;

    @FindBy(id = "contactsBtn")
    private WebElement contacsTabButton;

    @FindBy(id = "notificationsBtn")
    private WebElement notificationsTabButton;

    @FindBy(id = "securityBtn")
    private WebElement securityTabButton;

    @FindBy(id = "userProfileDto.firstName")
    private WebElement firstNameField;

    @FindBy(id = "userProfileDto.lastName")
    private WebElement lastNameField;

    @FindBy(id = "user-menu-profile")
    private WebElement profileButton;

    @FindBy(id = "user-dropdown-menu-link")
    private WebElement dropDownMenu;

    @FindBy(css = "div.control-group.error")
    List<WebElement> errorFormElements;

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

    public WebElement getAutoSubscribeCheckbox() {
        return  autoSubscribeCheckbox;
    }

    public WebElement getNotifyIfSomeoneMentionsYouCheckbox() {
        return notifyIfSomeoneMentionsYouCheckbox;
    }

    public WebElement getNotifyIfPrivateMessageIsReceivedCheckbox() {
        return notifyIfPrivateMessageIsReceivedCheckbox;
    }

    public WebElement getProfileTabButton() {
        return profileTabButton;
    }

    public WebElement getContacsTabButton() {
        return contacsTabButton;
    }

    public WebElement getNotificationsTabButton() {
        return notificationsTabButton;
    }

    public WebElement getSecurityTabButton() {
        return securityTabButton;
    }

    public WebElement getLocationField() {
        return locationField;
    }

    public WebElement getFirstNameField() {
        return firstNameField;
    }

    public WebElement getLastNameField() {
        return lastNameField;
    }

    public WebElement getProfileButton() {
        return profileButton;
    }

    public WebElement getDropDownMenu() {
        return dropDownMenu;
    }

    //setters

    public void selectPageSizeByValue(int num) {
        String sNum = Integer.toString(num);
        new Select(getPageSizeField()).selectByValue(sNum);
    }

    public void selectAutoSubscribe(boolean flag) {
        if (flag == autoSubscribeCheckbox.isSelected()) {
            info(flag ? "Autosubscribe checkbox is already checked" : "Autosubscribe checkbox is already unchecked" + " so doing nothing");
        } else {
            info("Autosubscribe will be check");
            autoSubscribeCheckbox.click();
        }
    }

    public void selectNotifyIfSomeoneMentionsYou(boolean flag) {
        if (flag == notifyIfSomeoneMentionsYouCheckbox.isSelected()) {
            info(flag ? "NotifyIfSomeoneMentionsYouCheckbox checked" : "NotifyIfSomeoneMentionsYouCheckbox unchecked" + " so doing nothing");
        } else {
            info("NotifyIfSomeoneMentionsYouCheckbox will be check");
            notifyIfSomeoneMentionsYouCheckbox.click();
        }
    }

    public void selectNotifyIfPrivateMessageIsReceived(boolean flag) {
        if (flag == notifyIfPrivateMessageIsReceivedCheckbox.isSelected()) {
            info(flag ? "NotifyIfPrivateMessageIsReceivedCheckbox checked" : "unchecked" + " so doing nothing");
        } else {
            info("NotifyIfPrivateMessageIsReceivedCheckbox will be check");
            notifyIfPrivateMessageIsReceivedCheckbox.click();
        }
    }

    public void clickOnDropDownMenuForUserOnMainPage() {
        dropDownMenu.click();
    }

    public void clickOnProfileInDropDownMenu() {
        profileButton.click();
    }

    public void clickOnSaveButtonInProfile() {
        saveEditButton.click();
    }

    public void fillFirstName(String firstName) {
        info("First name: " + firstName);
        firstNameField.clear();
        firstNameField.sendKeys(firstName);
    }

    public void fillLastName(String lastName) {
        info("Last name: " + lastName);
        lastNameField.clear();
        lastNameField.sendKeys(lastName);
    }

    public void fillSignature(String signature) {
        info("Signature: " + signature);
        signatureField.clear();
        signatureField.sendKeys(signature);
    }

    public void fillEmail(String email) {
        info("Email: " + email);
        emailEditField.clear();
        emailEditField.sendKeys(email);
    }

    public void fillLocation(String location) {
        info("Location: " + location);
        locationField.clear();
        locationField.sendKeys(location);
    }

    public void fillCurrentPassword(String currentPassword) {
        info("Current password: " + currentPassword);
        currentPasswordField.sendKeys(currentPassword);
    }

    public void fillNewPassword(String newPassword) {
        info("New password: " + newPassword);
        newPasswordField.sendKeys(newPassword);
    }

    public void fillConfirmNewPassword(String confirmNewPassword) {
        info("Confirm new password: " + confirmNewPassword);
        confirmNewPasswordField.sendKeys(confirmNewPassword);
    }

    public void openContactsTab() {
        info("Clicking on contacts tab button");
        contacsTabButton.click();
    }

    public void openNotificationsTab() {
        info("Clicking on notifications tab button");
        notificationsTabButton.click();
    }

    public void openSecurityTab() {
        info("Clicking on security tab button");
        securityTabButton.click();
    }

    public List<WebElement> getErrorFormElements() {
        return errorFormElements;
    }

    public WebElement getSelectedValueInList() {
        Select select = new Select(getPageSizeField());
        return select.getFirstSelectedOption();
    }
}
