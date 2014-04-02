package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class BranchPage {
    @FindBy(className = "branch-title")
    private List<WebElement> branchList;

    public BranchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public WebElement findBranch(String branchTitle) {
        for (WebElement branch : getBranches()) {
            if (branch.getText().equals(branchTitle)) {
                return branch;
            }
        }
        return null;
    }

    public List<WebElement> getBranches() {
        return branchList;
    }
}
