package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ExternalLinksDialog {
    public static final String externalLinksSel = "//span[@id='externalLinks']/span/a";
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
    @FindBy(xpath = externalLinksSel)
    private List<WebElement> externalLinks;
    @FindBy(className = "close")
    private WebElement closeDialogButton;
    @FindBy(xpath = externalLinksFromDialogSel)
    private List<WebElement> externalLinksFromDialog;

    public ExternalLinksDialog(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement getCloseDialogButton() {
        return closeDialogButton;
    }

    public WebElement getAddLinkBut() {
        return addLinkBut;
    }

    public WebElement getTitleField() {
        return titleField;
    }

    public WebElement getUrlField() {
        return urlField;
    }

    public WebElement getHintField() {
        return hintField;
    }

    public WebElement getSaveLinkBut() {
        return saveLinkBut;
    }

    public WebElement getCancelLinkBut() {
        return cancelLinkBut;
    }

    public WebElement getRemoveLinkBut() {
        return removeLinkBut;
    }

    public List<WebElement> getExternalLinks() {
        return externalLinks;
    }

    public List<WebElement> getExternalLinksFromDialog() {
        return externalLinksFromDialog;
    }
}
