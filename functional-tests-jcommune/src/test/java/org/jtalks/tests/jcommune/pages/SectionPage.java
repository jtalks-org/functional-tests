package org.jtalks.tests.jcommune.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @autor masyan
 */
public class SectionPage {

	public static final String sectionListSel = "//a[@class='forum_header_link']";

	@FindBy(xpath = sectionListSel)
	private List<WebElement> sectionList;


	public List<WebElement> getSectionList() {
		return sectionList;
	}

	public SectionPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
}
