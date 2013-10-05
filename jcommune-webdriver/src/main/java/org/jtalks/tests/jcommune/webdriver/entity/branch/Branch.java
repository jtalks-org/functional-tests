package org.jtalks.tests.jcommune.webdriver.entity.branch;

import com.google.common.collect.Sets;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;

import java.util.Set;

/**
 * @author stanislav bashkirtsev
 * @author andrey ivanov
 */
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
