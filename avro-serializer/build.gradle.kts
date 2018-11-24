plugins {
    java
    maven

}

repositories {
    maven(url = "https://packages.confluent.io/maven/")
}

dependencies {
    compile(project(":event-common"))
    compile(project(":taiji-token"))
    compile("org.slf4j:slf4j-api:1.7.25")
    compile("com.networknt:config:1.5.22")
    compile("org.apache.avro:avro:1.8.2")
    compile("io.confluent:kafka-schema-registry-client:5.0.1")
    testCompile("junit:junit:4.12")
    testCompile("ch.qos.logback:logback-classic:1.2.3")
}

