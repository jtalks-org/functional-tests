package org.jtalks.tests.jcommune.webdriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;
import org.testng.xml.XmlSuite;

/** @author stanislav bashkirtsev */
public class SeleniumSessionListener implements ITestListener, IConfigurationListener2 {
    @Override
    public void beforeConfiguration(ITestResult tr) {
        if (tr.getMethod().isBeforeClassConfiguration()) {
            ITestClass testClass = tr.getMethod().getTestClass();

            logger.info("STARTING TEST CLASS [{}] >>> >>>", testClass.getName());
            XmlSuite suite = testClass.getXmlTest().getSuite();
            JCommuneSeleniumConfig seleniumConfig = new JCommuneSeleniumConfig();
            try {
                seleniumConfig.init(suite.getParameter("webDriverUrl"), suite.getParameter("appUrl"));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            seleniumConfig.printSeleniumSessionId(tr.getTestClass().getName());
            configs.set(seleniumConfig);
        }
    }

    @Override
    public void onConfigurationSuccess(ITestResult tr) {
        if (tr.getMethod().isAfterClassConfiguration()) {
            logger.info("FINISHING TEST CLASS [{}] >>> >>>", tr.getTestClass().getName());
            configs.get().destroy();
        }
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("TEST FAIL [{}] >>>", result.getMethod().getMethodName());
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("TEST START [{}] >>>", result.getMethod().getMethodName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }


    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {
    }

    @Override
    public void onConfigurationFailure(ITestResult itr) {
    }

    @Override
    public void onConfigurationSkip(ITestResult itr) {
    }

    private final ThreadLocal<JCommuneSeleniumConfig> configs = new ThreadLocal<JCommuneSeleniumConfig>();
    private final Logger logger = LoggerFactory.getLogger(SeleniumSessionListener.class);
}
