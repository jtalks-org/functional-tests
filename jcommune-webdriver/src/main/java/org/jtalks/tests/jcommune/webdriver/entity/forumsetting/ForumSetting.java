package org.jtalks.tests.jcommune.webdriver.entity.forumsetting;

/**
 * @author Yuliya
 */
public class ForumSetting {

    private String value;

    private Object file;

    public String getValue() {
        return value;
    }

    public ForumSetting withValue(String value){
        this.value = value;
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
