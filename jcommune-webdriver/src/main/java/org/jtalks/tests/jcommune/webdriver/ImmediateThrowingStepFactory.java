package org.jtalks.tests.jcommune.webdriver;

import net.sf.cglib.proxy.MethodInterceptor;
import net.thucydides.core.pages.Pages;
import net.thucydides.core.steps.StepFactory;
import net.thucydides.core.steps.StepInterceptor;

public class ImmediateThrowingStepFactory extends StepFactory {
    public ImmediateThrowingStepFactory(Pages pages) {
        super(pages);
    }

    public ImmediateThrowingStepFactory() {
    }
    
    @Override
    public <T> T instantiateNewStepLibraryFor(Class<T> scenarioStepsClass) {
        StepInterceptor stepInterceptor = new NotCatchingStepInterceptor(scenarioStepsClass);
        stepInterceptor.setThowsExceptionImmediately(true);
        return instantiateNewStepLibraryFor(scenarioStepsClass, stepInterceptor);
    }
}
