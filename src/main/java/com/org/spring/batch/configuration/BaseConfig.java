package com.org.spring.batch.configuration;

import com.org.spring.batch.job.FilesJob;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing(modular = true)
public class BaseConfig {

    @Bean
    public ApplicationContextFactory fileJobs() {
        return new GenericApplicationContextFactory(FilesJob.class);
    }
}
