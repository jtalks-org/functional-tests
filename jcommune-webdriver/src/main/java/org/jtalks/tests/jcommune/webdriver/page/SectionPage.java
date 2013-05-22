package org.jtalks.tests.jcommune.webdriver.page;


import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @author masyan
 */
public class SectionPage {

    public static final String sectionListSel = "//a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/sections/')]";

    public static final String breadCrumbsSectionLinkSel = "//ul[@class='breadcrumb']//a[contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/sections/')]";

    @FindBy(xpath = sectionListSel)
    private List<WebElement> sectionList;

    @FindBy(xpath = breadCrumbsSectionLinkSel)
    private WebElement breadCrumbsSectionLink;


    public SectionPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    //Getters
    public List<WebElement> getSectionList() {
        return sectionList;
    }

    public WebElement getBreadCrumbsSectionLink() {
        return breadCrumbsSectionLink;
    }
}
