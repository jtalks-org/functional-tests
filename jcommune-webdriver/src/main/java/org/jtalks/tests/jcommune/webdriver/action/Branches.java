package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.openqa.selenium.WebElement;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.testng.Assert.assertTrue;

/**
 * @author stanislav bashkirtsev
 * @author andrey ivanov
 */
public class Branches {
    public static void openBranch(String branchTitle) throws CouldNotOpenPageException {
        info("Opening branch: [" + branchTitle + "] to open");
        WebElement branch = branchPage.findBranch(branchTitle);
        if (branch != null) {
            info("The branch was found, clicking on it..");
            branch.click();
        } else {
            info(String.format("No branch found with name [%s]", branchTitle));
            info(String.format("There were these branches available: %s", Branch.fromForm(branchPage.getBranches())));
            throw new CouldNotOpenPageException(branchTitle);
        }
    }

    public static void clickMarkAllAsRead() {
        throw new UnsupportedOperationException("This is just stub method. Implementation will be created in the future");
    }

    public static void subscribe(Branch branch, User user) {
        throw new UnsupportedOperationException("To be implemented");
    }

    public static Branch userIsViewingRandomBranch() {
        WebElement randomBranch = branchPage.getBranches().get(0);
        randomBranch.click();
        return Branch.fromForm(randomBranch);
    }

    public static void assertUserBrowsersBranch(Branch branch) {
        assertTrue(JCommuneSeleniumConfig.driver.getCurrentUrl().endsWith("/branches/" + branch.getId()));
    }
}
