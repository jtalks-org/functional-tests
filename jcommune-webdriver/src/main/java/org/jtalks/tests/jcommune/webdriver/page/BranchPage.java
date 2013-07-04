package org.jtalks.tests.jcommune.webdriver.page;

import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: erik Date: 04.03.12 Time: 11:18 To change
 * this template use File | Settings | File Templates.
 */
public class BranchPage {
	public static final String branchListSel = "//tbody/tr/td/a[contains(@href,'"
			+ JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/branches/')]";


	public static final String breadCrumbsBranchLinkSel = "//ul[@class='breadcrumb']//a[contains(@href,'"
			+ JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/branches/')]";

	public static final String branchNameSel = "//div[2]/div/h2";

	@FindBy(xpath = branchListSel)
	private List<WebElement> branchList;

	@FindBy(xpath = breadCrumbsBranchLinkSel)
	private WebElement breadCrumbsBranchLink;

	@FindBy(xpath = branchNameSel)
	private WebElement branchName;

	public BranchPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	// Getters
	public List<WebElement> getBranchList() {
		return branchList;
	}

	public WebElement getBreadCrumbsBranchLink() {
		return breadCrumbsBranchLink;
	}

	public String getBranchName() {
		return branchName.getText();
	}

}
