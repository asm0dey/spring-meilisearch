/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.github.asm0dey.meilisearch;

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
