import io.papermc.paperweight.PaperweightSourceGeneratorHelper
import io.papermc.paperweight.extension.PaperweightSourceGeneratorExt

plugins {
    java
}

plugins.apply(PaperweightSourceGeneratorHelper::class)

extensions.configure(PaperweightSourceGeneratorExt::class) {
    atFile.set(file("wideners.at"))
}

repositories {
    mavenLocal() // todo publish typewriter somewhere
}

val testData = sourceSets.create("testData")

dependencies {
    implementation("com.squareup:javapoet:1.13.0")
    implementation(project(":paper-api"))
    implementation("io.github.classgraph:classgraph:4.8.47")
    implementation("org.jetbrains:annotations:24.0.1")
    implementation("io.papermc.typewriter:typewriter:1.0-SNAPSHOT") {
        isTransitive = false // paper-api already have everything
    }
    testImplementation("org.junit.jupiter:junit-jupiter:5.10.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation(testData.output)
}

val generatedApiPath = file("generatedApi")
val generatedServerPath = file("generatedServer")

tasks.register<JavaExec>("generate") {
    dependsOn(tasks.check)
    mainClass.set("io.papermc.generator.Main")
    classpath(sourceSets.main.map { it.runtimeClasspath })
    args(generatedApiPath.toString(),
        project(":paper-api").sourceSets["main"].java.srcDirs.first().toString(),
        generatedServerPath.toString(),
        project(":paper-server").sourceSets["main"].java.srcDirs.first().toString())
}

tasks.register<JavaExec>("scanOldGeneratedSourceCode") {
    mainClass.set("io.papermc.generator.rewriter.OldGeneratedCodeTest")
    classpath(sourceSets.test.map { it.runtimeClasspath })
    args(generatedApiPath.toString(),
        generatedServerPath.toString())
}

tasks {
    test {
        useJUnitPlatform {
            if (false && System.getenv()["CI"]?.toBoolean() == true) {
                // the CI shouldn't run the test since it's not included by default but just in case this is moved to its own repo
                excludeTags("parser")
            } else {
                // excludeTags("parser") // comment this line while working on parser related things
            }
        }
        inputs.files(testData.output.files)
    }

    compileTestJava {
        options.compilerArgs.add("-parameters")
    }
}

group = "io.papermc.paper"
version = "1.0-SNAPSHOT"
