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
