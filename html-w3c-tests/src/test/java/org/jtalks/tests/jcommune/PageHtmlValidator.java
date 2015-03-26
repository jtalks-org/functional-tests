package org.jtalks.tests.jcommune;

import com.rexsl.w3c.Defect;
import com.rexsl.w3c.ValidationResponse;
import com.rexsl.w3c.Validator;
import com.rexsl.w3c.ValidatorBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static junit.framework.Assert.assertTrue;
import static org.apache.commons.lang3.StringUtils.repeat;

/**
 * @author Leonid Kazancev
 */
public final class PageHtmlValidator {
    private static final int ALLOWED_ERROR_COUNT = 0;
    private static final int ALLOWED_WARNING_COUNT = 1;
    private static final Logger logger = LoggerFactory.getLogger(PageHtmlValidator.class);


    private PageHtmlValidator() {
    }

    public static void validatePage(String pageSource) {
        Validator htmlValidator = new ValidatorBuilder().html();
        ValidationResponse response = htmlValidator.validate(pageSource);

        Set<Defect> errors = response.errors();
        int errorCount = errors.size();
        logger.info("W3C validation errors count: {}", errorCount);
        logDefects(new ArrayList<>(errors), "error");

        Set<Defect> warnings = response.warnings();
        int warningCount = warnings.size();
        logger.info("W3C validation warnings count: {}", warningCount);
        logDefects(new ArrayList<>(warnings), "warning");

        if (errorCount > 0 || warningCount > 0) {
            logger.info(repeat("=", 40));
            logger.info("===PAGE SOURCE==: \n{}", pageSource);
            logger.info(repeat("=", 40));
        }

        assertTrue("Error count exceed.", errorCount <= ALLOWED_ERROR_COUNT);
        assertTrue("Warning count exceed.", warnings.size() <= ALLOWED_WARNING_COUNT);
    }

    private static void logDefects(List<Defect> defects, String warningOrError) {

        for (int i = 0; i < defects.size(); i++) {
            logger.info(repeat("=", 20) + warningOrError + " #" + i + repeat("=", 20));
            logger.info("W3C validation {}: \n{}", warningOrError, defects.get(0));
            logger.info(repeat("=", 20));
        }
    }
}
