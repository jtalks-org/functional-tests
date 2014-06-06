package org.jtalks.tests.jcommune.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Reporter;
import org.testng.annotations.*;
import ru.yandex.qatools.allure.events.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import static ru.yandex.qatools.allure.Allure.LIFECYCLE;

public class ReportNgLogger {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReportNgLogger.class);
    public static void info(String toLog) {
        LOGGER.info(toLog);
        LIFECYCLE.fire(new StepStartedEvent(toLog));
        LIFECYCLE.fire(new StepFinishedEvent());
        String logPrefix = "|" + StringUtils.repeat("_", stackTraceDepth() - 3);//-3 because current methods also count
        Reporter.log(logPrefix + toLog);
    }

    private static int stackTraceDepth() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        for (int i = 2; i < stackTrace.length; i++) {//skip current method and getStackTrace() method
            StackTraceElement traceElement = stackTrace[i];
            try {
                Class<?> aClass = Class.forName(traceElement.getClassName());
                Method[] declaredMethods = aClass.getDeclaredMethods();
                for (Method method : declaredMethods) {
                    if (method.getName().equals(traceElement.getMethodName())) {
                        if (annotatedWithTestNgAnnotations(method)) {
                            return i;
                        }
                    }
                }
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException(e);
            }
        }
        return 0;
    }

    private static boolean annotatedWithTestNgAnnotations(Method method) {
        List<Class<? extends Annotation>> testNgAnnotations = Arrays.asList(Test.class, BeforeMethod.class,
                AfterMethod.class, BeforeClass.class, AfterClass.class, BeforeSuite.class, AfterSuite.class);
        for (Class<? extends Annotation> testNgAnnotation : testNgAnnotations) {
            if (method.getAnnotation(testNgAnnotation) != null) {
                return true;
            }
        }
        return false;
    }

}
