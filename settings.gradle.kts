rootProject.name = "Otus_Home_Works"
include("hw01-gradle")

include("hw04-generics")

include("hw06-annotations")

include("hw08-gc")

include("hw10-byteCodes")

include("hw12-solid")

include("hw15-structuralPatterns")

include("hw16-io")

include("hw18-jdbc")

include("hw21-cache")

include("hw22-jpql")
include("hw22-jpql:homework-template")
findProject(":hw22-jpql:homework-template")?.name = "homework-template"
include("hw24-webServer")

include("hw28-springDataJdbc")

include("hw25-di:mathTrainer")

include("hw31-executors")

include("hw32-concurrentCollections")

include("hw33-multiprocess")
include("project")
include("project:AtmEmulator")

include ("hw37-webflux:source")
include ("hw37-webflux:processor")
include ("hw37-webflux:client")
include ("hw37-webflux-chat:client-service")
include ("hw37-webflux-chat:datastore-service")

pluginManagement {
    val jgitver: String by settings
    val dependencyManagement: String by settings
    val springframeworkBoot: String by settings
    val johnrengelmanShadow: String by settings
    val jib: String by settings
    val protobufVer: String by settings
    val sonarlint: String by settings
    val spotless: String by settings
    val lombok: String by settings
    val assertj: String by settings
    val hikariCP: String by settings
    val flyway: String by settings
    val postgresql: String by settings
    val gson: String by settings
    val encache: String by settings
    val thymeleaf: String by settings
    val hibernate: String by settings

    plugins {
        id("fr.brouillard.oss.gradle.jgitver") version jgitver
        id("io.spring.dependency-management") version dependencyManagement
        id("org.springframework.boot") version springframeworkBoot
        id("com.github.johnrengelman.shadow") version johnrengelmanShadow
        id("com.google.cloud.tools.jib") version jib
        id("com.google.protobuf") version protobufVer
        id("name.remal.sonarlint") version sonarlint
        id("com.diffplug.spotless") version spotless
        id("org.projectlombok.lombok") version lombok
        id("org.assertj-core") version assertj
        id("com.zaxxer.HikariCP") version hikariCP
        id("org.flywaydb.flyway-core") version flyway
        id("org.postgresql.postgresql") version postgresql
        id("com.google.code.gson") version gson
        id("org.ehcache") version encache
        id("org.thymeleaf") version thymeleaf
        id("org.hibernate.orm.hibernate-core") version hibernate
    }
}
