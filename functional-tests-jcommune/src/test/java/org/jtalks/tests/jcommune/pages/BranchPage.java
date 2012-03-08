package org.jtalks.tests.jcommune.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: erik
 * Date: 04.03.12
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class BranchPage{
	public static final String branchListSel = "//a[@class='forum_link']";

	@FindBy(xpath = branchListSel)
	private List<WebElement> branchList;

    public BranchPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

	public List<WebElement> getBranchList() {
		return branchList;
	}

}
