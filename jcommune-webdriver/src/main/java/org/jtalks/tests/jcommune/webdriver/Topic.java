package org.jtalks.tests.jcommune.webdriver;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Jcommune topic representation.
 */
public class Topic {

    private DateTime creationDate;
    private DateTime modificationDate;
    private User topicStarter;
    private String title;
    private boolean sticked;
    private boolean announcement;
    private boolean closed;
    private int views;
    private Poll poll;
    private CodeReview codeReview;
    private List<Post> posts = new ArrayList<Post>();
    private Set<User> subscribers = new HashSet<User>();


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

    public User getTopicStarter() {
        return topicStarter;
    }

    public void setTopicStarter(User topicStarter) {
        this.topicStarter = topicStarter;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSticked() {
        return sticked;
    }

    public void setSticked(boolean sticked) {
        this.sticked = sticked;
    }

    public boolean isAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(boolean announcement) {
        this.announcement = announcement;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll = poll;
    }

    public CodeReview getCodeReview() {
        return codeReview;
    }

    public void setCodeReview(CodeReview codeReview) {
        this.codeReview = codeReview;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
        this.subscribers = subscribers;
    }
}
