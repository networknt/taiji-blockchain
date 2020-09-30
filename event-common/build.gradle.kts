plugins {
    java
    maven
}

dependencies {

    val slf4jVersion: String by project
    compile("org.slf4j", "slf4j-api", slf4jVersion)

    val light4jVersion: String by project
    compile("com.networknt", "service", light4jVersion)

    val jacksonVersion: String by project
    compile("com.fasterxml.jackson.core", "jackson-databind", jacksonVersion)

    val avroVersion: String by project
    compile("org.apache.avro", "avro", avroVersion)

    val junitVersion: String by project
    testImplementation("org.junit.jupiter", "junit-jupiter-api", junitVersion)
    testImplementation("org.junit.jupiter", "junit-jupiter-params", junitVersion)
    testRuntimeOnly("org.junit.jupiter", "junit-jupiter-engine", junitVersion)
    val logbackVersion: String by project
    testImplementation("ch.qos.logback", "logback-classic", logbackVersion)
    val hamcrestVersion: String by project
    testImplementation("org.hamcrest", "hamcrest-library", hamcrestVersion)
}

tasks.test {
    useJUnitPlatform()
}

