package org.jtalks.tests.jcommune.webdriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author stanislav bashkirtsev
 */
public class SeleniumSessionListener implements ITestListener, IInvokedMethodListener {
    @Override
    public void onTestFailure(ITestResult result) {
        logger.info("TEST FAIL [time on video {}] >>>", getTimeOnVideo(System.currentTimeMillis()));
    }

    @Override
    public void onTestStart(ITestResult result) {
        MDC.put("testMethod", result.getMethod().getMethodName());
        logger.info("TEST START [time on video {}] >>>", getTimeOnVideo(result.getStartMillis()));
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
        if (seleniumConfig != null) {
            seleniumConfig.destroy();
        }
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        ITestClass testClass = method.getTestMethod().getTestClass();
        Class testClassToBeInvoked = testClass.getRealClass();
        if (testClassToBeInvoked != currentTestClass) {
            currentTestClass = testClassToBeInvoked;
            startTime.set(System.currentTimeMillis());
            MDC.put("testClass", currentTestClass.getSimpleName());
            logger.info("STARTING TEST CLASS");
            if (seleniumConfig != null) {
                seleniumConfig.destroy();
            }

            seleniumConfig = new JCommuneSeleniumConfig();
            XmlSuite suite = testClass.getXmlTest().getSuite();
            try {
                seleniumConfig.init(suite.getParameter("webDriverUrl"), suite.getParameter("appUrl"));
            } catch (Exception e) {
                logger.error("Unexpected error during Selenium configuration.", e);
                throw new RuntimeException(e);
            }
            seleniumConfig.printSeleniumSessionId(testClass.getName());
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        MDC.remove("testMethod");
    }


    private String getTimeOnVideo(long startTimeOfMethod) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date(startTimeOfMethod - startTime.get());
            return sdf.format(date);
        } catch (Exception e) {
            logger.error("TEST METHOD. fail on getting video time >>> >>>");
        }
        return null;
    }

    private JCommuneSeleniumConfig seleniumConfig;
    private final ThreadLocal<Long> startTime = new ThreadLocal<>();
    /**
     * TestNG runs tests in groups split by class name. So first it runs all the tests in class 1, then in class 2. We
     * change the value of this field when first method of class 2 is invoked - this is a sign that we need to start a
     * new selenium session.
     */
    private Class currentTestClass;
    private final Logger logger = LoggerFactory.getLogger(SeleniumSessionListener.class);
}
