package com.github.asm0dey.opdsko_spring.meilisearch;

import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;

import java.net.InetSocketAddress;

/**
 * Defines the connection details required to connect to a Meilisearch instance.
 * Implementations of this interface provide information about the host address
 * and the authentication key needed for accessing a Meilisearch server.
 * <br/>
 * This interface extends {@link ConnectionDetails}, allowing it to be used in
 * Spring Boot's connection configuration framework.
 */
public interface MeilisearchConnectionDetails extends ConnectionDetails {
    /**
     * Retrieves the socket address of the Meilisearch server. The returned address combines the host
     * and port details
     *
     * @return an {@link InetSocketAddress} representing the host and port of the Meilisearch server
     */
    InetSocketAddress address();

    /**
     * Retrieves the authentication key required to access the Meilisearch server.
     *
     * @return a {@link String} representing the API key or master key used for authentication.
     */
    String key();
}
