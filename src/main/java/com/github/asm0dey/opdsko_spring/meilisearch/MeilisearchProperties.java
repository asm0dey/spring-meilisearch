package com.github.asm0dey.opdsko_spring.meilisearch;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for connecting to a Meilisearch instance.
 * <p>
 * This record is loaded with values from the application's configuration
 * using the prefix "meilisearch".
 * <p>
 *
 * @param apiKey The API key used for authenticating requests to the Meilisearch instance.
 * @param host   The hostname or IP address of the Meilisearch server.
 * @param port   The port number on which the Meilisearch server is running.
 */
@ConfigurationProperties(prefix = "meilisearch")
public record MeilisearchProperties(String apiKey, String host, int port) {
    /**
     * Constructor for the {@code MeilisearchProperties} record, used to initialize
     * properties for connecting to a Meilisearch instance. Ensures that the host and
     * apiKey fields are not null to prevent NullPointerExceptions during usage.
     *
     * @param apiKey The API key used for authenticating requests to the Meilisearch instance.
     *               If null, it defaults to an empty string.
     * @param host   The hostname or IP address of the Meilisearch server. If null, it defaults
     *               to an empty string.
     * @param port   The port number on which the Meilisearch server is running.
     */
    public MeilisearchProperties {
        if (host == null) {
            host = "";
        }
        if (apiKey == null) {
            apiKey = "";
        }
    }
}
