plugins {
    java
    maven
}

dependencies {
    compile("org.slf4j:slf4j-api:1.7.25")
    compile("com.networknt:utility:1.5.21")
    testCompile("junit:junit:4.12")
    testCompile("ch.qos.logback:logback-classic:1.2.3")
}
