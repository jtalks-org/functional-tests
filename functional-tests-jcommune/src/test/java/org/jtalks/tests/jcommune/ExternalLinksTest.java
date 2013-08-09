package org.jtalks.tests.jcommune;

import org.jtalks.tests.jcommune.webdriver.action.ExternalLinks;
import org.jtalks.tests.jcommune.webdriver.entity.externallink.ExternalLink;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.utils.StringHelp.randomString;
import static org.testng.Assert.assertTrue;

/** @author stanislav bashkirtsev */
public class ExternalLinksTest {
    @BeforeClass
    public void signInAsAdmin() {
        //when web driver is implemented, here the respective logic will be placed
        //for the sake of speed and since there is no impact, we can use a single user
        //for all the tests
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void externalLinkWithValidDataShouldBeCreated() throws Exception {
        ExternalLink externalLink = new ExternalLink()
                .withTitle(randomString(25))
                .withHref("http://" + randomString(50))
                .withHint(randomString(30));

        ExternalLinks.createExternalLink(externalLink);
        assertTrue(ExternalLinks.isVisibleOnMainPage(externalLink));

        ExternalLinks.removeExternalLink(externalLink);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void titleMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withTitle(randomString(30));

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void titleMinValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withTitle(randomString(1));

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void hrefMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHref(randomString(256));

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void emptyEmptyShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHref("");

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void hintMaxValueShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHint(randomString(128));

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void emptyHintShouldPassValidation() {
        ExternalLink link = new ExternalLink().withHint("");

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link));

        ExternalLinks.removeExternalLink(link);
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void hintShouldBeTrimmed() {
        ExternalLink link = new ExternalLink().withHint("  ");

        ExternalLinks.createExternalLink(link);
        assertTrue(ExternalLinks.isVisibleOnMainPage(link.withHint("")));

        ExternalLinks.removeExternalLink(link);
    }

}
