package org.jtalks.tests.jcommune.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;


/**
 * @author masyan
 */
public class PostPage {

    public static final String newButtonSel = "(//a[@id='new-topic-btn'])[2]";

    public static final String messageFieldSel = "tbMsg";

    public static final String postButtonSel = "post";

    public static final String lastPostMessageSel = "//div[@class='post'][last()]/table/tbody/tr/td[@class='post-content-td']/div";

    public static final String lastPostAuthorSel = "//li[@class='forum_row'][last()]/div[@class='forum_userinfo']/a[@class='username']";

    public static final String backButtonSel = "//a[@class='back-btn']";

    public static final String deleteButtonNearLastPostSel = "//div[@class='post'][last()]//a[@class='btn btn-mini btn-danger delete']";

    public static final String linkButtonNearLastPostSel = "//div[@class='post'][last()]//a[@class='btn btn-mini postLink']";

    public static final String deleteTopicButtonSel = "//div[@class='post']//a[@class='btn btn-mini btn-danger delete']";

    public static final String deleteConfirmOkButtonSel = "jqi_state0_buttonOk";

    public static final String deleteConfirmCancelButtonSel = "jqi_state0_buttonCancel";

    public static final String postErrorMessageSel = "bodyText.errors";

    public static final String postsListSel = "//div[@class='post']";

    public static final String postsMessagesSel = "//div[@class='post']/table/tbody/tr/td[@class='post-content-td']/div";

    public static final String editPostButtonSel = "//a[@id='edit_button' and contains(@href, 'posts')]";

    public static final String editTopicButtonSel = "//a[@id='edit_button' and contains(@href, 'topics')]";

    public static final String permanentUrlToPostSel = "//div[@class='jqimessage']";

    public static final String lastPostLinksFromBranchSel = "//table[@id='topics-table']/tbody/tr/td[contains(@class, 'latest-by')]/a";

    public static final String lastPostLinksFromTopicSel = "//table[@id='topics-table']/tbody/tr/td[@class='latest-by']/a";

    public static final String pagesButtonsSel = "//div[contains(@class, 'pull-right forum-pagination')]//li/a";

    public static final String showAllButtonSel = "//a[contains(@href, '?pagingEnabled=false')]";

    public static final String showPagesButtonSel = "//a[@href='?pagingEnabled=true']";

    public static final String signatureTextSel = "//div[@class='post']/table/tbody/tr/td[@class='post-content-td']/div[@align='left']";

    public static final String authorsOfPostsListSel = "//div[@class='post']/table/tbody/tr/td[@class='userinfo']/div/a[@class='post-userinfo-username']";

    @FindBy(xpath = lastPostAuthorSel)
    private WebElement lastPostAuthor;

    @FindBy(xpath = newButtonSel)
    private WebElement newButton;

    @FindBy(id = messageFieldSel)
    private WebElement messageField;

    @FindBy(id = postButtonSel)
    private WebElement postButton;

    @FindBy(xpath = lastPostMessageSel)
    private WebElement lastPostMessage;

    @FindBy(xpath = backButtonSel)
    private WebElement backButton;

    @FindBy(xpath = deleteButtonNearLastPostSel)
    private WebElement deleteButtonNearLastPost;

    @FindBy(xpath = linkButtonNearLastPostSel)
    private WebElement linkButtonNearLastPost;

    @FindBy(id = deleteConfirmOkButtonSel)
    private WebElement deleteConfirmOkButton;

    @FindBy(id = deleteConfirmCancelButtonSel)
    private WebElement deleteConfirmCancelButton;

    @FindBy(id = postErrorMessageSel)
    private WebElement postErrorMessage;

    @FindBy(xpath = postsListSel)
    private List<WebElement> postsList;

    @FindBy(xpath = postsMessagesSel)
    private List<WebElement> postsMessages;

    @FindBy(xpath = deleteTopicButtonSel)
    private WebElement deleteTopicButton;

    @FindBy(xpath = editPostButtonSel)
    private WebElement editPostButton;

    @FindBy(xpath = editTopicButtonSel)
    private WebElement editTopicButton;

    @FindBy(xpath = permanentUrlToPostSel)
    private WebElement permanentUrlToPost;

    @FindBy(xpath = lastPostLinksFromBranchSel)
    private List<WebElement> lastPostLinksFromBranch;

    @FindBy(xpath = lastPostLinksFromTopicSel)
    private List<WebElement> lastPostLinksFromTopic;

    @FindBy(xpath = pagesButtonsSel)
    private List<WebElement> pagesButtons;

    @FindBy(xpath = showAllButtonSel)
    private WebElement showAllButton;

    @FindBy(xpath = showPagesButtonSel)
    private WebElement showPagesButton;

    @FindBy(xpath = signatureTextSel)
    private WebElement signatureText;

    @FindBy(xpath = authorsOfPostsListSel)
    private List<WebElement> authorsOfPostsList;


    public PostPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }


    //Getters


    public WebElement getLastPostAuthor() {
        return lastPostAuthor;
    }

    public WebElement getNewButton() {
        return newButton;
    }

    public WebElement getMessageField() {
        return messageField;
    }

    public WebElement getPostButton() {
        return postButton;
    }

    public WebElement getLastPostMessage() {
        return lastPostMessage;
    }

    public WebElement getBackButton() {
        return backButton;
    }

    public WebElement getDeleteButtonNearLastPost() {
        return deleteButtonNearLastPost;
    }

    public WebElement getLinkButtonNearLastPost() {
        return linkButtonNearLastPost;
    }

    public WebElement getDeleteConfirmOkButton() {
        return deleteConfirmOkButton;
    }

    public WebElement getDeleteConfirmCancelButton() {
        return deleteConfirmCancelButton;
    }

    public WebElement getPostErrorMessage() {
        return postErrorMessage;
    }

    public List<WebElement> getPostsList() {
        return postsList;
    }

    public List<WebElement> getPostsMessages() {
        return postsMessages;
    }

    public WebElement getDeleteTopicButton() {
        return deleteTopicButton;
    }

    public WebElement getEditPostButton() {
        return editPostButton;
    }

    public WebElement getEditTopicButton() {
        return editTopicButton;
    }

    public WebElement getPermanentUrlToPost() {
        return permanentUrlToPost;
    }

    public List<WebElement> getLastPostLinksFromBranch() {
        return lastPostLinksFromBranch;
    }

    public List<WebElement> getLastPostLinksFromTopic() {
        return lastPostLinksFromTopic;
    }

    public List<WebElement> getPagesButtons() {
        return pagesButtons;
    }

    public WebElement getShowAllButton() {
        return showAllButton;
    }

    /**
     * Returns the page link button object in pagination region
     *
     * @param pageNum - page number is needed to return
     */
    public WebElement getPageLinkButton(int pageNum) {
        WebElement pageLinkButton = pagesButtons.get(pageNum - 1);
        return pageLinkButton;
    }

    public WebElement getShowPagesButton() {
        return showPagesButton;
    }

    public WebElement getSignatureText() {
        return signatureText;
    }

    public List<WebElement> getAuthorsOfPostsList() {
        return authorsOfPostsList;
    }
}
