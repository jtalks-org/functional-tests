package org.jtalks.tests.jcommune.webdriver;

import java.util.ArrayList;
import java.util.List;

/**
 * Jcommune poll representation.
 */
public class Poll {

    private String title;
    private Topic topic;
    private List<PollItem> pollItems = new ArrayList<PollItem>();
    private String pollItemsValue;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<PollItem> getPollItems() {
        return pollItems;
    }

    public void setPollItems(List<PollItem> pollItems) {
        this.pollItems = pollItems;
    }

    public String getPollItemsValue() {
        return pollItemsValue;
    }

    public void setPollItemsValue(String pollItemsValue) {
        this.pollItemsValue = pollItemsValue;
    }
}
