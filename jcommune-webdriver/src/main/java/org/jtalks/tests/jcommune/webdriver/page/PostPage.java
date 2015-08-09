package org.jtalks.tests.jcommune.webdriver.page;

import com.google.common.base.Splitter;
import org.apache.commons.lang3.StringUtils;
import org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig;
import org.jtalks.tests.jcommune.webdriver.entity.topic.CodeReviewComment;
import org.jtalks.tests.jcommune.webdriver.exceptions.PermissionsDeniedException;
import org.jtalks.tests.jcommune.webdriver.exceptions.TimeoutException;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.jtalks.tests.jcommune.utils.ReportNgLogger.info;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.moveTopicEditor;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.postPage;
import static org.jtalks.tests.jcommune.webdriver.page.Pages.scrollToEl;

/**
 * @author masyan
 * @author pancheshenko andrey
 */
public class PostPage {

    public static final String topicTitleSel = "//a[@class='invisible-link' and contains(@href, '/topics/')]";

    public static final String messageFieldSel = "postBody";

    public static final String postButtonSel = "post";

    public static final String branchNameSel = "//ul[@class='breadcrumb']/li[last()]/h2/a";

    public static final String lastPostMessageSel = "//div[@class='post'][last()]/table/tbody/tr/td[@class='post-content-td']/div";

    public static final String lastPostAuthorSel = "//li[@class='forum_row'][last()]/div[@class='forum_userinfo']/a[@class='username']";

    public static final String backButtonSel = "//a[@class='back-btn']";

    public static final String subscribeButtonSel = "//a[@id='subscription' and contains(@href, '/topics/')]";

    public static final String deleteButtonNearLastPostSel = "//div[@class='post'][last()]//a[@class='btn btn-mini btn-danger delete']";

    public static final String linkButtonNearLastPostSel = "//div[@class='post'][last()]//a[@class='btn btn-mini postLink']";

    public static final String deleteTopicButtonSel = "//a[@class='btn btn-mini btn-danger delete' and contains(@href, 'topics')]";

    public static final String deletePostButtonSel = "//a[@class='btn btn-mini btn-danger delete' and contains(@href, 'posts')]";

    public static final String deleteConfirmOkButtonSel = "remove-entity-ok";

    public static final String deleteCRCommentConfirmOkButtonSel = "remove-review-ok";

    public static final String deleteConfirmCancelButtonSel = "jqi_state0_buttonCancel";

    public static final String postErrorMessageSel = "bodyText.errors";

    public static final String firstPostSel = "//div[@class='post script-first-post']";

    public static final String postsListSel = "//div[@class='container']/div/div[contains(@class,'post')]";

    public static final String postMessageSel = "//td[@class='post-content-td']/div";

    public static final String editButtonCommonSel = "//a[contains(@class,'edit_button')]";

    public static final String editPostButtonSel = "//a[contains(@class,'edit_button') and contains(@href, 'posts')]";

    public static final String editTopicButtonSel = "//a[contains(@class,'edit_button') and contains(@href, 'topics')]";

    public static final String moveTopicButtonSel = "//div[contains(@class, 'upper-pagination')]/div/a[@class='move_topic btn space-left-medium-nf']";

    public static final String permanentUrlToPostSel = "//div[@class='jqimessage']";

    public static final String lastPostLinksFromBranchSel = "//table[@id='topics-table']/tbody/tr/td[contains(@class, 'latest-by')]/a";

    public static final String lastPostLinksFromTopicSel = "//table[@id='topics-table']/tbody/tr/td[contains(@class, 'latest-by')]/a";

    public static final String pagesButtonsSel = "//div[contains(@class, 'pull-right forum-pagination')]//li/a";

    public static final String showAllButtonSel = "//a[contains(@href, '?pagingEnabled=false')]";

    public static final String showPagesButtonSel = "//a[@href='?pagingEnabled=true']";

    public static final String signatureTextSel = "//div[@class='post']/table/tbody/tr/td[@class='post-content-td']/div[@align='left']";

    public static final String authorsOfPostsListSel = "//div[@class='post']/table/tbody/tr/td[@class='userinfo']/div/a[@class='post-userinfo-username']";

    public static final String codeReviewLineSel = "//ol[@class='linenums']/li";

    public static final String codeReviewCommentsListSel = "//div[@class='review-container']";

    public static final String codeReviewCommentBodySel = "//div[@class='review-body']";

    public static final String codeReviewCommentEditButtonSel = "//a[@name='edit-review']";

    public static final String codeReviewCommentDeleteButtonSel = "//a[@name='delete-review']";

    public static final String codeReviewCommentTextFieldSel = "//textarea[@class='review-container-content']";

    public static final String codeReviewCommentConfirmButtonSel = "//input[@class='btn btn-primary review-container-controls-ok']";

    public static final String codeReviewCommentBodyErrorMessageSel = "//div[@id='add-comment-form']/div[@class='control-group error']/span[@class='help-inline']";

    public static final String textareaEditorTag = "textarea";

    public static final String postDtoId = "postDto";

    public static final String draftCounterId = "counter";

    // space symbol is used for localization compatibility, in any language counter message will have spaces
    public static final String draftCounterActiveSel = "//span[@id='" + draftCounterId + "' and contains(text(),' ')]";

    public static final String postLinkClass = "postLink";

    @FindBy(xpath = topicTitleSel)
    private WebElement topicTitle;

    @FindBy(xpath = lastPostAuthorSel)
    private WebElement lastPostAuthor;

    @FindBy(id = messageFieldSel)
    private WebElement messageField;

    @FindBy(id = postButtonSel)
    private WebElement postButton;

    @FindBy(xpath = branchNameSel)
    private WebElement branchName;

    @FindBy(xpath = lastPostMessageSel)
    private WebElement lastPostMessage;

    @FindBy(xpath = backButtonSel)
    private WebElement backButton;

    @FindBy(xpath = subscribeButtonSel)
    private WebElement subscribeButton;

    @FindBy(xpath = deleteButtonNearLastPostSel)
    private WebElement deleteButtonNearLastPost;

    @FindBy(xpath = linkButtonNearLastPostSel)
    private WebElement linkButtonNearLastPost;

    @FindBy(id = deleteConfirmOkButtonSel)
    private WebElement deleteConfirmOkButton;

    @FindBy(id = deleteConfirmCancelButtonSel)
    private WebElement deleteConfirmCancelButton;

    @FindBy(id = deleteCRCommentConfirmOkButtonSel)
    private WebElement deleteCRCommentConfirmOkButton;

    @FindBy(id = postErrorMessageSel)
    private WebElement postErrorMessage;

    @FindBy(xpath = firstPostSel)
    private WebElement firstPost;

    @FindBy(xpath = postsListSel)
    private List<WebElement> postsList;

    @FindBy(xpath = postMessageSel)
    private List<WebElement> postsMessages;

    @FindBy(xpath = deleteTopicButtonSel)
    private WebElement deleteTopicButton;

    @FindBy(xpath = deletePostButtonSel)
    private WebElement deletePostButton;

    @FindBy(xpath = editPostButtonSel)
    private WebElement editPostButton;

    @FindBy(xpath = editTopicButtonSel)
    private WebElement editTopicButton;

    @FindBy(xpath = moveTopicButtonSel)
    private WebElement moveTopicButton;

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

    @FindBy(xpath = codeReviewLineSel)
    private List<WebElement> codeReviewLines;

    @FindBy(xpath = codeReviewCommentsListSel)
    private List<WebElement> codeReviewCommentsList;

    @FindBy(xpath = codeReviewCommentBodySel)
    private WebElement codeReviewCommentBody;

    @FindBy(xpath = codeReviewCommentEditButtonSel)
    private WebElement codeReviewCommentEditButton;

    @FindBy(xpath = codeReviewCommentDeleteButtonSel)
    private WebElement codeReviewCommentDeleteButton;

    @FindBy(xpath = codeReviewCommentTextFieldSel)
    private WebElement codeReviewCommentTextField;

    @FindBy(xpath = codeReviewCommentConfirmButtonSel)
    private WebElement codeReviewCommentConfirmButton;

    @FindBy(xpath = codeReviewCommentBodyErrorMessageSel)
    private WebElement codeReviewCommentBodyErrorMessage;

    @FindBy(tagName = textareaEditorTag)
    private WebElement textareaEditor;

    @FindBy(id = postDtoId)
    private WebElement postDto;

    @FindBy(id = draftCounterId)
    private WebElement draftCounter;

    @FindBy(xpath = draftCounterActiveSel)
    private WebElement draftCounterActive;

    @FindBy(className = postLinkClass)
    private WebElement postLink;

    private WebDriver driver;

    public PostPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void clickAnswerToTopicButton() throws PermissionsDeniedException {
        info("Clicking a button to add post in the topic");
        try {
            getPostButton().click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are granted");
            throw new PermissionsDeniedException("User does not have permissions to leave posts in the branch");
        }
    }

    public void clickLineInCodeReviewForComment(int lineNumber) throws PermissionsDeniedException {
        info("Clicking a line #" + lineNumber + " to leave comment");
        if(getCRLines().size()<lineNumber) {
            info("Passed linenumber is greater than the number of lines in the code review topic. Clicking on the last line.");
            getCRLines().get(getCRLines().size()-1).click();
        } else {
            getCRLines().get(lineNumber - 1).click();
        }
        try {
            (new WebDriverWait(driver, 5)).until(ExpectedConditions.visibilityOf(getCRCommentTextField()));
        } catch (TimeoutException e) {
            info("Clicking the line does nothing, looks like the permissions are not granted");
            throw new PermissionsDeniedException("User does not have permissions to leave comments in branch");
        }
    }

    public void fillPostBody(String body) {
        info("Filling new post body: [" + StringUtils.left(body, 100) + "...]");
        getMessageField().clear();
        for (String token : Splitter.fixedLength(100).split(body)) {
            getMessageField().sendKeys(token);
        }
    }

    public void fillCodeReviewCommentBody(CodeReviewComment codeReviewComment) {
        info("Filling code review comment body: [" + StringUtils.left(codeReviewComment.getPostContent(), 100) + "...]");
        getCRCommentTextField().sendKeys(codeReviewComment.getPostContent());
    }

    public void clickAddCommentToCodeReviewButton() throws PermissionsDeniedException {
        info("Clicking a button to add comment to code review");
        try {
            getCRCommentConfirmButton().click();
            info("The button was clicked");
        } catch (NoSuchElementException e) {
            info("Couldn't click the button, looks like the permissions are not granted");
            throw new PermissionsDeniedException("User does not have permissions to leave comments in the branch");
        }
    }

    public void clickEditInPostContainingString(String postContent) {
        info("Trying to find post with content: " + postContent);
        for (WebElement currentPost : getPostsList()) {
            if (currentPost.findElement(By.xpath("." + postMessageSel)).getText().contains(postContent)) {
                info("Post was found. Clicking edit button...");
                try {
                    currentPost.findElement(By.xpath("." + editButtonCommonSel)).click();
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Edit button wasn't found in the post. Lack of permissions?");
                }
                break;
            }
        }
    }

    public void clickEditInCodeReviewCommentContainingString(String commentContent) {
        info("Trying to find comment with content: " + commentContent);
        for (WebElement currentComment : getCRCommentsList()) {
            if(currentComment.findElement(By.xpath("." + codeReviewCommentBodySel)).getText().contains(commentContent)) {
                info("Comment was found. Clicking edit button...");
                try {
                    currentComment.findElement(By.xpath("." + codeReviewCommentEditButtonSel)).click();
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Edit button wasn't found in the comment. Lack of permissions?");
                }
            }
        }
    }

    public void clickDeleteInCodeReviewCommentContainingString(String commentContent) {
        info("Trying to find comment with content: " + commentContent);
        for (WebElement currentComment : getCRCommentsList()) {
            if(currentComment.findElement(By.xpath("." + codeReviewCommentBodySel)).getText().contains(commentContent)) {
                info("Comment was found. Clicking delete button...");
                try {
                    currentComment.findElement(By.xpath("." + codeReviewCommentDeleteButtonSel)).click();
                } catch (NoSuchElementException e) {
                    throw new NoSuchElementException("Delete button wasn't found in the comment. Lack of permissions?");
                }
            }
        }
    }

    public void editCodeReviewCommentBody(String newMessage) {
        info("Editing comment body");
        getCRCommentTextField().clear();
        for (String token : Splitter.fixedLength(100).split(newMessage)) {
            getCRCommentTextField().sendKeys(token);
        }
    }

    public void clickOkButtonInEditComment() {
        info("Clicking a button to confirm changes in the code review comment");
        try {
            getCRCommentConfirmButton().click();
            info("The confirmation button [Edit] was clicked");
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("[Edit] confirmation button wasn't found.");
        }
    }

    public void closeDeleteConfirmDialogOk() {
        try {
            info("Waiting for post/topic delete confirmation dialog...");
            (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(getDeleteConfirmOkButton()));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Tried to wait delete confirmation dialog without any reaction");
        }
        info("The dialog showed up!");
        getDeleteConfirmOkButton().click();
    }

    public void closeDeleteCRCommentConfirmDialogOk() {
        try {
            info("Waiting for code review comment delete confirmation dialog...");
            (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(getDeleteCRCommentConfirmOkButton()));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Tried to wait delete confirmation dialog without any reaction");
        }
        info("The dialog showed up!");
        getDeleteCRCommentConfirmOkButton().click();
    }

    public void openMoveTopicEditorDialog() {
        try {
            getMoveTopicButton().click();
        } catch (NoSuchElementException e) {
            info("Move button was not found");
            throw new PermissionsDeniedException("Move button was not found. Lack of permissions?");
        }
        try {
            info("Waiting for move topic editor dialog...");
            (new WebDriverWait(driver, 20)).until(ExpectedConditions.visibilityOf(moveTopicEditor.getMoveTopicEditorForm()));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Tried to wait move topic editor dialog without any reaction");
        }
        info("The dialog showed up!");
    }

    public void returnToBranchPage() {
        getBranchName().click();
    }

    public boolean isUserAlreadySubscribed() {
        return getSubscribeButton().getAttribute("href").contains("/unsubscribe");
    }

    public boolean isUserInsideCorrectTopic(String title) {
        info("Check whether user is in required topic already");
        if (JCommuneSeleniumConfig.driver.getCurrentUrl().contains("/topics/")) {
            return getTopicTitle().getText().trim().equals(title);
        }
        return false;
    }

    public void setFocusOnPostLinkButton() {
        // move focus outside of textarea
        getPostLinkButton().sendKeys("");
        info("Focus set on edit post button");
    }

    public String scrollDownAndFindDraftCountMessage() {
        // scroll page to the bottom of post editor,
        // guarantee that draft counter message will be visible for driver
        scrollToEl(getPostDto());
        info("Trying to find draft counter message");
        return getDraftCounterActiveSpan().getText();
    }

    public String findDraftContent() {
        checkCounter();
        info("Trying to find draft content");
        return getTextareaEditor().getAttribute("value");
    }

    public boolean checkCounter() {
        try {
            postPage.scrollDownAndFindDraftCountMessage();
        } catch (Exception e) {
            throw new RuntimeException("Draft was not created", e);
        }
        return true;
    }

    //Getters

    public WebElement getTopicTitle() {
        return topicTitle;
    }

    public WebElement getLastPostAuthor() {
        return lastPostAuthor;
    }

    public WebElement getMessageField() {
        return messageField;
    }

    public WebElement getPostButton() {
        return postButton;
    }

    public WebElement getBranchName() {
        return branchName;
    }

    public WebElement getLastPostMessage() {
        return lastPostMessage;
    }

    public WebElement getBackButton() {
        return backButton;
    }

    public WebElement getSubscribeButton() {
        return subscribeButton;
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

    public WebElement getDeleteCRCommentConfirmOkButton() {
        return deleteCRCommentConfirmOkButton;
    }

    public WebElement getPostErrorMessage() {
        return postErrorMessage;
    }

    public WebElement getFirstPost() {
        return firstPost;
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

    public WebElement getDeletePostButton() {
        return deletePostButton;
    }

    public WebElement getEditPostButton() {
        return editPostButton;
    }

    public WebElement getEditTopicButton() {
        return editTopicButton;
    }

    public WebElement getMoveTopicButton() {
        return moveTopicButton;
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

    public List<WebElement> getCRLines() {
        return codeReviewLines;
    }

    public List<WebElement> getCRCommentsList() {
        return codeReviewCommentsList;
    }

    public WebElement getCRCommentBody() {
        return codeReviewCommentBody;
    }

    public WebElement getCRCommentEditButton() {
        return codeReviewCommentEditButton;
    }

    public WebElement getCRCommentTextField() {
        return codeReviewCommentTextField;
    }

    public WebElement getCRCommentConfirmButton() {
        return codeReviewCommentConfirmButton;
    }

    public WebElement getCRCommentBodyErrorMessage() {
        return codeReviewCommentBodyErrorMessage;
    }

    public WebElement getPostDto() {
        return postDto;
    }

    public WebElement getDraftCounter() {
        return draftCounter;
    }

    public WebElement getDraftCounterActiveSpan() {
        return draftCounterActive;
    }

    public WebElement getTextareaEditor() {
        return textareaEditor;
    }

    public WebElement getPostLinkButton() {
        return postLink;
    }
}