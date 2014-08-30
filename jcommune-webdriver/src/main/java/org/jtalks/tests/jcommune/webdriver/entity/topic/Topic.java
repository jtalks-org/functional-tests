package org.jtalks.tests.jcommune.webdriver.entity.topic;

import org.apache.commons.lang3.RandomStringUtils;
import org.joda.time.DateTime;
import org.jtalks.tests.jcommune.utils.TestStringUtils;
import org.jtalks.tests.jcommune.webdriver.entity.branch.Branch;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * Jcommune topic representation.
 */
public class Topic {
    private Branch branch;
    private DateTime creationDate;
    private DateTime modificationDate;
    private User topicStarter;
    private String title = randomAlphanumeric(30);
    private boolean sticked = false;
    private boolean announcement = false;
    private Boolean closed;
    private int views;
    private Poll poll;
    private List<Post> posts = new ArrayList<Post>();
    private boolean hasNewMessages;

    public Topic() {
        posts.add(new Post(randomAlphanumeric((200))));
    }

    public Topic(String title, String firstPostContent) {
        this.title = title;
        posts.add(new Post(firstPostContent));
    }

    public Topic withTitle(String title){
        this.title = title;
        return this;
    }

    public Topic withBody(String body) {
        Post firstPost = getPosts().get(0);
        firstPost.setPostContent(body);
        return this;
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

    public Topic withTopicStarter(User topicStarter) {
        this.topicStarter = topicStarter;
        return this;
    }

    public String getTitle() {
        return title;
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

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
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

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Branch getBranch() {
        return branch;
    }

    public Topic withBranch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public Topic withBranch(String branchTitle) {
        this.branch = new Branch(branchTitle);
        return this;
    }

    public Post getFirstPost() {
        return getPosts().get(0);
    }

    public Post getLastPost() {
        return getPosts().get(getPosts().size() - 1);
    }

    public boolean hasNewMessages() {
        return hasNewMessages;
    }

    public void setHasNewMessages(boolean hasNewMessages) {
        this.hasNewMessages = hasNewMessages;
    }

    @Override
    public String toString() {
        return String.format("Topic: title=[%s]", this.title);
    }
}
