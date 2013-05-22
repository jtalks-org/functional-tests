package org.jtalks.tests.jcommune.webdriver;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

/** @author stanislav bashkirtsev */
public class SeleniumSessionListener implements ITestListener {
    @Override
    public void onTestStart(ITestResult result) {

    }

    @Override
    public void onTestSuccess(ITestResult result) {

    }

    @Override
    public void onTestFailure(ITestResult result) {

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {
        JCommuneSeleniumConfig seleniumConfig = new JCommuneSeleniumConfig();
        try {
            seleniumConfig.init(
                    context.getSuite().getParameter("webDriverUrl"),
                    context.getSuite().getParameter("appUrl"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        seleniumConfig.printSeleniumSessionId(context.getName());
        configs.set(seleniumConfig);
    }

    @Override
    public void onFinish(ITestContext context) {
        configs.get().destroy();
    }

    private final ThreadLocal<JCommuneSeleniumConfig> configs = new ThreadLocal<JCommuneSeleniumConfig>();
}
