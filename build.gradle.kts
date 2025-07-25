object Constants {
    const val MINECRAFT_VERSION: String = "1.21.1"

    const val ARCHIVES_NAME: String = "template"
    const val PLUGIN_NAME: String = "Template"
    const val MAVEN_GROUP: String = "dev.erudites.template"

    // https://semver.org/
    const val PLUGIN_VERSION: String = "0.1.0"
}

plugins {
    id("java")
    id("xyz.jpenilla.run-paper") version ("2.3.1")
}

base {
    archivesName = Constants.ARCHIVES_NAME
}

group = Constants.MAVEN_GROUP
version = createVersionString()

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:${Constants.MINECRAFT_VERSION}-R0.1-SNAPSHOT")

    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks {
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(21)
    }

    processResources {
        val propertiesMap = mapOf(
            "version" to createVersionString(),
            "plugin_name" to Constants.PLUGIN_NAME,
            "api_version" to Constants.MINECRAFT_VERSION
        )

        inputs.properties(propertiesMap)
        filesMatching("plugin.yml") {
            expand(propertiesMap)
        }
    }

    jar {
        destinationDirectory.set(rootProject.layout.buildDirectory.dir("plugins"))
        //from("LICENSE")
    }

    runServer {
        jvmArgs(
            "-Xmx1G",
            "-Dfile.encoding=UTF-8",
            "-Dconsole.encoding=UTF-8",
            "-Dsun.stdout.encoding=UTF-8",
            "-Dsun.stderr.encoding=UTF-8"
        )
        //downloadPlugins {
        //    modrinth("commandapi", "YePtl9Tj") // 10.1.1
        //}
        minecraftVersion(Constants.MINECRAFT_VERSION)
    }
}

tasks.test {
    useJUnitPlatform()
}

fun createVersionString(): String {
    val builder = StringBuilder()

    val isReleaseBuild = project.hasProperty("build.release")
    val buildId = System.getenv("GITHUB_RUN_NUMBER")

    builder.append(Constants.PLUGIN_VERSION)

    if (!isReleaseBuild) {
        builder.append("-snapshot")

        if (buildId != null) {
            builder.append("-build.${buildId}")
        } else {
            builder.append("-local")
        }
    }

    return builder.toString()
}