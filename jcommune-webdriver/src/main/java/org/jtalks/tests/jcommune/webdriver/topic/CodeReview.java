package org.jtalks.tests.jcommune.webdriver.topic;

import java.util.ArrayList;
import java.util.List;

/**
 * Jcommune code review representation.
 */
public class CodeReview {

    private Topic topic;
    private List<CodeReviewComment> comments = new ArrayList<CodeReviewComment>();

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public List<CodeReviewComment> getComments() {
        return comments;
    }

    public void setComments(List<CodeReviewComment> comments) {
        this.comments = comments;
    }
}
