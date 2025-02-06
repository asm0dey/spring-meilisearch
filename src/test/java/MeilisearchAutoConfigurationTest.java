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

import com.github.asm0dey.opdsko_spring.meilisearch.MeilisearchAutoConfiguration;
import com.github.asm0dey.opdsko_spring.meilisearch.MeilisearchConnectionDetails;
import com.meilisearch.sdk.Client;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import java.net.InetSocketAddress;

import static org.assertj.core.api.Assertions.assertThat;

class MeilisearchAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(MeilisearchAutoConfiguration.class));

    @Test
    void shouldNotConfigureWhenClientClassIsMissing() {
        new ApplicationContextRunner()
                .run(context ->
                        assertThat(context).doesNotHaveBean(Client.class));
    }

    @Test
    void shouldConfigureDefaultConnectionDetails() {
        contextRunner
                .withPropertyValues(
                        "meilisearch.host=localhost",
                        "meilisearch.port=7700",
                        "meilisearch.api-key=test-key"
                )
                .run(context -> {
                    assertThat(context).hasSingleBean(MeilisearchConnectionDetails.class);
                    assertThat(context).hasSingleBean(Client.class);

                    MeilisearchConnectionDetails details = context.getBean(MeilisearchConnectionDetails.class);
                    assertThat(details.address().getHostName()).isEqualTo("localhost");
                    assertThat(details.address().getPort()).isEqualTo(7700);
                    assertThat(details.key()).isEqualTo("test-key");
                });
    }

    @Test
    void shouldUseCustomConnectionDetails() {
        contextRunner
                .withBean(MeilisearchConnectionDetails.class, () ->
                        new MeilisearchConnectionDetails() {
                            @Override
                            public InetSocketAddress address() {
                                return new InetSocketAddress("custom-host", 7701);
                            }

                            @Override
                            public String key() {
                                return "custom-key";
                            }
                        })
                .run(context -> {
                    assertThat(context).hasSingleBean(MeilisearchConnectionDetails.class);
                    assertThat(context).hasSingleBean(Client.class);

                    MeilisearchConnectionDetails details = context.getBean(MeilisearchConnectionDetails.class);
                    assertThat(details.address().getHostName()).isEqualTo("custom-host");
                    assertThat(details.address().getPort()).isEqualTo(7701);
                    assertThat(details.key()).isEqualTo("custom-key");
                });
    }

    @Test
    void shouldFailWithoutRequiredProperties() {
        contextRunner
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure())
                            .isInstanceOf(BeanCreationException.class)
                            .rootCause()
                            .hasSameClassAs(new IllegalStateException("Meilisearch host is blank"))
                            .hasMessage("Meilisearch host is blank");
                });
    }

    @Test
    void shouldFailWithMeaningfulErrorWhenPortOutOfRange() {
        contextRunner
                .withPropertyValues(
                        "meilisearch.host=localhost",
                        "meilisearch.port=99999" // invalid port
                )
                .run(context -> {
                    assertThat(context).hasFailed();
                    assertThat(context.getStartupFailure())
                            .isInstanceOf(BeanCreationException.class)
                            .hasMessageContaining("99999")
                            .hasMessageContaining("port");
                });
    }

}