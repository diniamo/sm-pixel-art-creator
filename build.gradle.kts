import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("application")
    id("org.openjfx.javafxplugin") version "0.0.8"
    id("org.beryx.runtime") version "1.12.2"

    kotlin("jvm") version "1.4.31"
}

group = "me.diniamo"
version = "1.0-SNAPSHOT"

application {
    mainClass.set("me.diniamo.pixelartcreator.MyAppKt")
}

javafx {
    version = "11.0.2"
    modules = listOf("javafx.controls", "javafx.graphics")
}

repositories {
    mavenCentral()
}

val javaFXOptions = the<org.openjfx.gradle.JavaFXOptions>()
dependencies {
    implementation("com.beust", "klaxon", "5.5")
    implementation("de.codecentric.centerdevice", "javafxsvg", "1.3.0")

    implementation("no.tornado", "tornadofx", "1.7.20") {
        exclude("org.jetbrains.kotlin")
    }

    org.openjfx.gradle.JavaFXPlatform.values().forEach { platform ->
        val cfg = configurations.create("javafx_" + platform.classifier)
        org.openjfx.gradle.JavaFXModule.getJavaFXModules(javaFXOptions.modules).forEach { m ->
            project.dependencies.add(cfg.name,
                String.format("org.openjfx:%s:%s:%s", m.artifactName, javaFXOptions.version, platform.classifier));
        }
    }
}

sourceSets {
    main {
        java.srcDir("src/main/kotlin")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "me.diniamo.pixelartcreator.MyAppKt"
    }

    // To add all of the dependencies
    from(sourceSets.main.get().output)

    dependsOn(configurations.runtimeClasspath)
    from({
        configurations.runtimeClasspath.get().filter { it.name.endsWith("jar") }.map { zipTree(it) }
    })
}


runtime {
    imageZip.set(project.file("${project.buildDir}/image-zip/hello-image.zip"))
    options.set(listOf("--strip-debug", "--compress", "2", "--no-header-files", "--no-man-pages"))
    modules.set(listOf("java.desktop", "jdk.unsupported", "java.scripting", "java.logging", "java.xml"))

//    targetPlatform("linux", System.getenv("JDK_LINUX_HOME"))
//    targetPlatform("mac", System.getenv("JDK_MAC_HOME"))
//    targetPlatform("win", System.getenv("JDK_WIN_HOME"))
}

tasks.withType(CreateStartScripts::class).forEach { script ->
    script.doFirst {
        script.classpath = files("lib/*")
    }
}

tasks["runtime"].doLast {
//    (this as org.beryx.runtime.RuntimeTask).
    val cfg = configurations["javafx_win"]
    cfg.resolvedConfiguration.files.forEach { f ->
        copy {
            from(f)
            into("build/image/hello-windows/lib")
        }
    }
}