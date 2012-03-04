package org.jtalks.tests.jcommune.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * @autor masyan
 */
public class BranchPage {

	public static final String branchListSel = "//a[@class='forum_link']";

	@FindBy(xpath = branchListSel)
	private List<WebElement> branchList;


	public List<WebElement> getBranchList() {
		return branchList;
	}

	public BranchPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

}
