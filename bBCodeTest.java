import java.swing.JTextPane;
import java.awt.Font;
import java.swing.text.SimpleAttributeSet;

public class BbCodeTest {

    @BeforeMethod
    @Parameters({"appUrl"})
    public void setupCase(String appUrl) throws ValidationException {
        driver.get(appUrl);
        mainPage.logOutIfLoggedIn(driver);
    }

    @Test /*test for an only creating topic with a [b]message[/b], without any checking bold text or not*/
    public void boldBbCodeSingleExpressionNewTopic_ShouldPass_QA_7() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic("subject","[b]" + "message" + "[/b]");
        Topic createdTopic = Topics.createTopic(topic);
        Assert.assertTrue(Topics.isCreated(createdTopic));
    }

    @Test(enabled = false, expectedExceptions = ValidationException.class,
            expectedExceptionsMessageRegExp = TopicPage.EMPTY_BODY_ERROR) /*I have had some troubles with finding an exceptions so I leaved test as if, and a big question where should I create an exception here or in another file?*/
    private void noTextIsideBoldBbCodeSingleExpressionNewTopic_ShouldFail_QA_26() throws Exception {
        Users.signUpAndSignIn();
        Topic topic = new Topic("subject", "[b]" + "" + "[/b]");
        Topics.createTopic(topic);
    }

}
