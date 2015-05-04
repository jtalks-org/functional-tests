package org.jtalks.tests.jcommune.webdriver.entity.topic;


import com.google.common.base.Splitter;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;


/**
 * JCommune QA representation
 */

public class Question  extends Topic {
    private String content = randomAlphanumeric(50);

    public Question() {
    }

    public Question(String title, String content) {
        this.withTitle(title);
        this.content = content;
    }
}
