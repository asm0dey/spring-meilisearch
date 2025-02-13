![Maven Central Version](https://img.shields.io/maven-central/v/com.github.asm0dey/spring-meilisearch?style=for-the-badge&logo=meilisearch&link=https%3A%2F%2Fcentral.sonatype.com%2Fartifact%2Fcom.github.asm0dey%2Fspring-meilisearch%2Foverview)

# Spring Boot Meilisearch Autoconfiguration
This library provides Spring Boot autoconfiguration for [Meilisearch](https://www.meilisearch.com/), making it easy to integrate Meilisearch search engine into your Spring Boot applications.
## Overview
Spring Boot Meilisearch Autoconfiguration enables automatic configuration of Meilisearch client in Spring Boot applications, with support for:
- Automatic configuration of Meilisearch client
- Docker Compose integration
- Property-based configuration
- Spring Boot 3.x compatibility

## Installation
Add the dependency to your project:
```kotlin
dependencies {
    implementation("com.github.asm0dey:spring-meilisearch:VERSION")
}
```

```xml
<dependency>
    <groupId>com.github.asm0dey</groupId>
    <artifactId>spring-meilisearch</artifactId>
    <version>VERSION</version>
</dependency>
```
## Configuration
You can configure Meilisearch in two ways:
### 1. Using properties
Add the following to your `application.properties` or `application.yml`:
```properties
meilisearch.host=http://localhost:7700
meilisearch.api-key=your_api_key
```
### 2. Using Docker Compose
If you're using Spring Boot's Docker Compose support, the library will automatically configure Meilisearch based on your `compose.yml` configuration.
## Usage
Once configured, you can autowire the Meilisearch client in your components:
```java
@Service
public class SearchService {
    private final Client meilisearchClient;

    public SearchService(Client meilisearchClient) {
        this.meilisearchClient = meilisearchClient;
    }
    
    // Use the client for search operations
}
```
## Requirements
- Java 17 or later
- Spring Boot 3.x
- Meilisearch server (or Docker)

## License
This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.
## Contributing
Contributions are welcome! Please feel free to submit a Pull Request.
1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request
