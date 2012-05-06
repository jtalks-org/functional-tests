package org.jtalks.tests.jcommune.tests.topic;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;


/**
 * author: erik
 */
public class JC115PaginationForUnregisteredUser {
    String topicUrl;

    @BeforeMethod
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setUp(String appUrl, String userName, String password) {
        driver.get(appUrl);
        signIn(userName, password);
        clickOnRandomSection();
        clickOnRandomBranch();
        createTopicForTest();
        topicUrl = driver.getCurrentUrl();

        createPostsForTest(50, 250);
        logOut(appUrl);

    }

    @Test
    public void testPagination() {
        driver.get(topicUrl);
        Assert.assertEquals(postPage.getPostsList().size(), 50);
        assertionExistBySelector(driver, "//*[@class='page']");
        driver.findElement(By.xpath("//a[@class='page' and contains(text(), '2')]")).click();
        Assert.assertTrue(postPage.getPostsList().size() > 0);
    }
}
