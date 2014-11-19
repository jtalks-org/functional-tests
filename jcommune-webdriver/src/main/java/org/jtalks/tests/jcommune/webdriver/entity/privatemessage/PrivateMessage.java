package org.jtalks.tests.jcommune.webdriver.entity.privatemessage;

import org.jtalks.tests.jcommune.webdriver.entity.topic.Topic;
import org.jtalks.tests.jcommune.webdriver.entity.user.User;
import org.joda.time.DateTime;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * JCommune private messages representation
 *
 * @author Varro
 */
public class PrivateMessage {
    private User author;
    private User receiver;
    private String messageSubject;
    private String messageContent;
    private DateTime creationDate;
    private boolean read;

    public PrivateMessage()
    {
        this.messageSubject = RandomStringUtils.randomAlphanumeric(25);
        this.messageContent = RandomStringUtils.randomAlphanumeric(25);
    }

    public PrivateMessage(User receiver, String messageSubject, String postContent) {
        this.receiver = receiver;
        this.messageSubject = messageSubject;
        this.messageContent = postContent;
    }

    public PrivateMessage(User author, User receiver, String messageSubject, String postContent) {
        this.author = author;
        this.receiver = receiver;
        this.messageSubject = messageSubject;
        this.messageContent = postContent;
    }

    public User getAuthor() {
        return this.author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getReceiver() {
        return this.receiver;
    }

    public PrivateMessage withReceiver(User receiver){
        this.receiver = receiver;
        return this;
    }

    public String getMessageSubject() {
        return this.messageSubject;
    }

    public PrivateMessage withSubject(String messageSubject){
        this.messageSubject = messageSubject;
        return this;
    }

    public String getMessageContent(){
        return this.messageContent;
    }

    public PrivateMessage withContent(String messageContent) {
        this.messageContent = messageContent;
        return this;
    }

    public DateTime getCreationDate() {return this.creationDate; }
}
