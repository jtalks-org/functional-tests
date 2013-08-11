package org.jtalks.tests.jcommune;

import com.rexsl.w3c.Defect;
import com.rexsl.w3c.ValidationResponse;
import com.rexsl.w3c.Validator;
import com.rexsl.w3c.ValidatorBuilder;
import junit.framework.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

import static org.jtalks.tests.jcommune.webdriver.JCommuneSeleniumConfig.driver;

/**
 * @author Leonid Kazancev
 */
public final class PageHtmlValidator {
    private static final int ALLOWED_ERROR_COUNT = 0;
    private static final int ALLOWED_WARNING_COUNT = 1;
    private static final Logger logger = LoggerFactory.getLogger(PageHtmlValidator.class);


    private PageHtmlValidator(){
    }

    public static void validatePage(String pageSource) {
        Validator htmlValidator = new ValidatorBuilder().html();
        ValidationResponse response = htmlValidator.validate(pageSource);

        Set<Defect> errors = response.errors();
        int errorCount = errors.size();
        logger.info("W3C validation errors count: {}", errorCount);
        if (errorCount > 0) {
            logger.info("W3C validation errors: {}", errors);
        }
        Set<Defect> warnings = response.warnings();
        int warningCount = warnings.size();
        logger.info("W3C validation warnings count: {}", warningCount);
        if (warningCount > 0) {
            logger.info("W3C validation warnings: {}", warnings);
        }

        Assert.assertTrue("Error count exceed.", errorCount <= ALLOWED_ERROR_COUNT);
        Assert.assertTrue("Warning count exceed.", warnings.size()  <= ALLOWED_WARNING_COUNT);
    }
}
