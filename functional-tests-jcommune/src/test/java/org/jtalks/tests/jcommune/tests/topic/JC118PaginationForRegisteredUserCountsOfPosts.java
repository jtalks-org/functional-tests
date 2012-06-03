package org.jtalks.tests.jcommune.tests.topic;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static org.jtalks.tests.jcommune.assertion.Exsistence.assertionExistBySelector;
import static org.jtalks.tests.jcommune.common.JCommuneSeleniumTest.*;

/**
 * author: erik
 */
public class JC118PaginationForRegisteredUserCountsOfPosts {
    Boolean wasValueChanged = false;
    String value;
    String topicLink;

    @BeforeMethod
    @Parameters({"app-url", "uUsername", "uPassword"})
    public void setUp(String appUrl, String username, String password) {
        driver.get(appUrl);
        signIn(username, password);
        mainPage.getProfileLink().click();

        //here we will change count of posts for user in profile if it not equals 5
        if (!driver.findElement(By.xpath("//li[@class='forum_row' and label='Page size']/span")).getText().equals("5")) {
            wasValueChanged = true;
            profilePage.getEditProfileButton().click();
            value = driver.findElement(By.xpath("//li[@class='forum_row' and label= 'Page size']//select")).getAttribute("value");
            Select select = new Select(driver.findElement(By.xpath("//li[@class='forum_row' and label= 'Page size']//select")));
            select.selectByValue("5");
            profilePage.getSaveEditButton().click();
        }

        driver.get(appUrl);
        clickOnRandomSection();
        clickOnRandomBranch();
        createTopicForTest();
        topicLink = driver.getCurrentUrl();
        createPostsForTest(21, 50);
    }

    @AfterMethod
    @Parameters({"app-url", "uUsername"})
    public void back(String appUrl, String username) {

        //here we will change count of posts for user in profile how it was before test
        if (wasValueChanged) {
            driver.get(appUrl);
            mainPage.getProfileLink().click();
            profilePage.getEditProfileButton().click();
            Select select = new Select(driver.findElement(By.xpath("//li[@class='forum_row' and label= 'Page size']//select")));
            select.selectByValue(value);
            profilePage.getSaveEditButton().click();
        }

        logOut(appUrl);
    }

    @Test
    public void paginationTest() {
        driver.get(topicLink);
        if (postPage.getPostsMessages().size() != 5) {
            Assert.fail("Post page contains count of posts that not equals 5");
        }

        assertionExistBySelector(driver, "//*[@class='nav_top']");
        assertionExistBySelector(driver, "//*[@class='nav_bottom']");

        //change count of posts to 10
        topicPage.getProfileLink().click();
        profilePage.getEditProfileButton().click();
        Select select = new Select(driver.findElement(By.xpath("//li[@class='forum_row' and label= 'Page size']//select")));
        select.selectByValue("10");
        profilePage.getSaveEditButton().click();

        driver.get(topicLink);
        if (postPage.getPostsMessages().size() != 10) {
            Assert.fail("Post page contains count of posts that not equals 5");
        }

        postPage.getPagesButtons().get(1).click();
        if (postPage.getPostsMessages().size() != 10) {
            Assert.fail("Post page contains count of posts that not equals 5");
        }

        //change count of posts to 10
        topicPage.getProfileLink().click();
        profilePage.getEditProfileButton().click();
        select = new Select(driver.findElement(By.xpath("//li[@class='forum_row' and label= 'Page size']//select")));
        select.selectByValue("5");
        profilePage.getSaveEditButton().click();

    }

}
