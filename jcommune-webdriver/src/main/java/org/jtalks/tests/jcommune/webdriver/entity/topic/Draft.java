package org.jtalks.tests.jcommune.webdriver.entity.topic;

/**
 * @author baranov.r.p
 */
public class Draft extends Post {

    public Draft(String postContent) {
        super(postContent);
    }

    public void loseFocus() {
        // actions for losing focus
    }

    public void publish() {
        // publish draft as post/answer
    }

    public void remove() {
        // remove draft by clicking trash button
    }

    public void deleteContent() {
        // delete content by pressing Backspace
    }

    public void editContent(int quantitySymbRemain) {
        // remain quantity of symb that specified in parameters
    }
}