plugins {
    java
    maven
}


dependencies {
    compile(project(":taiji-core"))
    compile(project(":taiji-crypto"))
    compile("com.networknt:client:1.5.22")
    compile("com.networknt:cluster:1.5.22")
    compile("com.networknt:balance:1.5.22")
    compile("com.networknt:consul:1.5.22")
    compile("com.networknt:monad-result:1.5.22")
    compile("com.beust:jcommander:1.72")
    compile("ch.qos.logback:logback-classic:1.2.3")
}
