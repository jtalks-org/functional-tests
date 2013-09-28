package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.ExternalLinks;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.externallink.ExternalLink;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.*;

import static org.jtalks.tests.jcommune.utils.StringHelp.randomString;
import static org.jtalks.tests.jcommune.utils.StringHelp.randomUrl;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.action.ExternalLinks.exitAdminMode;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.testng.Assert.assertTrue;

/** @author stanislav bashkirtsev */
public class ExternalLinksTest {
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
    public void destroyCase() {
        exitAdminMode();
    }

    @Test
    public void externalLinkWithValidDataShouldBeCreated() throws Exception {
        ExternalLink externalLink = new ExternalLink()
                .withTitle(randomString(25))
                .withHref(randomUrl(50))
                .withHint(randomString(30));

        ExternalLinks.createExternalLink(externalLink);
        assertTrue(ExternalLinks.isVisibleOnMainPage(externalLink));

        ExternalLinks.removeExternalLink(externalLink);
    }

    @Test
    public void titleMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withTitle(randomString(30));

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void titleMinValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withTitle(randomString(1));

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void hrefMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHref(randomUrl(255));

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void emptyHrefShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHref("");

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void hintMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHint(randomString(128));

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void emptyHintShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHint("");

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test
    public void hintShouldBeTrimmed() {
        ExternalLink link = new ExternalLink().withHint("  ");

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link.withHint("")));

        ExternalLinks.removeExternalLink(link);
    }

    private static final Logger logger = LoggerFactory.getLogger(Users.class);

}
