package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author stanislav bashkirtsev
 * @author andrey ivanov
 */
public class Branches {
    private final static Logger LOGGER = LoggerFactory.getLogger(Branches.class);

    public static void openBranch(String branchTitle) throws CouldNotOpenPageException {
        boolean found = false;
        for (WebElement branch : branchPage.getBranchList()) {
            if (branch.getText().equals(branchTitle)) {
                branch.click();
                found = true;
                break;
            }
        }
        if (!found) {
            LOGGER.info("No branch found with name [{}]", branchTitle);
            throw new CouldNotOpenPageException(branchTitle);
        }
    }

    public static void clickMarkAllAsRead() {
        throw new UnsupportedOperationException("This is just stub method. Implementation will be created in the future");
    }

    public static void subscribe(Branch branch, User user) {
        throw new UnsupportedOperationException();
    }

    public static Branch userIsViewingRandomBranch() {
        branchPage.getBranchList().get(0).click();
        return new Branch().withId(1);
    }

    public static void assertUserBrowsersBranch(Branch branch) {
        assertTrue(JCommuneSeleniumConfig.driver.getCurrentUrl().endsWith("/branches/" + branch.getId()));
    }
}
