package org.jtalks.tests.jcommune.pages;

import org.jtalks.tests.jcommune.common.JCommuneSeleniumTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Created by IntelliJ IDEA.
 * User: erik
 * Date: 04.03.12
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class BranchPage{
    public static final String newTopicButtonSel = "//a[contains(@href,'" + JCommuneSeleniumTest.contextPath + "/topics/new')]";

    @FindBy (xpath = newTopicButtonSel)
    WebElement newTopicButton;

    public BranchPage(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    public WebElement getNewTopicButton() {
        return newTopicButton;
    }
}
