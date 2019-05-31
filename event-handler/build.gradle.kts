plugins {
    java
    maven
}

dependencies {
    compile(project(":event-common"))



    compile("org.slf4j:slf4j-api:1.7.25")
    compile("com.networknt:service:1.5.28")
    compile("com.fasterxml.jackson.core:jackson-databind:2.9.5")
    testCompile("junit:junit:4.12")
    testCompile("ch.qos.logback:logback-classic:1.2.3")

    val slf4jVersion: String by project
    compile("org.slf4j", "slf4j-api", slf4jVersion)

    val light4jVersion: String by project
    compile("com.networknt", "service", light4jVersion)

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
    val mockitoVersion: String by project
    testImplementation("org.mockito", "mockito-all", mockitoVersion)
}

