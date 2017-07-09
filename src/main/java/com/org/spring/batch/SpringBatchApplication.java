package com.org.spring.batch;

import com.org.spring.batch.watcher.WatcherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@Slf4j
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SpringBatchApplication implements CommandLineRunner {

    @Autowired
    public WatcherService watcherService;

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(SpringBatchApplication.class, args);

        try {
            ctx.getBean("watchService");
        } catch (NoSuchBeanDefinitionException exception) {
            ctx.close();
        }
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("Watcher service running...");

        new Thread(watcherService, "watcher-service").start();
    }
}
