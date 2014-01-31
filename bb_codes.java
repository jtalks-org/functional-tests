/**
 * Created by Gradobik on 31.01.14.
 * заготовка для тестов по бб-коду
 */
//text attributes
import java.swing.JTextPane;
import java.awt.Font;
import java.swing.text.SimpleAttributeSet;

class bb_codes {
    // set a bb-code method
    public  setBBCode() {
        //set a string type for a future bb-codes, possible could be an array like {"b","/b"}
        String bb_code() = {
            return bb_code;
        };
        /* String[][] bb_code() = {
            retunt bb_code //возможно бб коды описать массивами и вызывать по типу bb_code[0][1] который и будет
            описывать открывающий или закрывающий тэг
        }
         */
    }

    SimpleAttributeSet attributes = new SimpleAttributeSet();
    attributes.addAttribute(StyleConstants.CharacterConstants.Bold, Boolean.TRUE);
    }

    @Test
    public void usingAbb_codeForASsingleExpressionInNewTopiсScreen_QA_7() throws Exception {
        Users.signUpAndSignIn();
        setBBCode bb_code1 = new bb_codes();
        setBBCode bb_code2 = new bb_codes();
        bb_code1.setBBCode = "b";
        bb_code2.setBBCode = "/b";
        Topic topic = new Topic("subject", set_text_in);
        Topic createdTopic = Topics.createTopic(topic);
    }
}
