package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

/**
 * @author Yuliya
 */
public class ForumSettingsDialog {

    public static final String WRONG_IMAGE_SIZE_ERROR = "File is too large. Maximum image size is 10,240 bytes.";

    public static final String WRONG_IMAGE_FORMAT_ERROR = "File has invalid extension. Only *.jpeg, *.jpg, *.gif, *.png, *.ico are allowed.";

    @FindBy(id = "logoPreview")
    private WebElement logoPreview;
    @FindBy(id = "uploadLogo")
    private WebElement uploadLogoBut;
    @FindBy(id = "alert-ok")
    private WebElement alertOkBut;
    @FindBy(id = "removeLogo")
    private WebElement removeLogoBut;
    @FindBy(id = "restoreDefaultOk")
    private WebElement submitRestoreBut;
    @FindBy(id = "restoreDefaultCancel")
    private WebElement cancelRestoreBut;
    @FindBy(id = "iconPreview")
    private WebElement iconPreview;
    @FindBy(id = "uploadIcon")
    private WebElement uploadIconBut;
    @FindBy(id = "removeIcon")
    private WebElement removeIconBut;
    @FindBy(id = "forumName")
    private WebElement titleField;
    @FindBy(id = "forumDescription")
    private WebElement descField;
    @FindBy(id = "forumTitlePrefix")
    private WebElement prefixField;
    @FindBy(id = "forumLogoTooltip")
    private WebElement tooltipField;
    @FindBy(id = "forumCopyright")
    private WebElement copyrightField;
    @FindBy(id = "administrationSubmitButton")
    private WebElement adminSubmitBut;
    @FindBy(id = "administrationCancelButton")
    private WebElement adminCancelBut;
    @FindBy(className = "help-inline")
    private WebElement errorLine;

    public ForumSettingsDialog(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getErrorLine() {
        return errorLine;
    }

    @Step
    public void clickUploadLogoButton() {
        info("Pressing on Upload Logo button");
        uploadLogoBut.click();
    }

    @Step
    public void clickAlertOkButton() {
        info("Pressing on Alert OK button");
        alertOkBut.click();
    }

    @Step
    public void clickRemoveLogoButton() {
        info("Pressing on Remove Logo button");
        removeLogoBut.click();
    }

    @Step
    public void clickSubmitRestoreButton() {
        info("Pressing on Submit Restore button");
        submitRestoreBut.click();
    }

    @Step
    public void clickCancelRestoreButton() {
        info("Pressing on Cancel Restore button");
        cancelRestoreBut.click();
    }

    @Step
    public void clickUploadIconButton() {
        info("Pressing on Upload Icon button");
        uploadIconBut.click();
    }

    @Step
    public void clickRemoveIconButton() {
        info("Pressing on Remove Icon button");
        removeIconBut.click();
    }

    @Step
    public void fillTitleField(String title) {
        info("Fill Title field: " + title);
        titleField.clear();
        titleField.sendKeys(title);
    }

    @Step
    public void fillDescField(String description) {
        info("Fill Description field: " + description);
        descField.clear();
        descField.sendKeys(description);
    }

    @Step
    public void fillPrefixField(String prefix) {
        info("Fill Prefix field: " + prefix);
        prefixField.clear();
        prefixField.sendKeys(prefix);
    }

    @Step
    public void fillTooltipField(String tooltip) {
        info("Fill Tooltip field: " + tooltip);
        tooltipField.clear();
        tooltipField.sendKeys(tooltip);
    }

    @Step
    public void fillCopyrightField(String copyright) {
        info("Fill Copyright field: " + copyright);
        copyrightField.clear();
        copyrightField.sendKeys(copyright);
    }

    @Step
    public void clickSaveChangesButton() {
        info("Clicking Save Changes button");
        adminSubmitBut.click();
    }

    @Step
    public void clickCancelButton() {
        info("Clicking Cancel button");
        adminCancelBut.click();
    }
}