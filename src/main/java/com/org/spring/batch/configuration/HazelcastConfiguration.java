package com.org.spring.batch.configuration;

import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IQueue;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.org.spring.batch.messaging.NotificationMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * The type Hazelcast configuration.
 */
@Configuration
@EnableCaching
public class HazelcastConfiguration {

    /**
     * Cache manager cache manager.
     *
     * @return the cache manager
     */
    @Bean
    public CacheManager cacheManager() {
        return new HazelcastCacheManager(hazelcastInstance());
    }

    /**
     * Hazelcast instance hazelcast instance.
     *
     * @return the hazelcast instance
     */
    @Bean
    public HazelcastInstance hazelcastInstance() {
        return Hazelcast.newHazelcastInstance(config());
    }


    /**
     * Messages queue queue.
     *
     * @return the queue
     */
    @Bean
    @Qualifier("messagesQueue")
    public IQueue<NotificationMessage> messagesQueue() {
        return hazelcastInstance().getQueue("notificationMessages");
    }

    /**
     * Config config.
     *
     * @return the config
     */
    @Bean
    public Config config() {
        Config config = new Config();
        return config;
    }

}
