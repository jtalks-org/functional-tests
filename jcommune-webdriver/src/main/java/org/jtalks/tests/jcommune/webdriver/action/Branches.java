package org.jtalks.tests.jcommune.webdriver.action;

import org.jtalks.tests.jcommune.webdriver.exceptions.CouldNotOpenPageException;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.jtalks.tests.jcommune.webdriver.page.Pages.branchPage;

/** @author stanislav bashkirtsev */
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

}
