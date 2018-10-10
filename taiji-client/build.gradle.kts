plugins {
    application
}

application {
    mainClassName = "com.networknt.taiji.client.Cli"
}

dependencies {
    compile(project(":taiji-core"))
    compile(project(":taiji-crypto"))
    compile("com.networknt:client:1.5.20")
    compile("com.beust:jcommander:1.72")
    compile("ch.qos.logback:logback-classic:1.2.3")
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "Gradle Jar File Example"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "com.networknt.taiji.client.Cli"
    }
    from(configurations.runtime.map({ if (it.isDirectory) it else zipTree(it) }))
    with(tasks["jar"] as CopySpec)
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}
