package com.org.spring.batch.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Configuration
@Slf4j
public class WatchServiceConfig {

    @Value("${watched.path.drop}")
    private String dirPath;

    @Conditional(FolderCondition.class)
    @Bean
    public WatchService watchService() throws IOException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Paths.get(dirPath).register(watchService, ENTRY_CREATE);
        log.info("Started watching directory: " + dirPath);
        return watchService;
    }

}