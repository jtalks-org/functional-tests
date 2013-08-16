package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.webdriver.entity.externallink.ExternalLink;
import org.openqa.selenium.WebElement;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;

/**
 * @author stanislav bashkirtsev
 * @author maxim reshetov
 */
public class ExternalLinks {
    public static ExternalLink createExternalLink(ExternalLink externalLink) {
     mainPage.getOnAdminModeBut().click();
     externalLinksPage.getLinksEditorBut().click();
     fillLinkFields(externalLink);
     externalLinksPage.getSaveLinkBut().click();
     return externalLink;
    }

    public static boolean isVisibleOnMainPage(ExternalLink externalLink) {
        for(WebElement link : externalLinksPage.getExternalLinks()){
            if(externalLink.getHref().equals(link.getAttribute("href"))){
                return true;
            }
        }

        return false;
    }

    public static void removeExternalLink(ExternalLink externalLink) {
        throw new UnsupportedOperationException();
    }

    private static void fillLinkFields(ExternalLink externalLink) {
        externalLinksPage.getTitleField().sendKeys(externalLink.getTitle());
        externalLinksPage.getUrlField().sendKeys(externalLink.getHref());
        externalLinksPage.getHintField().sendKeys(externalLink.getHint());
    }
}
