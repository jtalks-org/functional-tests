package org.jtalks.tests.jcommune.webdriver;

import net.thucydides.core.guice.Injectors;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.reports.ReportService;
import net.thucydides.core.reports.html.HtmlAcceptanceTestReporter;
import net.thucydides.core.steps.*;
import net.thucydides.core.webdriver.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.jetty.util.ArrayQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.*;

import java.util.Arrays;
import java.util.Queue;


public class TestNgThucydidesListener implements ITestListener, IInvokedMethodListener, ISuiteListener {
    private final Logger logger = LoggerFactory.getLogger(TestNgThucydidesListener.class);
    /**
     * TestNG runs tests in groups split by class name. So first it runs all the tests in class 1, then in class 2. We
     * change the value of this field when first method of class 2 is invoked - this is a sign that we need to start a
     * new selenium session.
     */
    private volatile Object currentTestClassInstance;
    private volatile StepEventBus thucydidesStepEventBus;
    private volatile BaseStepListener thucydidesBaseStepListener;
    private volatile Configuration configuration;
    private volatile ReportService reportService;
    /**
     * Because TestNG does not provide much of information in
     * {@link #beforeInvocation(org.testng.IInvokedMethod, org.testng.ITestResult)},
     * we need to save this info before any of class methods are invoked.
     */
    private Queue<ITestNGMethod> currentClassMethods = new ArrayQueue<>();

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        Object testClassInstance = method.getTestMethod().getInstance();
        if (testClassInstance != currentTestClassInstance) {
            logger.debug("Initializing Thucydides for class {}", testClassInstance.getClass());
            currentTestClassInstance = testClassInstance;
            Pages pages = new Pages(JCommuneSeleniumConfig.driver, configuration);
            thucydidesBaseStepListener = Listeners.getBaseStepListener()
                    .withPages(pages)
                    .and().withOutputDirectory(configuration.getOutputDirectory());
            thucydidesStepEventBus = StepEventBus.getEventBus().registerListener(thucydidesBaseStepListener);
            thucydidesStepEventBus.testSuiteStarted(currentTestClassInstance.getClass());
            StepFactory stepFactory = new ImmediateThrowingStepFactory(pages).thatThrowsExcpetionsImmediately();
            StepAnnotations.injectScenarioStepsInto(testClassInstance, stepFactory);

            reportService = new ReportService(configuration.getOutputDirectory(), ReportService.getDefaultReporters());
            reportService.subscribe(new HtmlAcceptanceTestReporter());
        }
        if (!method.isConfigurationMethod() && !method.isTestMethod()) {
            ITestNGMethod testMethod = currentClassMethods.peek();
            String testCaseName = testMethod.getDescription();
            if(StringUtils.isEmpty(testCaseName)){
                testCaseName = testMethod.getMethodName();
            }
            thucydidesStepEventBus.testStarted(testCaseName);
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void onTestStart(ITestResult result) {
        currentClassMethods.poll();
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        reportService.generateReportsFor(thucydidesBaseStepListener.getTestOutcomes());
        thucydidesStepEventBus.testFinished();
    }

    @Override
    public void onTestFailure(ITestResult result) {
        reportService.generateReportsFor(thucydidesBaseStepListener.getTestOutcomes());
        thucydidesStepEventBus.testFailed(result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
//        thucydidesStepEventBus.testIgnored();
    }

    @Override
    public void onStart(ITestContext context) {
        currentClassMethods.clear();
        currentClassMethods.addAll(Arrays.asList(context.getAllTestMethods()));
    }

    @Override
    public void onFinish(ITestContext context) {
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    @Override
    public void onStart(ISuite suite) {
        configuration = Injectors.getInjector().getInstance(Configuration.class);
    }

    @Override
    public void onFinish(ISuite suite) {
        thucydidesStepEventBus.testSuiteFinished();
    }
}
