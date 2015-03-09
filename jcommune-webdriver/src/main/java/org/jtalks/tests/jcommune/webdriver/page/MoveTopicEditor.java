package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;

/**
 * @author pancheshenko andrey
 */
public class MoveTopicEditor {
    public static final String moveTopicEditorFormSel = "move-topic-editor";

    public static final String selectWithSectionNamesSel = "section_name";

    public static final String selectWithBranchNamesSel = "branch_name";

    public static final String firstBranchInMoveTopicEditorSel = "//select[@id='branch_name']/option[@value='1']";

    public static final String confirmMoveButtonSel = "move-button-save";

    @FindBy(id = moveTopicEditorFormSel)
    private WebElement moveTopicEditorForm;

    @FindBy(id = selectWithSectionNamesSel)
    private WebElement selectWithSectionNames;

    @FindBy(id = selectWithBranchNamesSel)
    private WebElement selectWithBranchNames;

    @FindBy(id = confirmMoveButtonSel)
    private WebElement confirmMoveButton;

    private WebDriver driver;

    public MoveTopicEditor(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    /**
     * This method will choose 'All sections' in sections list, and then choose
     * the @branchNameDest branch title in the branch list.
     *
     * @param branchNameDest - title of the required branch to choose in the 'All sections' list
     * @return
     */
    public void chooseBranch(String branchNameDest) {
        info("Selecting the [" + branchNameDest + "] branch in the [All sections] list");
        Select sectionList = new Select(getSelectWithSectionNames());
        for (WebElement sectionListCurrentOption : sectionList.getOptions()) {
            if (sectionListCurrentOption.getText().equals("All sections")) {
                sectionListCurrentOption.click();
                info("Section [" + sectionListCurrentOption.getText() + "] was selected");
                break;
            }
        }

        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(firstBranchInMoveTopicEditorSel)));

        Select branchList = new Select(getSelectWithBranchNames());
        for (WebElement branchNameFromList : branchList.getOptions()) {
            if (branchNameFromList.getText().equals(branchNameDest)) {
                branchNameFromList.click();
                info("Branch [" + branchNameFromList.getText() + "] was selected");
                return;
            }
        }
        throw new NoSuchElementException("Branch [" + branchNameDest + "] was not found in the 'All branches' list.");
    }

    /**
     * This method will choose 'All sections' in sections list, and then choose
     * the @branchIndex branch index in the branch list.
     *
     * @param branchIndex - index of the required branch to choose in the 'All sections' list
     * @return - returns the title of the chosen branch if the index was inside the bounds
     */
    public String chooseBranchIndex(Integer branchIndex) {
        info("Selecting the [" + (branchIndex + 1) + "] branch number in the 'All branches' list");
        Select sectionList = new Select(getSelectWithSectionNames());
        sectionList.getOptions().get(0).click(); // select 'All sections'

        (new WebDriverWait(driver, 10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath(firstBranchInMoveTopicEditorSel)));

        Select branchList = new Select(getSelectWithBranchNames());
        if (branchIndex < branchList.getOptions().size()) {
            WebElement branchNameFromList = branchList.getOptions().get(branchIndex);
            branchNameFromList.click();
            info("Branch [" + branchNameFromList.getText() + "] was selected");
            return branchNameFromList.getText();
        }
        throw new IndexOutOfBoundsException("Desired branch index is out of bounds for the 'All branches' list.");
    }

    public void clickConfirmMoveButton() {
        info("Clicking move confirmation button in move topic editor");
        getConfirmMoveButton().click();
    }

    // Getters

    public WebElement getMoveTopicEditorForm() {
        return moveTopicEditorForm;
    }

    public WebElement getSelectWithSectionNames() {
        return selectWithSectionNames;
    }

    public WebElement getSelectWithBranchNames() {
        return selectWithBranchNames;
    }

    public WebElement getConfirmMoveButton() {
        return confirmMoveButton;
    }
}
