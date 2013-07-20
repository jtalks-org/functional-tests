package org.jtalks.tests.jcommune.webdriver.entity.branch;

/** @author stanislav bashkirtsev */
public class Branch {
    private String title;

    public Branch() {
    }

    public Branch(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Branch withTitle(String title) {
        this.title = title;
        return this;
    }
}
