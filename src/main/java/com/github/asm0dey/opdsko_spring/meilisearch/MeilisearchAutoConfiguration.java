package com.github.asm0dey.opdsko_spring.meilisearch;

import com.meilisearch.sdk.Client;
import com.meilisearch.sdk.Config;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Autoconfiguration class for integrating Meilisearch into a Spring Boot application.
 * <p>
 * This class configures the required beans for communicating with a Meilisearch instance.
 * It enables configuration properties for Meilisearch, and conditionally defines beans
 * depending on the presence of specific classes or beans within the application context.
 * <p>
 * The main responsibilities of this configuration are:
 * <ul>
 * <li>Creating a {@link MeilisearchConnectionDetails} bean if none exists, using the properties
 *   {@link MeilisearchProperties}</li>
 * <li>Creating a {@link Client} bean to interact with the Meilisearch instance using
 *   the connection details.</li>
 * </ul>
 */
@AutoConfiguration
@ConditionalOnClass(Client.class)
@EnableConfigurationProperties(MeilisearchProperties.class)
public class MeilisearchAutoConfiguration {
    MeilisearchAutoConfiguration() {
    }

    @Bean
    @ConditionalOnMissingBean(MeilisearchConnectionDetails.class)
    MeilisearchConnectionDetails memcachedConnectionDetails(MeilisearchProperties props) {
        return new MeilisearchPropertiesConnectionDetails(props);
    }

    @Bean
    Client meiliSearch(MeilisearchConnectionDetails connectionDetails) {
        return new Client(new Config("http://" + connectionDetails.address(), connectionDetails.key()));
    }
}