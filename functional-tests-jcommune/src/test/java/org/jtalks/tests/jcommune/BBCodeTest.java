package org.jtalks.tests.jcommune;

import org.apache.commons.lang.RandomStringUtils;
import org.jtalks.tests.jcommune.webdriver.action.Topics;
import org.jtalks.tests.jcommune.webdriver.action.Users;
import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.exceptions.ValidationException;
import org.jtalks.tests.jcommune.webdriver.page.TopicPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.mainPage;

import static org.apache.commons.lang.RandomStringUtils.randomAlphanumeric;

/**
 * Created by PC on 09.02.14.
 */


public class BBCodeTest {
    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test(dataProvider = "bbCodesWithMessagethatShouldPass")
        public void  bbCodesWithTextThatShouldPass(String testString, String messageIfTEstFails) throws Exception {
            Users.signUpAndSignIn();
            Topic topic = new Topic(randomAlphanumeric(10), testString);
            Topic createdTopic = Topic.createTopic(topic);
            Assert.assertTrue(Topics.isCreated(createdTopic));
    }

    @Test(dataProvider = "bbCodesWithMessagethatShouldFail")
    public void  bbCodesWithTextThatShouldFail(String testString, String messageIfTEstFails) throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic(randomAlphanumeric(10), testString);
        Topic createdTopic = Topic.createTopic(topic);
        Assert.assertTrue(Topics.isCreated(createdTopic));
    }

    @DataProvider
    public Object[][]bbCodesWithMessagethatShouldPass(){
        String minBoundaryMessage = "BB codes that are not visible to users do not count " +
                "during length validation, therefore 2 symbols is the lower boundary for the topic body";
        String averageBodySizeMessage = "A valid text of usual sizes (10 symbols)";
        String maxBoundaryMessage = "Max boundary 20000 symbols. Since bb codes are stored in " +
                "DB as usual symbols, they are calculated during max size validation of topic body.";
        String openBbCodeShouldNotFailMessage = "Open BB code tag is visible to user and therefore is calculated during" +
                " message size validation. Since open BB code is more than 2 symbols, the validation should pass";
        String wrongLinkInsideBbCodesTagsMessage = "Wrong link inside BB code tags" +
                "Since open BB code is more than 2 symbols, the validation should pass";

        return new String[][]{
            {"[b]" + randomAlphanumeric(2) + "[/b]", "BB code [b] validation passed. " + minBoundaryMessage},
            {"[b]" + randomAlphanumeric(10) + "[/b]", "BB code [b] validation passed. " + averageBodySizeMessage},
            {"[b]" + randomAlphanumeric(20000) + "[/b]", "BB code [b] validation passed. " + maxBoundaryMessage},
            {"[b]", "BB code [b] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/b]", "BB code close [/b] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[i]" + randomAlphanumeric(2) + "[/i]", "BB code [i] validation passed. " + minBoundaryMessage},
            {"[i]" + randomAlphanumeric(10) + "[/i]", "BB code [i] validation passed. " + averageBodySizeMessage},
            {"[i]" + randomAlphanumeric(20000) + "[/i]", "BB code [i] validation passed. " + maxBoundaryMessage},
            {"[i]", "BB code [i] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/i]", "BB code close [/i] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[s]" + randomAlphanumeric(2) + "[/s]", "BB code [s] validation passed. " + minBoundaryMessage},
            {"[s]" + randomAlphanumeric(10) + "[/s]", "BB code [s] validation passed. " + averageBodySizeMessage},
            {"[s]" + randomAlphanumeric(20000) + "[/s]", "BB code [s] validation passed. " + maxBoundaryMessage},
            {"[s]", "BB code [s] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/s]", "BB code close [/s] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[u]" + randomAlphanumeric(2) + "[/u]", "BB code [u] validation passed. " + minBoundaryMessage},
            {"[u]" + randomAlphanumeric(10) + "[/u]", "BB code [u] validation passed. " + averageBodySizeMessage},
            {"[u]" + randomAlphanumeric(20000) + "[/u]", "BB code [u] validation passed. " + maxBoundaryMessage},
            {"[u]", "BB code [u] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/u]", "BB code close [/u] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[highlight]" + randomAlphanumeric(2) + "[/highlight]", "BB code [highlight] validation passed. " + minBoundaryMessage},
            {"[highlight]" + randomAlphanumeric(10) + "[/highlight]", "BB code [highlight] validation passed. " + averageBodySizeMessage},
            {"[highlight]" + randomAlphanumeric(20000) + "[/highlight]", "BB code [highlight] validation passed. " + maxBoundaryMessage},
            {"[highlight]", "BB code [highlight] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/highlight]", "BB code close [/highlight] tag validation passed. " + openBbCodeShouldNotFailMessage},

            {"[left]" + randomAlphanumeric(2) + "[/left]", "BB code [left] validation passed. " + minBoundaryMessage},
            {"[left]" + randomAlphanumeric(10) + "[/left]", "BB code [left] validation passed. " + averageBodySizeMessage},
            {"[left]" + randomAlphanumeric(20000) + "[/left]", "BB code [left] validation passed. " + maxBoundaryMessage},
            {"[left]", "BB code [left] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/left]", "BB code close [/left] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[center]" + randomAlphanumeric(2) + "[/center]", "BB code [center] validation passed. " + minBoundaryMessage},
            {"[center]" + randomAlphanumeric(10) + "[/center]", "BB code [center] validation passed. " + averageBodySizeMessage},
            {"[center]" + randomAlphanumeric(20000) + "[/center]", "BB code [center] validation passed. " + maxBoundaryMessage},
            {"[center]", "BB code [center] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/center]", "BB code close [/center] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[right]" + randomAlphanumeric(2) + "[/right]", "BB code [right] validation passed. " + minBoundaryMessage},
            {"[right]" + randomAlphanumeric(10) + "[/right]", "BB code [right] validation passed. " + averageBodySizeMessage},
            {"[right]" + randomAlphanumeric(20000) + "[/right]", "BB code [right] validation passed. " + maxBoundaryMessage},
            {"[right]", "BB code [right] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/right]", "BB code close [/right] tag validation passed. " + openBbCodeShouldNotFailMessage},

            {"[list][*]" + randomAlphanumeric(2) + "[/list]", "BB code [list] validation passed. " + minBoundaryMessage},
            {"[list][*]" + randomAlphanumeric(10) + "[/list]", "BB code [list] validation passed. " + averageBodySizeMessage},
            {"[list][*]" + randomAlphanumeric(20000) + "[/list]", "BB code [list] validation passed. " + maxBoundaryMessage},
            {"[list][*]", "BB code [list] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/list]", "BB code close [/list] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[*]", "BB code add list item [*] tag validation passed. " + openBbCodeShouldNotFailMessage},

            {"[color=000099]" + randomAlphanumeric(2) + "[/color]", "BB code [color] validation passed. " + minBoundaryMessage},
            {"[color=000099]" + randomAlphanumeric(10) + "[/color]", "BB code [color] validation passed. " + averageBodySizeMessage},
            {"[color=000099]" + randomAlphanumeric(20000) + "[/color]", "BB code [color] validation passed. " + maxBoundaryMessage},
            {"[color=000099]", "BB code [color] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/color]", "BB code close [/color] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[size=7]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation passed. " + minBoundaryMessage},
            {"[size=7]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation passed. " + averageBodySizeMessage},
            {"[size=7]" + randomAlphanumeric(20000) + "[/size]", "BB code [size] validation passed. " + maxBoundaryMessage},
            {"[size=7]", "BB code [size] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/size]", "BB code close [/size] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[size=9]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation passed. " + minBoundaryMessage},
            {"[size=9]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation passed. " + averageBodySizeMessage},
            {"[size=9]" + randomAlphanumeric(20000) + "[/size]", "BB code [size] validation passed. " + maxBoundaryMessage},
            {"[size=9]", "BB code [right] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[size=12]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation passed. " + minBoundaryMessage},
            {"[size=12]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation passed. " + averageBodySizeMessage},
            {"[size=12]" + randomAlphanumeric(20000) + "[/size]", "BB code [size] validation passed. " + maxBoundaryMessage},
            {"[size=12]", "BB code [size] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[size=18]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation passed. " + minBoundaryMessage},
            {"[size=18]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation passed. " + averageBodySizeMessage},
            {"[size=18]" + randomAlphanumeric(20000) + "[/size]", "BB code [size] validation passed. " + maxBoundaryMessage},
            {"[size=18]", "BB code [size] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[size=24]" + randomAlphanumeric(2) + "[/size]", "BB code [size] validation passed. " + minBoundaryMessage},
            {"[size=24]" + randomAlphanumeric(10) + "[/size]", "BB code [size] validation passed. " + averageBodySizeMessage},
            {"[size=24]" + randomAlphanumeric(20000) + "[/size]", "BB code [size] validation passed. " + maxBoundaryMessage},
            {"[size=24]", "BB code [size] validation passed. " + openBbCodeShouldNotFailMessage},

            {"[img]" + randomAlphanumeric(2) + "[/img]", "BB code [img] validation passed. " + wrongLinkInsideBbCodesTagsMessage},
            {"[img]" + randomAlphanumeric(10) + "[/img]", "BB code [img] validation passed. " + wrongLinkInsideBbCodesTagsMessage},
            {"[img]" + randomAlphanumeric(20000) + "[/img]", "BB code [img] validation passed. " + wrongLinkInsideBbCodesTagsMessage},
            {"[img]", "BB code [img] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/img]", "BB code close [/img] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[url=new link]" + randomAlphanumeric(2) + "[/url]", "BB code [url] validation passed. " + wrongLinkInsideBbCodesTagsMessage},
            {"[url=new link]" + randomAlphanumeric(10) + "[/url]", "BB code [url] validation passed. " + wrongLinkInsideBbCodesTagsMessage},
            {"[url=new link]" + randomAlphanumeric(20000) + "[/url]", "BB code [url] validation passed. " + wrongLinkInsideBbCodesTagsMessage},
            {"[url=new link]", "BB code [url] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/url]", "BB code close [/url] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=cpp]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=cpp]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=cpp]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=cpp]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/code]", "BB code close [/code] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=csharp]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=csharp]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=csharp]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=csharp]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=java]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=java]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=java]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=java]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=php]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=php]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=php]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=php]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=python]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=python]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=python]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=python]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=pascal]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=pascal]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=pascal]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=pascal]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=bash]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=bash]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=bash]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=bash]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=js]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=js]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=js]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=js]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=html]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=html]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=html]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=html]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=css]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=css]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=css]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=css]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=sql]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=sql]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=sql]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=sql]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[code=xml]" + randomAlphanumeric(2) + "[/code]", "BB code [code] validation passed. " + minBoundaryMessage},
            {"[code=xml]" + randomAlphanumeric(10) + "[/code]", "BB code [code] validation passed. " + averageBodySizeMessage},
            {"[code=xml]" + randomAlphanumeric(20000) + "[/code]", "BB code [code] validation passed. " + maxBoundaryMessage},
            {"[code=xml]", "BB code [code] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[quote]" + randomAlphanumeric(2) + "[/quote]", "BB code [quote] validation passed. " + minBoundaryMessage},
            {"[quote]" + randomAlphanumeric(10) + "[/quote]", "BB code [quote] validation passed. " + averageBodySizeMessage},
            {"[quote]" + randomAlphanumeric(20000) + "[/quote]", "BB code [quote] validation passed. " + maxBoundaryMessage},
            {"[quote]", "BB code [quote] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/quote]", "BB code close [/quote] tag validation passed. " + openBbCodeShouldNotFailMessage},

            {"[indent=15]" + randomAlphanumeric(2) + "[/indent]", "BB code [indent] validation passed. " + minBoundaryMessage},
            {"[indent=15]" + randomAlphanumeric(10) + "[/indent]", "BB code [indent] validation passed. " + averageBodySizeMessage},
            {"[indent=15]" + randomAlphanumeric(20000) + "[/qindent]", "BB code [indent] validation passed. " + maxBoundaryMessage},
            {"[indent=15]", "BB code [indent] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[/indent]", "BB code close [/indent] tag validation passed. " + openBbCodeShouldNotFailMessage},
            {"[indent=20]" + randomAlphanumeric(2) + "[/indent]", "BB code [indent] validation passed. " + minBoundaryMessage},
            {"[indent=20]" + randomAlphanumeric(10) + "[/indent]", "BB code [indent] validation passed. " + averageBodySizeMessage},
            {"[indent=20]" + randomAlphanumeric(20000) + "[/qindent]", "BB code [indent] validation passed. " + maxBoundaryMessage},
            {"[indent=20]", "BB code [indent] validation passed. " + openBbCodeShouldNotFailMessage},
            {"[indent=25]" + randomAlphanumeric(2) + "[/indent]", "BB code [indent] validation passed. " + minBoundaryMessage},
            {"[indent=25]" + randomAlphanumeric(10) + "[/indent]", "BB code [indent] validation passed. " + averageBodySizeMessage},
            {"[indent=25]" + randomAlphanumeric(20000) + "[/qindent]", "BB code [indent] validation passed. " + maxBoundaryMessage},
            {"[indent=25]", "BB code [indent] validation passed. " + openBbCodeShouldNotFailMessage},


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
            {"[size=python][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
            {"[size=python]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
            {"[code=pascal][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
            {"[code=pascal]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
            {"[code=bash][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
            {"[code=bash]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
            {"[code=js][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
            {"[code=js]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
            {"[code=html][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
            {"[code=html]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
            {"[size=css][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
            {"[size=css]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
            {"[code=sql][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
            {"[code=sql]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
            {"[size=sql][/code]", "BB code [code] validation did NOT fail. " + emptyBbCodeMessage},
            {"[size=sql]a[/code]", "BB code [code] validation did NOT fail. " + singleVisibleCharacterIsLowerThanMinBoundaryMessage},
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

}
