package org.jtalks.tests.jcommune;

import org.apache.commons.lang3.StringUtils;
import org.jtalks.tests.jcommune.utils.DriverMethodHelp;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.testng.annotations.*;

import static org.apache.commons.lang.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;
import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

public class BbCodeTest {

    @BeforeClass
    @Parameters({"appUrl"})
    public void signInOnlyOnce_becauseTheseTestsDoNotRequireDataIsolation(String appUrl) throws Exception {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
        Users.signUpAndSignIn();
    }

    /**
     * Previous test could've been failed on the topic page open with field data. If that's the case, then when
     * next test will try to open another page, browser will ask whether user is sure to leave the current page and the
     * test will fail. Therefore we've added this alert
     */
    @BeforeMethod
    @Parameters({"appUrl"})
    public void clickLeaveThePageIfPreviousTestFailed(String appUrl) {
        driver.get(appUrl);
        DriverMethodHelp.closeAlertIfExists(driver);
    }

    @Test(dataProvider = "bbCodesWithMessage_thatShouldPass")
    public void bbCodesWithTextThatShouldPass(String topicBody, String messageIfTestFails) throws Exception {
        info("Running a test case [" + messageIfTestFails + "]");
        Topic topic = new Topic(topicTitleWithTestCaseName(messageIfTestFails), topicBody);
        Topic createdTopic = Topics.createTopic(topic);
        assertTrue(Topics.isCreated(createdTopic), messageIfTestFails);
    }

    @Test(dataProvider = "bbCodesMessage_thatShouldFail")
    public void bbCodesWithTextThatShouldFail(String topicBody, String messageIfTestFails) throws Exception {
        info("Running a test case [" + messageIfTestFails + "]");
        Topic topic = new Topic(topicTitleWithTestCaseName(messageIfTestFails), topicBody);
        try {
            Topics.createTopic(topic);//show throw error if validation failed
            fail(messageIfTestFails);
        } catch (ValidationException e) {
            //if validation error happened, then the test passed
        }
    }

    @DataProvider
    public Object[][] bbCodesWithMessage_thatShouldPass() {
        String minBoundaryMessage = "BB codes that are not visible to users do not count " +
                "during length validation, therefore 2 symbols is the lower boundary for the topic body";
        String averageBodySizeMessage = "A valid text of usual sizes (10 symbols)";
        String maxBoundaryMessage = "Max boundary 20000 symbols. Since bb codes are stored in " +
                "DB as usual symbols, they are calculated during max size validation of topic body.";
        String openBbCodeShouldNotFailMessage = "Open BB code tag is visible to user and therefore is calculated during" +
                " message size validation. Since open BB code is more than 2 symbols, the validation should pass";
        String closingBbCodeShouldNotFailMessage = "Closing BB code tag is visible to user and therefore is calculated " +
                "during message size validation. Since closing BB code is more than 2 symbols, the validation should pass";
        String wrongLinkInsideBbCodesTagsMessage = "Wrong link inside BB code tags" +
                "Since open BB code is more than 2 symbols, the validation should pass";

        return new String[][]{
                {"[b]" + randomAlphanumeric(2) + "[/b]", "BB code [b] validation failed! " + minBoundaryMessage},
                {"[b]" + randomAlphanumeric(10) + "[/b]", "BB code [b] validation failed! " + averageBodySizeMessage},
                {"[b]" + randomAlphanumeric(19993) + "[/b]", "BB code [b] validation failed! " + maxBoundaryMessage},
                {"[b]", "BB code [b] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/b]", "BB code close [/b] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[i]" + randomAlphanumeric(2) + "[/i]", "BB code [i] validation failed! " + minBoundaryMessage},
                {"[i]" + randomAlphanumeric(10) + "[/i]", "BB code [i] validation failed! " + averageBodySizeMessage},
                {"[i]" + randomAlphanumeric(19993) + "[/i]", "BB code [i] validation failed! " + maxBoundaryMessage},
                {"[i]", "BB code [i] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/i]", "BB code close [/i] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[s]" + randomAlphanumeric(2) + "[/s]", "BB code [s] validation failed! " + minBoundaryMessage},
                {"[s]" + randomAlphanumeric(10) + "[/s]", "BB code [s] validation failed! " + averageBodySizeMessage},
                {"[s]" + randomAlphanumeric(19993) + "[/s]", "BB code [s] validation failed! " + maxBoundaryMessage},
                {"[s]", "BB code [s] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/s]", "BB code close [/s] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[u]" + randomAlphanumeric(2) + "[/u]", "BB code [u] validation failed! " + minBoundaryMessage},
                {"[u]" + randomAlphanumeric(10) + "[/u]", "BB code [u] validation failed! " + averageBodySizeMessage},
                {"[u]" + randomAlphanumeric(19993) + "[/u]", "BB code [u] validation failed! " + maxBoundaryMessage},
                {"[u]", "BB code [u] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/u]", "BB code close [/u] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[highlight]" + randomAlphanumeric(2) + "[/highlight]", "BB code [highlight] validation failed! " + minBoundaryMessage},
                {"[highlight]" + randomAlphanumeric(10) + "[/highlight]", "BB code [highlight] validation failed! " + averageBodySizeMessage},
                {"[highlight]" + randomAlphanumeric(19977) + "[/highlight]", "BB code [highlight] validation failed! " + maxBoundaryMessage},
                {"[highlight]", "BB code [highlight] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/highlight]", "BB code close [/highlight] tag validation failed! " + closingBbCodeShouldNotFailMessage},

                {"[left]" + randomAlphanumeric(2) + "[/left]", "BB code [left] validation failed! " + minBoundaryMessage},
                {"[left]" + randomAlphanumeric(10) + "[/left]", "BB code [left] validation failed! " + averageBodySizeMessage},
                {"[left]" + randomAlphanumeric(19987) + "[/left]", "BB code [left] validation failed! " + maxBoundaryMessage},
                {"[left]", "BB code [left] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/left]", "BB code close [/left] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[center]" + randomAlphanumeric(2) + "[/center]", "BB code [center] validation failed! " + minBoundaryMessage},
                {"[center]" + randomAlphanumeric(10) + "[/center]", "BB code [center] validation failed! " + averageBodySizeMessage},
                {"[center]" + randomAlphanumeric(19983) + "[/center]", "BB code [center] validation failed! " + maxBoundaryMessage},
                {"[center]", "BB code [center] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/center]", "BB code close [/center] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[right]" + randomAlphanumeric(2) + "[/right]", "BB code [right] validation failed! " + minBoundaryMessage},
                {"[right]" + randomAlphanumeric(10) + "[/right]", "BB code [right] validation failed! " + averageBodySizeMessage},
                {"[right]" + randomAlphanumeric(19985) + "[/right]", "BB code [right] validation failed! " + maxBoundaryMessage},
                {"[right]", "BB code [right] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/right]", "BB code close [/right] tag validation failed! " + closingBbCodeShouldNotFailMessage},

                {"[list][*]" + randomAlphanumeric(2) + "[/list]", "BB code [list] validation failed! " + minBoundaryMessage},
                {"[list][*]" + randomAlphanumeric(10) + "[/list]", "BB code [list] validation failed! " + averageBodySizeMessage},
                {"[list][*]" + randomAlphanumeric(19984) + "[/list]", "BB code [list] validation failed! " + maxBoundaryMessage},
                {"[list][*]", "BB code [list] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/list]", "BB code close [/list] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[*]", "BB code add list item [*] tag validation failed! This symbols is visible since it's not a part of real BB code"},

                {"[color=000099]" + randomAlphanumeric(2) + "[/color]", "BB code [color] validation failed! " + minBoundaryMessage},
                {"[color=000099]" + randomAlphanumeric(10) + "[/color]", "BB code [color] validation failed! " + averageBodySizeMessage},
                {"[color=000099]" + randomAlphanumeric(19978) + "[/color]", "BB code [color] validation failed! " + maxBoundaryMessage},
                {"[color=000099]", "BB code [color] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/color]", "BB code close [/color] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[size=7]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation failed! " + minBoundaryMessage},
                {"[size=7]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation failed! " + averageBodySizeMessage},
                {"[size=7]" + randomAlphanumeric(19985) + "[/size]", "BB code [size] validation failed! " + maxBoundaryMessage},
                {"[size=7]", "BB code [size] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/size]", "BB code close [/size] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[size=9]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation failed! " + minBoundaryMessage},
                {"[size=9]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation failed! " + averageBodySizeMessage},
                {"[size=9]", "BB code [right] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[size=12]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation failed! " + minBoundaryMessage},
                {"[size=12]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation failed! " + averageBodySizeMessage},
                {"[size=12]" + randomAlphanumeric(19984) + "[/size]", "BB code [size] validation failed! " + maxBoundaryMessage},
                {"[size=12]", "BB code [size] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[size=18]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation failed! " + minBoundaryMessage},
                {"[size=18]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation failed! " + averageBodySizeMessage},
                {"[size=18]", "BB code [size] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[size=24]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation failed! " + minBoundaryMessage},
                {"[size=24]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation failed! " + averageBodySizeMessage},
                {"[size=24]", "BB code [size] validation failed! " + openBbCodeShouldNotFailMessage},

                {"[img]" + randomAlphanumeric(2) + "[/img]", "BB code [img] validation failed! " + wrongLinkInsideBbCodesTagsMessage},
                {"[img]" + randomAlphanumeric(10) + "[/img]", "BB code [img] validation failed! " + wrongLinkInsideBbCodesTagsMessage},
                {"[img]" + randomAlphanumeric(19987) + "[/img]", "BB code [img] validation failed! " + wrongLinkInsideBbCodesTagsMessage},
                {"[img]", "BB code [img] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/img]", "BB code close [/img] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[url=new link]" + randomAlphanumeric(2) + "[/url]", "BB code [url] validation failed! " + wrongLinkInsideBbCodesTagsMessage},
                {"[url=new link]" + randomAlphanumeric(10) + "[/url]", "BB code [url] validation failed! " + wrongLinkInsideBbCodesTagsMessage},
                {"[url=new link]" + randomAlphanumeric(19980) + "[/url]", "BB code [url] validation failed! " + wrongLinkInsideBbCodesTagsMessage},
                {"[url=new link]", "BB code [url] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/url]", "BB code close [/url] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[code=cpp]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation failed! " + minBoundaryMessage},
                {"[code=cpp]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=cpp]" + randomAlphanumeric(19983) + "[/code]", "BB code [code] validation failed! " + maxBoundaryMessage},
                {"[code=cpp]", "BB code [code] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/code]", "BB code close [/code] tag validation failed! " + closingBbCodeShouldNotFailMessage},
//                since we don't want to duplicate tests on the same classes of data, we check only that next languages are supported
                {"[code=csharp]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=java]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=php]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=python]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=pascal]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=bash]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=js]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=html]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=css]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=sql]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},
                {"[code=xml]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation failed! " + averageBodySizeMessage},

                {"[quote]" + randomAlphanumeric(2) + "[/quote]", "BB code [quote] validation failed! " + minBoundaryMessage},
                {"[quote]" + randomAlphanumeric(10) + "[/quote]", "BB code [quote] validation failed! " + averageBodySizeMessage},
                {"[quote]" + randomAlphanumeric(19985) + "[/quote]", "BB code [quote] validation failed! " + maxBoundaryMessage},
                {"[quote]", "BB code [quote] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/quote]", "BB code close [/quote] tag validation failed! " + closingBbCodeShouldNotFailMessage},

                {"[indent=15]" + randomAlphanumeric(2) + "[/indent]", "BB code [indent] validation failed! " + minBoundaryMessage},
                {"[indent=15]" + randomAlphanumeric(10) + "[/indent]", "BB code [indent] validation failed! " + averageBodySizeMessage},
                {"[indent=15]" + randomAlphanumeric(19980) + "[/indent]", "BB code [indent] validation failed! " + maxBoundaryMessage},
                {"[indent=15]", "BB code [indent] validation failed! " + openBbCodeShouldNotFailMessage},
                {"[/indent]", "BB code close [/indent] tag validation failed! " + closingBbCodeShouldNotFailMessage},
                {"[indent=20]" + randomAlphanumeric(10) + "[/indent]", "BB code [indent] validation failed! " + averageBodySizeMessage},
                {"[indent=25]" + randomAlphanumeric(10) + "[/indent]", "BB code [indent] validation failed! " + averageBodySizeMessage},
        };
    }

    @DataProvider
    public Object[][] bbCodesMessage_thatShouldFail() {
        String emptyBbCodeMessage = "BB codes that are not visible to users do not count " +
                "during length validation, empty BB code results in empty message which is lower than min boundary";
        String singleVisibleCharacterIsLowerThanMinBoundaryMessage = "BB codes that are not visible to users do not " +
                "count. 1 symbol is lower than min boundary (2) and therefore validation should fail.";
        return new String[][]{
                {"[b][/b]", "BB code [b] validation did NOT fail. " + emptyBbCodeMessage},
                {"[b]a[/b]", "BB code [b] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[i][/i]", "BB code [i] validation did NOT fail. " + emptyBbCodeMessage},
                {"[i]a[/i]", "BB code [b] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[u][/u]", "BB code [u] validation did NOT fail. " + emptyBbCodeMessage},
                {"[u]a[/u]", "BB code [u] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[s][/s]", "BB code [s] validation did NOT fail. " + emptyBbCodeMessage},
                {"[s]a[/s]", "BB code [s] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[highlight][/highlight]", "BB code [highlight] validation did NOT fail. " + emptyBbCodeMessage},
                {"[highlight]a[/highlight]", "BB code [highlight] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[left][/left]", "BB code [left] validation did NOT fail. " + emptyBbCodeMessage},
                {"[left]a[/left]", "BB code [left] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[center][/center]", "BB code [center] validation did NOT fail. " + emptyBbCodeMessage},
                {"[center]a[/center]", "BB code [center] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[right][/right]", "BB code [right] validation did NOT fail. " + emptyBbCodeMessage},
                {"[right]a[/right]", "BB code [right] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[list][*][/list]", "BB code [list] validation did NOT fail. " + emptyBbCodeMessage},

                {"[color=33CCCC][/color]", "BB code [color] validation did NOT fail. " + emptyBbCodeMessage},
                {"[color=33CCCC]q[/color]", "BB code [color] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[size=7][/size]", "BB code [size] validation did NOT fail. " + emptyBbCodeMessage},
                {"[size=7]a[/size]", "BB code [size] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[size=9][/size]", "BB code [size] validation did NOT fail. " + emptyBbCodeMessage},
                {"[size=9]a[/size]", "BB code [size] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[size=12][/size]", "BB code [size] validation did NOT fail. " + emptyBbCodeMessage},
                {"[size=12]a[/size]", "BB code [size] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[size=18][/size]", "BB code [size] validation did NOT fail. " + emptyBbCodeMessage},
                {"[size=18]a[/size]", "BB code [size] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[size=24][/size]", "BB code [size] validation did NOT fail. " + emptyBbCodeMessage},
                {"[size=24]a[/size]", "BB code [size] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[code=cpp][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=cpp]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=csharp][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=csharp]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=java][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=java]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=php][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=php]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=python][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=python]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=pascal][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=pascal]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=bash][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=bash]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=js][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=js]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=html][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=html]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=css][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=css]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=sql][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=sql]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[code=xml][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
                {"[code=xml]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[quote][/quote]", "BB code [quote] validation did NOT fail. " + emptyBbCodeMessage},
                {"[quote]a[/quote]", "BB code [quote] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

                {"[indent=15][/indent]", "BB code [indent] validation did NOT fail. " + emptyBbCodeMessage},
                {"[indent=15]a[/indent]", "BB code [indent] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[indent=20][/indent]", "BB code [indent] validation did NOT fail. " + emptyBbCodeMessage},
                {"[indent=20]a[/indent]", "BB code [indent] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
                {"[indent=25][/indent]", "BB code [indent] validation did NOT fail. " + emptyBbCodeMessage},
                {"[indent=25]a[/indent]", "BB code [indent] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},

        };

    }

    /**
     * Adds some useful inf to the title (test case explanation) and adds a random string so that text doesn't repeat.
     *
     * @param testcaseName the test case description
     * @return topic title with random part and useful part in it
     */
    private String topicTitleWithTestCaseName(String testcaseName) {
        return StringUtils.left(testcaseName, 70) + "... " + randomAlphabetic(20);
    }

}
