package org.jtalks.tests.jcommune.webdriver;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.StepAnnotations;
import net.thucydides.core.steps.StepEventBus;
import net.thucydides.core.steps.StepFactory;
import net.thucydides.core.webdriver.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;


public class TestNgThucydidesListener implements ITestListener, IInvokedMethodListener {
    private final Logger logger = LoggerFactory.getLogger(TestNgThucydidesListener.class);
    /**
     * TestNG runs tests in groups split by class name. So first it runs all the tests in class 1, then in class 2. We
     * change the value of this field when first method of class 2 is invoked - this is a sign that we need to start a
     * new selenium session.
     */
    private volatile Object currentTestClassInstance;
    private volatile StepEventBus thucydidesStepEventBus;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        Object testClassInstance = method.getTestMethod().getInstance();
        if (testClassInstance != currentTestClassInstance) {
            logger.info("Initializing Thucydides for class {}", testClassInstance.getClass());
            currentTestClassInstance = testClassInstance;
            thucydidesStepEventBus = StepEventBus.getEventBus();
            thucydidesStepEventBus.clear();
            Pages pages = new Pages(JCommuneSeleniumConfig.driver, Injectors.getInjector().getInstance(Configuration.class));
            StepFactory stepFactory = new StepFactory(pages);
            StepAnnotations.injectScenarioStepsInto(testClassInstance, stepFactory);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onTestStart(ITestResult result) {
        thucydidesStepEventBus.testStarted(result.getTestName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        thucydidesStepEventBus.testFinished();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        thucydidesStepEventBus.testFailed(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        thucydidesStepEventBus.testIgnored();
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onStart(ITestContext context) {
    }

    @Override
    public void onFinish(ITestContext context) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
