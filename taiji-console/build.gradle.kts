plugins {
    application
}

application {
    mainClassName = "com.networknt.taiji.console.Cli"
}

dependencies {
    compile(project(":taiji-crypto"))
    compile(project(":taiji-client"))
    compile("org.slf4j:slf4j-api:1.7.25")
    compile("com.networknt:service:1.5.20")
    compile("org.web3j:console:3.6.0")
    compile("com.fasterxml.jackson.core:jackson-databind:2.9.5")
    testCompile("junit:junit:4.12")
    testCompile("ch.qos.logback:logback-classic:1.2.3")
}


val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "com.networknt.taiji.console.Cli"
    }
    from(configurations.runtime.map { if (it.isDirectory) it else zipTree(it)}) {
        exclude("META-INF/*.SF", "META-INF/*.DSA", "META-INF/*.DSA")
    }
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}

