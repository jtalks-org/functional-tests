package org.jtalks.tests.jcommune.webdriver.action;

import junit.framework.AssertionFailedError;
import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.externallink.ExternalLink;
import org.jtalks.tests.jcommune.webdriver.page.ExternalLinksDialog;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.externalLinksDialog;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author stanislav bashkirtsev
 * @author maxim reshetov
 */
public class ExternalLinks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalLinks.class);

    public static ExternalLink createExternalLink(ExternalLink externalLink) {
        openExternalLinksDialog();
        externalLinksDialog.getAddLinkBut().click();
        fillLinkFields(externalLink);
        externalLinksDialog.getSaveLinkBut().click();
        externalLinksDialog.getCloseDialogButton().submit();
        return externalLink;
    }

    public static boolean assertLinkVisible(ExternalLink externalLink) {
        List<ExternalLink> linksFromPage = ExternalLink.fromForm(externalLinksDialog.getExternalLinks());
        for (ExternalLink link : linksFromPage) {
            if (externalLink.getHref().isEmpty()) {//in other cases the Href is correct even returned by HtmlUnit
                normalizeHtmlUnitHref(link);
            }
            if (externalLink.equals(link)) {
                return true;
            }
        }
        LOGGER.info("Expected Link not found: {}. \nActual Links From page: {}", externalLink, linksFromPage);
        throw new AssertionFailedError("The links is not present on the page: " + externalLink);
    }

    /**
     * HtmlUnit and other browsers work differently - HtmlUnit tends to return the appUrl if href is empty while real
     * browsers return an empty value. If HtmlUnit is our case, the URL is changed to empty to mimic real browsers.
     *
     * @param link a real link got from Selenium that has to be normalized
     */
    private static void normalizeHtmlUnitHref(ExternalLink link) {
        if (JCommuneSeleniumConfig.getCapabilities().getBrowserName().equalsIgnoreCase("htmlunit")) {
            if (link.getHref().equalsIgnoreCase(JCommuneSeleniumConfig.getAppUrl().replaceAll("/$", ""))) {
                link.withHref("");
            }
        }
    }

    public static void removeExternalLink(ExternalLink externalLink) {
        openExternalLinksDialog();
        WebElement link = getLinkLine(externalLink);
        link.findElement(By.className(ExternalLinksDialog.externalLinksRemoveIconFromDialogSel)).click();
        sleep(500);
        externalLinksDialog.getRemoveLinkBut().click();
        externalLinksDialog.getCloseDialogButton().submit();
    }

    public static void exitAdminMode() {
        if (mainPage.isAdminModeOn()) {
            mainPage.getAdministrationDropdownMenu().click();
            mainPage.getToggleAdmineModeLink().click();
        }
    }

    public static void enterAdministrationMode() {
        if (!mainPage.isAdminModeOn()) {
            mainPage.getAdministrationDropdownMenu().click();
            mainPage.getToggleAdmineModeLink().click();
        }
    }

    private static void fillLinkFields(ExternalLink externalLink) {
        externalLinksDialog.getTitleField().sendKeys(externalLink.getTitle());
        externalLinksDialog.getUrlField().sendKeys(externalLink.getHref());
        externalLinksDialog.getHintField().sendKeys(externalLink.getHint());
    }

    private static void openExternalLinksDialog() {
        WebElement dialog = mainPage.getModalDialog();
        boolean visible;
        try {
            visible = dialog.isDisplayed();
        } catch (NoSuchElementException e) {
            visible = false;
        }
        if (!visible) {
            enterAdministrationMode();
            mainPage.pressOpenExternalLinksDialog();
            sleep(500);
        }
    }

    private static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            throw new IllegalStateException(e);
        }
    }

    private static WebElement getLinkLine(ExternalLink externalLink) {
        for (WebElement link : externalLinksDialog.getExternalLinksFromDialog()) {
            WebElement href = link.findElement(By.xpath(ExternalLinksDialog.externalLinksHrefFromDialogSel));
            if (DriverMethodHelp.getTextFromTag(href).equals(externalLink.getHref())) {
                return link;
            }
        }
        return null;
    }
}
