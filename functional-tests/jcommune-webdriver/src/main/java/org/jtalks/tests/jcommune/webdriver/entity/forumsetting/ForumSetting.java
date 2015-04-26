package org.jtalks.tests.jcommune.webdriver.entity.forumsetting;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.File;

/**
 * @author Yuliya
 */
public class ForumSetting {

    private String title = RandomStringUtils.randomAlphanumeric(30);
    private String description = RandomStringUtils.randomAlphanumeric(30);
    private String prefix = RandomStringUtils.randomAlphanumeric(30);
    private String tooltip = RandomStringUtils.randomAlphanumeric(30);
    private String copyright = RandomStringUtils.randomAlphanumeric(30);

    private Object file;

    public String getTitle() {
        return title;
    }

    public ForumSetting withTitle(String title){
        this.title = title;
        return this;
    }

    public String getDescription() { return description; }

    public ForumSetting withDescription(String description){
        this.description = description;
        return this;
    }

    public String getPrefix() { return prefix; }

    public ForumSetting withPrefix(String prefix){
        this.prefix = prefix;
        return this;
    }

    public String getTooltip() { return tooltip; }

    public ForumSetting withTooltip(String tooltip){
        this.tooltip = tooltip;
        return this;
    }

    public String getCopyright() { return copyright; }

    public ForumSetting withCopyright(String copyright){
        this.copyright = copyright;
        return this;
    }

    public Object getFile() {
        return file;
    }

    public ForumSetting withObject(Object file){
        this.file = file;
        return this;
    }

}
