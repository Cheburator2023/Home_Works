import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id ("com.github.johnrengelman.shadow")
}

dependencies {
    implementation ("com.google.guava:guava")
    implementation("org.projectlombok:lombok")
    implementation("org.slf4j:slf4j-api")
}

tasks {
    named<ShadowJar>("shadowJar") {
        archiveBaseName.set("hw01-gradle")
        archiveVersion.set("0.1")
        archiveClassifier.set("DEV")
        manifest {
            attributes(mapOf("Main-Class" to "ru.otus.Main"))
        }
    }

    build {
        dependsOn(shadowJar)
    }
}
