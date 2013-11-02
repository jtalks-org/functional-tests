package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

public class ExternalLinksDialog {
    public static final String externalLinksFromDialogSel = "//div[@class='modal-body']/table/tbody/tr";
    public static final String externalLinksHrefFromDialogSel = "td[@class='link-url']";
    //class name
    public static final String externalLinksRemoveIconFromDialogSel = "icon-trash";
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
    @FindAll({@FindBy(css = "[id^=big-screen-external-link]"), @FindBy(css = "[id^=small-screen-external-link]")})
    private List<WebElement> externalLinks;
    @FindBy(className = "close")
    private WebElement closeDialogButton;
    @FindBy(xpath = externalLinksFromDialogSel)
    private List<WebElement> externalLinksFromDialog;

    public ExternalLinksDialog(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void closeDialog() {
        info("Closing dialog by pressing Close button");
        closeDialogButton.submit();
    }

    public void clickAddLinkButton() {
        info("Pressing on Add Link button");
        addLinkBut.click();
    }

    public void fillLinkTitleField(String title) {
        info("Fill Link Title field: " + title);
        titleField.sendKeys(title);
    }

    public void fillLinkHrefField(String url) {
        info("Fill Link Href field: " + url);
        urlField.sendKeys(url);
    }

    public void fillLinkHintField(String hint) {
        info("Fill Link Hint field: " + hint);
        hintField.sendKeys(hint);
    }

    public void clickSaveLinkButton() {
        info("Clicking Save Link button");
        saveLinkBut.click();
    }

    public void clickRemoveLinkButton() {
        info("Clicking Remove Link button");
        removeLinkBut.click();
    }

    public List<WebElement> getExternalLinks() {
        return externalLinks;
    }

    public List<WebElement> getExternalLinksFromDialog() {
        return externalLinksFromDialog;
    }
}
