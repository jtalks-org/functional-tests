package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import org.jtalks.tests.jcommune.webdriver.entity.externallink.ExternalLink;
import org.jtalks.tests.jcommune.webdriver.page.ExternalLinksPage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.externalLinksPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author stanislav bashkirtsev
 * @author maxim reshetov
 */
public class ExternalLinks {
    public static ExternalLink createExternalLink(ExternalLink externalLink) {
        openExternalLinksDialog();
        externalLinksPage.getAddLinkBut().click();
        fillLinkFields(externalLink);
        externalLinksPage.getSaveLinkBut().click();
        return externalLink;
    }

    public static boolean isVisibleOnMainPage(ExternalLink externalLink) {
        for (WebElement link : externalLinksPage.getExternalLinks()) {
            /*
            *in browser href ends with "/"
            * if href is empty string than equal Title
            */
            if (externalLink.getHref().equalsIgnoreCase(link.getAttribute("href").replaceAll("/$", "")) ||
                    externalLink.getHref().equalsIgnoreCase(link.getAttribute("href")) ||
                    externalLink.getTitle().equals(link.getText())) {
                return true;
            }
        }

        return false;
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
