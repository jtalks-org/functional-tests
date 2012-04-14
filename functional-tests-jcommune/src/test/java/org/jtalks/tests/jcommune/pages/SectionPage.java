package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @author masyan
 */
public class SectionPage {

	public static final String sectionListSel = "//a[@class='forum_header_link']";

	public static final String breadCrumbsSectionLinkSel = "//ul[@class='breadcrumbs']//a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/sections/')]";

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
