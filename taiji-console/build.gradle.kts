plugins {
    application
    maven
    java
    id("com.github.johnrengelman.shadow") version "5.0.0"
}

application {
    mainClassName = "com.networknt.taiji.console.Cli"
}

repositories {
    maven(url = "https://packages.confluent.io/maven/")
}

dependencies {
    compile(project(":taiji-crypto"))
    compile(project(":taiji-client"))
    compile(project(":taiji-utility"))
    compile(project(":taiji-token"))

    val slf4jVersion: String by project
    compile("org.slf4j", "slf4j-api", slf4jVersion)

    val light4jVersion: String by project
    compile("com.networknt", "service", light4jVersion)
    compile("com.networknt", "kafka-common", light4jVersion)

    val jacksonVersion: String by project
    compile("com.fasterxml.jackson.core", "jackson-databind", jacksonVersion)

    val junitVersion: String by project
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testImplementation("org.junit.jupiter", "junit-jupiter-params", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
    val logbackVersion: String by project
    testImplementation("ch.qos.logback", "logback-classic", logbackVersion)
    val hamcrestVersion: String by project
    testImplementation("org.hamcrest", "hamcrest-library", hamcrestVersion)
}
