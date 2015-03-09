package org.jtalks.tests.jcommune.webdriver.page;

import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.yandex.qatools.allure.annotations.Step;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.postPage;

/**
 * @author pancheshenko andrey
 */
public class BranchPage {

    @FindBy(xpath = "//a[@id='subscription' and contains(@href, '/branches/')]")
    private WebElement subscribeButton;

    @FindBy(xpath = "//a[@class='invisible-link' and contains(@href, '/branches/')]")
    private WebElement branchName;

    @FindBy(xpath = "//div[@class='pagination pull-right forum-pagination']/ul/li/a")
    private List<WebElement> topicsButtons;

    @FindBy(xpath = "//div[@class='pagination pull-right forum-pagination']/ul/li[@class='active']/a")
    private List<WebElement> activeTopicsButton;

    @FindBy(className = "topic-types-dropdown")
    private WebElement newTopicTypeToggle;

    @FindBy(className = "new-topic-btn")
    private WebElement newOrdinaryTopicButton;

    @FindBy(className = "new-code-review-btn")
    private WebElement newCodeReviewButton;

    @FindBy(xpath = "//table[@id='topics-table']/tbody/tr/td[@class='posts-td-small posts-td-small_2']/h2/a[contains(@href, '" + JCommuneSeleniumConfig.JCOMMUNE_CONTEXT_PATH + "/topics/')]")
    private List<WebElement> topicsList;

    private final WebDriver driver;

    public BranchPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    @Step
    public void clickCreateTopic() throws PermissionsDeniedException {
        info("Clicking New Topic button");
        try {
            getNewOrdinaryTopicButton().click();
        } catch (NoSuchElementException e) {
            info("No such button found!");
            throw new PermissionsDeniedException("Couldn't find New Topic button. Here is the page source: \n"
                    + driver.getPageSource());
        }
    }

    @Step
    public void clickCreateCodeReview() throws PermissionsDeniedException {
        info("Clicking New Code Review Button");
        try {
            if (getNewTopicToggle() != null) {
                getNewTopicToggle().click();
            }
            getNewCodeReviewButton().click();
        } catch (NoSuchElementException e) {
            info("No such button found!");
            throw new PermissionsDeniedException("Couldn't find New Code Review button. Here is the page source: \n"
                    + driver.getPageSource());
        }
    }

    public boolean isUserAlreadySubscribed() {
        return subscribeButton.getAttribute("href").contains("/unsubscribe");
    }

    public boolean isUserInsideCorrectBranch(String branchTitle) {
        info("Check whether user is in required branch already or inside some topic from this branch");
        if (JCommuneSeleniumConfig.driver.getCurrentUrl().contains("/branches/")) {
            return branchName.getText().trim().equals(branchTitle);
        } else if (JCommuneSeleniumConfig.driver.getCurrentUrl().contains("/topics/")) {
            return postPage.getBranchName().getText().trim().equals(branchTitle);
        }
        return false;
    }

    public boolean openNextPage(int pagesToCheck) {
        info("Trying to open next page on the active branch...");
        int max = 0;
        if (getActiveTopicsButton().size() < 1) {
            return false;
        }
        WebElement activeBtn = getActiveTopicsButton().get(0);
        int active = Integer.parseInt(activeBtn.getText().trim());
        for (WebElement el : getTopicsButtons()) {
            try {
                Integer currentPageNumberInCycle = Integer.parseInt(el.getText().trim());
                if (currentPageNumberInCycle > max)
                    max = currentPageNumberInCycle;
            } catch (NumberFormatException e) {}
        }
        if ((active < pagesToCheck) && (active < max)) {
            for (WebElement elem : getTopicsButtons()) {
                try {
                    Integer currentPageNumberInCycle = Integer.parseInt(elem.getText().trim());
                    if (currentPageNumberInCycle == (active + 1)) {
                        info("Opening " + currentPageNumberInCycle + " page");
                        elem.click();
                        return true;
                    }
                } catch (NumberFormatException e) {}
            }
        }
        info("End of the 'Open next page' loop reached");
        return false;
    }

    public boolean findAndOpenTopic(String topicTitle) throws CouldNotOpenPageException {
        boolean found = false;
        for (WebElement topics : getTopicsList()) {
            if (topics.getText().trim().equals(topicTitle.trim())) {
                topics.click();
                info("Waiting for the topic to be opened");
                (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(postPage.getFirstPost()));
                found = true;
                break;
            }
        }
        return found;
    }

    // Getters

    public WebElement getSubscribeButton() {
        return subscribeButton;
    }

    public WebElement getBranchName() {
        return branchName;
    }

    public List<WebElement> getActiveTopicsButton() {
        return activeTopicsButton;
    }

    public List<WebElement> getTopicsButtons() {
        return topicsButtons;
    }

    public WebElement getNewTopicToggle() {
        return newTopicTypeToggle;
    }

    public WebElement getNewOrdinaryTopicButton() {
        return newOrdinaryTopicButton;
    }

    public WebElement getNewCodeReviewButton() {
        return newCodeReviewButton;
    }

    public List<WebElement> getTopicsList() {
        return topicsList;
    }
}
