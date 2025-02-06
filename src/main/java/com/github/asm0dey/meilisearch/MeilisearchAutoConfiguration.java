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