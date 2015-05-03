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
import ru.yandex.qatools.allure.annotations.Step;

import java.util.Iterator;
import java.util.List;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.externalLinksDialog;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author stanislav bashkirtsev
 * @author maxim reshetov
 */
public class ExternalLinks {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExternalLinks.class);

    @Step
    public static ExternalLink createExternalLink(ExternalLink externalLink) {
        info("Creating an External Link: " + externalLink);
        openExternalLinksDialog();
        externalLinksDialog.clickAddLinkButton();
        info("Fill new link data");
        fillLinkFields(externalLink);
        externalLinksDialog.clickSaveLinkButton();
        externalLinksDialog.closeDialog();
        return externalLink;
    }

    @Step
    public static void removeExternalLink(ExternalLink externalLink) {
        info("Removing External Link: " + externalLink);
        openExternalLinksDialog();
        WebElement link = getLinkLine(externalLink);
        link.findElement(By.className(ExternalLinksDialog.externalLinksRemoveIconFromDialogSel)).click();
        sleep(500);
        externalLinksDialog.clickRemoveLinkButton();
        externalLinksDialog.closeDialog();
    }

    @Step
    public static void editExternalLink(ExternalLink externalLink) {
        info("Editing an External Link: " + externalLink);
        openExternalLinksDialog();
        WebElement link = getLinkLine(externalLink);
        link.findElement(By.className(ExternalLinksDialog.externalLinksEditIconFromDialogSel)).click();
        sleep(500);
        fillLinkFields(externalLink);
        sleep(500);
        externalLinksDialog.clickSaveLinkButton();
        externalLinksDialog.closeDialog();
    }

    @Step
    public static boolean assertLinkVisible(ExternalLink externalLink) {
        info("Checking whether the link is actually visible on the page");
        List<ExternalLink> linksFromPage = ExternalLink.fromForm(externalLinksDialog.getExternalLinks());
        for (ExternalLink link : linksFromPage) {
            if (externalLink.getHref().isEmpty()) {//in other cases the Href is correct even returned by HtmlUnit
                normalizeHtmlUnitHref(link);
            }
            if (externalLink.equals(link)) {
                info("The link was found!");
                return true;
            }
        }
        LOGGER.info("Expected Link not found: {}. \nActual Links From page: {}", externalLink, linksFromPage);
        throw new AssertionFailedError("The links is not present on the page: " + externalLink);
    }

    @Step
    public static boolean assertLinkIsNotVisible(ExternalLink externalLink) {
        info("Checking whether the link is actually NOT visible on the page");
        List<ExternalLink> linksFromPage = ExternalLink.fromForm(externalLinksDialog.getExternalLinks());
        for (ExternalLink link : linksFromPage) {
            if (externalLink.getHref().isEmpty()) {//in other cases the Href is correct even returned by HtmlUnit
                normalizeHtmlUnitHref(link);
            }
            if (externalLink.equals(link)) {
                LOGGER.info("Expected Link was not deleted: {}. \nActual Links From page: {}", externalLink, linksFromPage);
                throw new AssertionFailedError("Deleted link is present on the page: " + externalLink);
            }
        }
        info("The link was NOT found, as expected");
        return false;

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



    public static void exitAdminMode() {
        mainPage.switchOffAdminMode();
    }

    private static void fillLinkFields(ExternalLink externalLink) {
        sleep(1000);
        externalLinksDialog.fillLinkTitleField(externalLink.getTitle());
        externalLinksDialog.fillLinkHrefField(externalLink.getHref());
        externalLinksDialog.fillLinkHintField(externalLink.getHint());
    }

    @Step
    private static void openExternalLinksDialog() {
        info("Opening External Links dialog");
        mainPage.switchOnAdminMode();
        mainPage.pressOpenExternalLinksDialog();
        info("Checking if External Links dialog is opened");
        if (!mainPage.isExternalLinksDialogVisible()) {
            info("Opening External Links dialog again");
            mainPage.switchOnAdminMode();
            mainPage.pressOpenExternalLinksDialog();
            info("Checking if External Links dialog is opened");
            if (!mainPage.isExternalLinksDialogVisible()) {
                throw new NoSuchElementException("Tried to open External Links dialog 2 times without any success");
            }
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

    public static void removeExistedExternalLinks() {
        info("Remove existed External links");
        openExternalLinksDialog();
        List<WebElement> links = externalLinksDialog.getExternalLinksFromDialog();
        Iterator <WebElement> iterator = links.iterator();
        if (links.size() != 0) {
            LOGGER.info("Going to remove {} links", links.size());
            while (iterator.hasNext()) {
                WebElement link = iterator.next();
                externalLinksDialog.clickFistTrashIconButton();
                externalLinksDialog.clickRemoveLinkButton();
                sleep(500);
                iterator.remove();
            }
        }else {
            LOGGER.info("There are no External links. There is nothing to remove");
            externalLinksDialog.closeDialog();
        }
    }
}
