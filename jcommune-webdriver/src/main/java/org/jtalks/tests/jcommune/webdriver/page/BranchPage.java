package org.jtalks.tests.jcommune.webdriver.page;

import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BranchPage {
    @FindBy(xpath = "//tbody/tr/td/a[contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/branches/')]")
    private List<WebElement> branchList;
    @FindBy(xpath = "//ul[@class='breadcrumb']//a[contains(@href,'" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH +
            "/branches/')]")
    private WebElement breadCrumbsBranchLink;

    @FindBy(xpath = "//div[2]/div/h2")
    private WebElement branchName;

    public BranchPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

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
