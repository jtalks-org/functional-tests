package org.jtalks.tests.jcommune.webdriver.page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * @author Yuliya
 */
public class ForumSettingsDialog {

    public static final String WRONG_IMAGE_SIZE_ERROR = "File is too large. Maximum image size is 10,240 bytes.";

    public static final String WRONG_IMAGE_FORMAT_ERROR = "File has invalid extension. Only *.jpeg, *.jpg, *.gif, *.png, *.ico are allowed.";

    public static final String WRONG_TITLE_LENGTH_ERROR = "Please use value with length between 1 and 100";

    public static final String WRONG_FIELD_LENGTH_ERROR = "Please use value with length between 1 and 255";

    public ForumSettingsDialog(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}