package org.jtalks.tests.jcommune.webdriver.entity.topic;

import org.joda.time.DateTime;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;


/**
 * Jcommune post representation.
 */
public class Post {

    private DateTime creationDate;
    private DateTime modificationDate;
    private User userCreated;
    private String postContent;
    private Topic topic;

    public Post() {
        this.postContent = randomAlphanumeric(100);
    }

    public Post(String postContent) {
        this.postContent = postContent;
    }

    public DateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(DateTime creationDate) {
        this.creationDate = creationDate;
    }

    public DateTime getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(DateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public User getUserCreated() {
        return userCreated;
    }

    public void setUserCreated(User userCreated) {
        this.userCreated = userCreated;
    }

    public String getPostContent() {
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }
}
