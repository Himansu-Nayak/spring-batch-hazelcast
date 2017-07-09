package com.org.spring.batch.watcher;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.io.File;

public class FolderCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        String folderPath = conditionContext.getEnvironment().getProperty("watched.path.drop");
        File folder = new File(folderPath);
        return folder.exists();
    }
}