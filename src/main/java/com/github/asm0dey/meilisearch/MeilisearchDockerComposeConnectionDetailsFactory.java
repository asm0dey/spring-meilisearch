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

import org.springframework.boot.docker.compose.core.RunningService;
import org.springframework.boot.docker.compose.service.connection.DockerComposeConnectionDetailsFactory;
import org.springframework.boot.docker.compose.service.connection.DockerComposeConnectionSource;

import java.net.InetSocketAddress;
import java.util.Optional;

/**
 * Factory class for creating {@link MeilisearchConnectionDetails} instances specific
 * to Docker Compose-based Meilisearch services.
 * <br/>
 * This class extends {@link DockerComposeConnectionDetailsFactory} and provides the
 * configurations required to connect to a Meilisearch instance running in a Docker
 * Compose environment.
 * <br/>
 * The main responsibilities of this factory are:
 * <ol>
 * <li>Identifying the running Meilisearch service within the Docker Compose environment.</li>
 * <li>Extracting the connection details (host, port, and key) required to access the Meilisearch service.</li>
 * </ol>
 */
public class MeilisearchDockerComposeConnectionDetailsFactory
        extends DockerComposeConnectionDetailsFactory<MeilisearchConnectionDetails> {
    /**
     * Constructs a new instance of {@code MeilisearchDockerComposeConnectionDetailsFactory}.
     * This factory is used to create instances of {@link MeilisearchConnectionDetails}
     * for Meilisearch services running in a Docker Compose environment.
     * <p>
     * These parameters help determine the service representation and required connection details.
     */
    public MeilisearchDockerComposeConnectionDetailsFactory() {
        super("getmeili/meilisearch", com.meilisearch.sdk.Client.class.getName());
    }

    @Override
    public MeilisearchConnectionDetails getDockerComposeConnectionDetails(DockerComposeConnectionSource source) {
        return new MeilisearchDockerComposeConnectionDetails(source.getRunningService());
    }

    /**
     * Represents the connection details for a Meilisearch service running in a Docker
     * Compose environment. This class provides the host address and authentication key
     * necessary for communicating with the Meilisearch instance.
     * <p>
     * Extends {@link DockerComposeConnectionDetails} to leverage Docker Compose-specific
     * connection configuration functionality. Implements {@link MeilisearchConnectionDetails}
     * to provide a consistent interface for retrieving Meilisearch-specific connection information.
     * <p>
     * This class extracts the following details from the Docker Compose service:
     * <ul>
     * <li>Host address (defaulting to "localhost" if unavailable).</li>
     * <li>Port number (specifically port 7700 for Meilisearch communication).</li>
     * <li>Authentication key from the MEILI_MASTER_KEY environment variable.</li>
     * </ul>
     */
    public static class MeilisearchDockerComposeConnectionDetails
            extends DockerComposeConnectionDetails
            implements MeilisearchConnectionDetails {

        private final RunningService runningService;

        MeilisearchDockerComposeConnectionDetails(RunningService runningService) {
            super(runningService);
            this.runningService = runningService;
        }

        @Override
        public InetSocketAddress address() {
            return new InetSocketAddress(
                    Optional.ofNullable(runningService.host()).orElse("localhost"),
                    runningService.ports().get(7700)
            );
        }

        @Override
        public String key() {
            return runningService.env().get("MEILI_MASTER_KEY");
        }
    }
}