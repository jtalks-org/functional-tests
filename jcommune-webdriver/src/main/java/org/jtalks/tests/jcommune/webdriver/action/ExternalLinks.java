package org.jtalks.tests.jcommune.webdriver.action;

import junit.framework.AssertionFailedError;
import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import org.jtalks.tests.jcommune.webdriver.entity.externallink.ExternalLink;
import org.jtalks.tests.jcommune.webdriver.page.ExternalLinksPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.externalLinksPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author stanislav bashkirtsev
 * @author maxim reshetov
 */
public class ExternalLinks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalLinks.class);

    public static ExternalLink createExternalLink(ExternalLink externalLink) {
        openExternalLinksDialog();
        externalLinksPage.getAddLinkBut().click();
        fillLinkFields(externalLink);
        externalLinksPage.getSaveLinkBut().click();
        return externalLink;
    }

    public static boolean assertLinkVisible(ExternalLink externalLink) {
        List<ExternalLink> linksFromPage = ExternalLink.fromForm(externalLinksPage.getExternalLinks());
        for (ExternalLink link : linksFromPage) {
            if (externalLink.equals(link)) {
                return true;
            }
        }
        LOGGER.info("Expected Link not found: {}. \nActual Links From page: {}", externalLink, linksFromPage);
        throw new AssertionFailedError("The links is not present on the page");
    }

    public static void removeExternalLink(ExternalLink externalLink) {
        openExternalLinksDialog();
        WebElement link = getLinkLine(externalLink);
        link.findElement(By.className(ExternalLinksPage.externalLinksRemoveIconFromDialogSel)).click();
        sleep(500);
        externalLinksPage.getRemoveLinkBut().click();
        externalLinksPage.getCloseDialogButton().submit();
    }

    public static void exitAdminMode() {
        mainPage.getAdministrationDropdownMenu().click();
        mainPage.getOffAdminModeBut().click();
    }

    public static void enterAdministrationMode() {
        mainPage.getAdministrationDropdownMenu().click();
        mainPage.getOnAdminModeBut().click();
    }

    private static void fillLinkFields(ExternalLink externalLink) {
        externalLinksPage.getTitleField().sendKeys(externalLink.getTitle());
        externalLinksPage.getUrlField().sendKeys(externalLink.getHref());
        externalLinksPage.getHintField().sendKeys(externalLink.getHint());
    }

    private static void openExternalLinksDialog() {
        List<WebElement> checkDialog = mainPage.getModalDialog();
        boolean visible = false;

        for (WebElement webElement : checkDialog) {
            if (webElement.isDisplayed()) {
                visible = true;
            }
        }
        if (!visible) {
            enterAdministrationMode();
            externalLinksPage.getLinksEditorBut().click();
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
        for (WebElement link : externalLinksPage.getExternalLinksFromDialog()) {
            WebElement href = link.findElement(By.xpath(ExternalLinksPage.externalLinksHrefFromDialogSel));
            if (DriverMethodHelp.getTextFromTag(href).equals(externalLink.getHref())) {
                return link;
            }
        }
        return null;
    }
}
