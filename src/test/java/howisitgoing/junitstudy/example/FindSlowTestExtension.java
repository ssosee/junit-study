package howisitgoing.junitstudy.example;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;

import java.lang.reflect.Method;

public class FindSlowTestExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private final long THREASHOLD;

    public FindSlowTestExtension(long THREASHOLD) {
        this.THREASHOLD = THREASHOLD;
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) throws Exception {
        ExtensionContext.Store store = getStore(context);
        store.put("START_TIME", System.currentTimeMillis());
    }

    @Override
    public void afterTestExecution(ExtensionContext context) throws Exception {
        String classMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = getStore(context);

        long startTime = (long) store.remove("START_TIME");
        long duration = System.currentTimeMillis() - startTime;

        // 리플렉션 이용
        Method requiredTestMethod = context.getRequiredTestMethod();
        SlowTest annotation = requiredTestMethod.getAnnotation(SlowTest.class);

        if(duration > THREASHOLD && annotation == null) {
            System.out.printf("Please consider mark method [%s] with @SlowTest\n", classMethodName);
        }
    }

    private static Store getStore(ExtensionContext context) {
        String className = context.getRequiredTestClass().getName();
        String classMethodName = context.getRequiredTestMethod().getName();
        ExtensionContext.Store store = context.getStore(ExtensionContext.Namespace.create(className, classMethodName));

        return store;
    }
}
