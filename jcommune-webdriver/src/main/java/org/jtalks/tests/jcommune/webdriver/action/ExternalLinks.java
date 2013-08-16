package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.webdriver.entity.externallink.ExternalLink;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;

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
            //in browser href ends of "/"
            if (externalLink.getHref().equalsIgnoreCase(link.getAttribute("href").replaceAll("/$", "")) ||
                    externalLink.getHref().equalsIgnoreCase(link.getAttribute("href"))) {
                return true;
            }
        }

        return false;
    }

    public static void removeExternalLink(ExternalLink externalLink) {
        openExternalLinksDialog();
        WebElement link = getLinkLine(externalLink);
        link.findElement(By.className(externalLinksPage.externalLinksRemoveIconFromDialogSel)).click();
        externalLinksPage.getRemoveLinkBut().click();
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
            if(webElement.isDisplayed()){
              visible = true;
            }
        }
        if(!visible){
            mainPage.getAdministrationDropdownMenu().click();
            mainPage.getOnAdminModeBut().click();
            externalLinksPage.getLinksEditorBut().click();
        }
    }

    private static WebElement getLinkLine(ExternalLink externalLink) {
        for (WebElement link : externalLinksPage.getExternalLinksFromDialog()) {
            if (link.findElement(By.xpath(externalLinksPage.externalLinksHrefFromDialogSel))
                    .getAttribute("innerHTML").equalsIgnoreCase(externalLink.getHref())) {
                return link;
            }
        }
        return null;
    }

}
