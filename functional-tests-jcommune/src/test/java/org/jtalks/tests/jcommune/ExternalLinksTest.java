package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.ExternalLinks;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.externallink.ExternalLink;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.utils.TestStringUtils.randomUrl;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.action.ExternalLinks.exitAdminMode;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

/**
 * @author stanislav bashkirtsev
 */
public class ExternalLinksTest {
    private static final Logger logger = LoggerFactory.getLogger(Users.class);

    @BeforeClass
    @Parameters({"appUrl"})
    public void signInAsAdmin(String appUrl) {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
        try {
            Users.signIn(User.admin());
        } catch (ValidationException e) {
            logger.error("Can't login by user [{}]", User.admin().getUsername());
        }
    }

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) {
        driver.get(appUrl);
    }

    @AfterMethod
    @Parameters({"appUrl"})
    public void destroyCase(String appUrl) {
        driver.get(appUrl);//in case some dialogs are not closed
        exitAdminMode();
    }

    @Test
    public void externalLinkWithValidDataShouldBeCreated() throws Exception {
        ExternalLink externalLink = new ExternalLink();

        ExternalLinks.createExternalLink(externalLink);
        ExternalLinks.assertLinkVisible(externalLink);

        ExternalLinks.removeExternalLink(externalLink);
    }

    @Test
    public void titleMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withTitle(randomAlphanumeric(30));

        ExternalLinks.createExternalLink(link);
        ExternalLinks.assertLinkVisible(link);

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void titleMinValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withTitle(randomAlphanumeric(1));

        ExternalLinks.createExternalLink(link);
        ExternalLinks.assertLinkVisible(link);

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void hrefMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHref(randomUrl(255));

        ExternalLinks.createExternalLink(link);
        ExternalLinks.assertLinkVisible(link);

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void emptyHrefShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHref("");

        //if href is empty, browser sets it to the current page instead of just empty URL
        ExternalLink expectedLink = ExternalLinks.createExternalLink(link);
        ExternalLinks.assertLinkVisible(expectedLink);

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void hintMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHint(randomAlphanumeric(128));

        ExternalLinks.createExternalLink(link);
        ExternalLinks.assertLinkVisible(link);

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void emptyHintShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHint("");

        ExternalLinks.createExternalLink(link);
        ExternalLinks.assertLinkVisible(link);

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void hintShouldBeTrimmed() {
        ExternalLink link = new ExternalLink().withHint("  ");

        ExternalLinks.createExternalLink(link);
        ExternalLinks.assertLinkVisible(link.withHint(""));

        ExternalLinks.removeExternalLink(link);
    }

}
