plugins {
    java
    `maven-publish`
    signing
    id("org.cyclonedx.bom") version "2.1.0"
    id("org.scm-manager.license") version "0.8.0"
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.spring.boot.autoconfigure)
    compileOnly(libs.spring.boot.docker.compose)
    compileOnly(libs.meilisearch)
    testImplementation(libs.meilisearch)
    testImplementation(libs.spring.boot.test)
    testImplementation(libs.spring.boot.autoconfigure)
    testImplementation(libs.junit.jupiter)
    testImplementation(libs.assertj.core)
}
group = "com.github.asm0dey"
version = "0.0.1"

tasks.test {
    useJUnitPlatform()
}


val javadocJar by tasks.registering(Jar::class) {
    from(tasks.javadoc)
    archiveClassifier.set("javadoc")
}

val sourcesJar by tasks.registering(Jar::class) {
    from(sourceSets.main.get().allSource)
    archiveClassifier.set("sources")
}


publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            from(components["java"])
            groupId = "com.github.asm0dey"
            artifactId = "spring-meilisearch"
            version = project.version as String?

            artifact(javadocJar.get())
            artifact(sourcesJar.get())

            pom {
                name.set("Spring Meilisearch")
                description.set("Spring Boot integration for Meilisearch")
                url.set("https://github.com/asm0dey/spring-meilisearch")
                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        id.set("asm0dey")
                        name.set("Pasha FInkelshteyn")
                        email.set("pavel.finkelshtein@gmail.cmo")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/asm0dey/spring-meilisearch.git")
                    developerConnection.set("scm:git:ssh://github.com:asm0dey/spring-meilisearch.git")
                    url.set("https://github.com/asm0dey/spring-meilisearch")
                }
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/asm0dey/spring-meilisearch")
            credentials {
                username = project.findProperty("gpr.user") as String? ?: System.getenv("USERNAME")
                password = project.findProperty("gpr.token") as String? ?: System.getenv("TOKEN")
            }
        }
        maven {
            name = "MavenCentral"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.findProperty("ossrhUsername") as String? ?: System.getenv("OSSRH_USERNAME")!!
                password = project.findProperty("ossrhPassword") as String? ?: System.getenv("OSSRH_PASSWORD")!!
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
    useGpgCmd()
}

tasks.cyclonedxBom {
    includeConfigs = listOf("runtimeClasspath")
    skipConfigs = listOf("compileClasspath", "testCompileClasspath")
    projectType = "library"
    schemaVersion = "1.6"
    destination = project.file("build/reports")
    outputName = "bom"
    outputFormat = "json"
    includeBomSerialNumber = false
    includeLicenseText = true
    includeMetadataResolution = true
    componentVersion = version.toString()
    componentName = "spring-meilisearch"
}

license {
    header = resources.text.fromFile(project.file("HEADER.txt"))
}