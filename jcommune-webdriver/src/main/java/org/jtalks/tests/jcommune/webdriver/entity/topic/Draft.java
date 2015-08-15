package org.jtalks.tests.jcommune.webdriver.entity.topic;
import ru.yandex.qatools.allure.annotations.Step;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.postPage;

/**
 * @author baranov.r.p
 */
public class Draft extends Post {

    public Draft(String postContent) {
        super(postContent);
    }

    @Step
    public Draft loseFocus() {
        info("Trying to set focus on link post button");
        postPage.setFocusOnPostLinkButton();
        return this;
    }

    public void publish() {
        // publish draft as post/answer
    }

    public void deleteContent() {
        // delete content by pressing Backspace
    }

    public void editContent(int quantitySymbRemain) {
        // remain quantity of symb that specified in parameters
    }
}