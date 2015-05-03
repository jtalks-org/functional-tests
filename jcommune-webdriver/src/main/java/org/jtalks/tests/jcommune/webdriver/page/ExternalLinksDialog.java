package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

public class ExternalLinksDialog {
    public static final String externalLinksFromDialogSel = "//div[@class='modal-body']/table/tbody/tr";
    public static final String externalLinksHrefFromDialogSel = "td[@class='link-url']";
    public static final String firstTrachIconSel = "/html/body/form/div[2]/table/tbody/tr[1]/td[5]/a";
    //class name
    public static final String externalLinksRemoveIconFromDialogSel = "icon-trash";
    public static final String externalLinksEditIconFromDialogSel = "icon-pencil";

    @FindBy(id = "addMainLink")
    private WebElement addLinkBut;
    @FindBy(id = "linkTitle")
    private WebElement titleField;
    @FindBy(id = "linkUrl")
    private WebElement urlField;
    @FindBy(id = "linkHint")
    private WebElement hintField;
    @FindBy(id = "saveLink")
    private WebElement saveLinkBut;
    @FindBy(id = "cancelLink")
    private WebElement cancelLinkBut;
    @FindBy(id = "removeLink")
    private WebElement removeLinkBut;
    @FindBy(id = "editLink")
    private WebElement editLinkBut;
    @FindAll({@FindBy(css = "[id^=big-screen-external-link]"), @FindBy(css = "[id^=small-screen-external-link]")})
    private List<WebElement> externalLinks;
    @FindBy(className = "close")
    private WebElement closeDialogButton;
    @FindBy(xpath = externalLinksFromDialogSel)
    private List<WebElement> externalLinksFromDialog;
    @FindBy(xpath = firstTrachIconSel)
    private WebElement firstTrachIconButton;

    public ExternalLinksDialog(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @Step
    public void closeDialog() {
        info("Closing dialog by pressing Close button");
        closeDialogButton.submit();
    }

    @Step
    public void clickAddLinkButton() {
        info("Pressing on Add Link button");
        addLinkBut.click();
    }

    @Step
    public void fillLinkTitleField(String title) {
        info("Fill Link Title field: " + title);
        titleField.clear();
        titleField.sendKeys(title);
    }

    @Step
    public void fillLinkHrefField(String url) {
        info("Fill Link Href field: " + url);
        urlField.clear();
        urlField.sendKeys(url);
    }

    @Step
    public void fillLinkHintField(String hint) {
        info("Fill Link Hint field: " + hint);
        hintField.clear();
        hintField.sendKeys(hint);
    }

    @Step
    public void clickSaveLinkButton() {
        info("Clicking Save Link button");
        saveLinkBut.click();
    }

    @Step
    public void clickRemoveLinkButton() {
        info("Clicking Remove Link button");
        removeLinkBut.click();
    }

    @Step
    public void clickFistTrashIconButton() {
        info("Clicking First Trash icon button");
        firstTrachIconButton.click();
    }

    public List<WebElement> getExternalLinks() {
        return externalLinks;
    }

    public List<WebElement> getExternalLinksFromDialog() {
        return externalLinksFromDialog;
    }

    public WebElement getFirstTrachIconButton() {
        return firstTrachIconButton;
    }
}
