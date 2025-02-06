package com.github.asm0dey.opdsko_spring.meilisearch;

import java.net.InetSocketAddress;

/**
 * Implementation of {@link MeilisearchConnectionDetails} that retrieves connection details
 * from {@link MeilisearchProperties}.
 */
public class MeilisearchPropertiesConnectionDetails implements MeilisearchConnectionDetails {
    private final MeilisearchProperties properties;

    /**
     * Constructs a new instance of {@code MeilisearchPropertiesConnectionDetails},
     * which retrieves connection details for a Meilisearch instance from the provided
     * {@link MeilisearchProperties} object.
     *
     * @param properties the {@link MeilisearchProperties} 
     */
    public MeilisearchPropertiesConnectionDetails(MeilisearchProperties properties) {
        this.properties = properties;
    }

    @Override
    public InetSocketAddress address() {
        if (properties.host().isBlank()) throw new IllegalStateException("Meilisearch host is blank");
        return new InetSocketAddress(
                properties.host(),
                properties.port()
        );
    }

    @Override
    public String key() {
        return properties.apiKey();
    }
}
