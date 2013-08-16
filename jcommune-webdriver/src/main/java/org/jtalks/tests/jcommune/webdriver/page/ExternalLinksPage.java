package org.jtalks.tests.jcommune.webdriver.page;

import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class ExternalLinksPage {
    public static final String externalLinksSel = "//span[@id='externalLinks']/span/a";

    @FindBy(id = "links_editor")
    private WebElement linksEditorBut;

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

    public ExternalLinksPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

    public WebElement getLinksEditorBut() {
        return linksEditorBut;
    }

    public void setLinksEditorBut(WebElement linksEditorBut) {
        this.linksEditorBut = linksEditorBut;
    }

    public WebElement getAddLinkBut() {
        return addLinkBut;
    }

    public void setAddLinkBut(WebElement addLinkBut) {
        this.addLinkBut = addLinkBut;
    }

    public WebElement getTitleField() {
        return titleField;
    }

    public void setTitleField(WebElement titleField) {
        this.titleField = titleField;
    }

    public WebElement getUrlField() {
        return urlField;
    }

    public void setUrlField(WebElement urlField) {
        this.urlField = urlField;
    }

    public WebElement getHintField() {
        return hintField;
    }

    public void setHintField(WebElement hintField) {
        this.hintField = hintField;
    }

    public WebElement getSaveLinkBut() {
        return saveLinkBut;
    }

    public void setSaveLinkBut(WebElement saveLinkBut) {
        this.saveLinkBut = saveLinkBut;
    }

    public WebElement getCancelLinkBut() {
        return cancelLinkBut;
    }

    public void setCancelLinkBut(WebElement cancelLinkBut) {
        this.cancelLinkBut = cancelLinkBut;
    }

    public WebElement getRemoveLinkBut() {
        return removeLinkBut;
    }

    public void setRemoveLinkBut(WebElement removeLinkBut) {
        this.removeLinkBut = removeLinkBut;
    }

    public List<WebElement> getExternalLinks() {
        return externalLinks;
    }

    public void setExternalLinks(List<WebElement> externalLinks) {
        this.externalLinks = externalLinks;
    }
}
