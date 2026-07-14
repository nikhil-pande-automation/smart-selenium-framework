package com.nikhil.framework.execution;

import com.nikhil.framework.config.ConfigReader;
import com.nikhil.framework.enums.ExecutionType;

public final class ExecutionManager {

    private ExecutionManager() {
    }

    public static ExecutionType getExecutionType() {

        String executionFromJVM = System.getProperty("execution");

        if (executionFromJVM != null && !executionFromJVM.trim().isEmpty()) {
            return ExecutionType.valueOf(
                    executionFromJVM.trim().toUpperCase()
            );
        }

        return ConfigReader.getInstance().getExecutionType();
    }
}