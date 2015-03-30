package org.jtalks.tests.jcommune.webdriver.entity.topic;

import com.google.common.base.Splitter;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;


/**
 * JCommune QA representation
 */
public class QA  extends Topic{
    private String content = randomAlphanumeric(50);

    public QA() {}

    public QA(String title, String content) {
        this.withTitle(title);
        this.content = content;
    }
}
