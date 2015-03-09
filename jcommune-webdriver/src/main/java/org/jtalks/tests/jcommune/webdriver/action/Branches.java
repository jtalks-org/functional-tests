package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import ru.yandex.qatools.allure.annotations.Step;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.*;

import static org.testng.Assert.assertTrue;

/**
 * @author stanislav bashkirtsev
 * @author andrey ivanov
 * @author pancheshenko andrey
 */
public class Branches {
    @Step
    public static void openBranch(Branch branch) throws CouldNotOpenPageException {
        if (!branchPage.isUserInsideCorrectBranch(branch.getTitle())) {
            info("User is not browsing the required branch. Opening it from the main page.");
            if (!JCommuneSeleniumConfig.driver.getCurrentUrl().equals(JCommuneSeleniumConfig.getAppUrl())) {
                gotoMainPage();
            }
            gotoBranch(branch.getTitle());
        } else {
            if (JCommuneSeleniumConfig.driver.getCurrentUrl().contains("/topics/")) {
                info("User is inside some topic at the required branch. Opening branch by link in the breadcrumbs.");
                postPage.returnToBranchPage();
            }
        }
    }

    @Step
    public static void subscribe(Branch branch) throws NoSuchElementException {
        openBranch(branch);
        try {
            if (branchPage.isUserAlreadySubscribed()) {
                info("User already subscribed on the branch");
            } else {
                info("Clicking subscribe button");
                branchPage.getSubscribeButton().click();
            }
        } catch (NoSuchElementException e) {
            info("Subscribe button was not found");
            throw new NoSuchElementException("Subscribe button was not found", e);
        }
    }

    @Step
    public static void unsubscribe(Branch branch) throws NoSuchElementException {
        openBranch(branch);
        try {
            if (branchPage.isUserAlreadySubscribed()) {
                info("User is subscribed on the branch. Clicking unsubscribe button");
                branchPage.getSubscribeButton().click();
            } else {
                info("User isn't subscribed on the branch");
            }
        } catch (NoSuchElementException e) {
            info("Subscribe button was not found");
            throw new NoSuchElementException("Subscribe button was not found", e);
        }
    }

    /**
     * This method is created for cleaning up the consequences of branch subscription.
     * This is a "sanitary" method that wouldn't affect the overall test result in case the method fails.
     *
     * @param branch
     */
    public static void unsubscribeIgnoringFail(Branch branch) {
        try {
            unsubscribe(branch);
        } catch (NoSuchElementException e) {
            info("Branch unsubscribe NoSuchElementException: " + e);
        } catch (CouldNotOpenPageException e) {
            info("Branch unsubscribe CouldNotOpenPageException: " + e);
        }
    }

    public static void gotoMainPage() {
        mainPage.clickForumsTitle();
    }

    private static void gotoBranch(String branchTitle) throws CouldNotOpenPageException {
        info("Start looking for branch: [" + branchTitle + "] in the main page");
        WebElement branch = sectionPage.findBranch(branchTitle);
        if (branch != null) {
            info("The branch was found, clicking on it..");
            branch.click();
        } else {
            info(String.format("No branch found with name [%s]", branchTitle));
            info(String.format("There were these branches available: %s", Branch.fromForm(sectionPage.getBranches())));
            throw new CouldNotOpenPageException(branchTitle);
        }
    }

    public static Branch userIsViewingRandomBranch() {
        WebElement randomBranch = sectionPage.getBranches().get(0);
        randomBranch.click();
        return Branch.fromForm(randomBranch);
    }

    public static void clickMarkAllAsRead() {
        throw new UnsupportedOperationException("This is just stub method. Implementation will be created in the future");
    }

    public static void assertUserBrowsesBranch(Branch branch) {
        assertTrue(JCommuneSeleniumConfig.driver.getCurrentUrl().endsWith("/branches/" + branch.getId()));
    }

    public static void assertBranchIsVisible(String branchTitle) {
        info("Begin searching for [" + branchTitle + "] branch");
        assertNotNull(sectionPage.findBranch(branchTitle), "Branch was not found");
        info("Success. Branch was found");
    }

    public static void assertBranchIsInvisible(String branchTitle) {
        info("Begin searching for \"" + branchTitle + "\" branch");
        assertNull(sectionPage.findBranch(branchTitle), "Branch is visible");
        info("Success. Branch wasn't found");
    }
}
