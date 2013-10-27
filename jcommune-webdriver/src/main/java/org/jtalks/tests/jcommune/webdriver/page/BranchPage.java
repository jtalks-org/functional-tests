package org.jtalks.tests.jcommune.webdriver.page;

import net.thucydides.core.pages.PageObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BranchPage extends PageObject {
    @FindBy(className = "branch-title")
    private List<WebElement> branchList;

    public BranchPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}

	public List<WebElement> getBranchList() {
		return branchList;
	}
}
