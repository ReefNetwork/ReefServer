/*
 * RRRRRR                         jjj
 * RR   RR   eee    eee               pp pp
 * RRRRRR  ee   e ee   e _____    jjj ppp  pp
 * RR  RR  eeeee  eeeee           jjj pppppp
 * RR   RR  eeeee  eeeee          jjj pp
 *                              jjjj  pp
 *
 * Copyright (c) 2020. Ree-jp.  All Rights Reserved.
 */

plugins {
    java
    maven
    kotlin("jvm") version "1.3.71"
    id("org.jetbrains.dokka-android") version "0.9.17"
    id("com.github.johnrengelman.shadow") version "5.2.0"
    id("net.minecrell.plugin-yml.nukkit") version "0.3.0"
}

group = "net.ree-jp"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven(
        url = uri("https://jitpack.io")
    )
    maven(
        url = uri("https://repo.nukkitx.com/main/")
    )
    maven(
        url = uri("http://jenkins.onebone.me/plugin/repository/everything/")
    )
}

dependencies {
    compileOnly("me.onebone:economyapi:1.0.0")
    testCompileOnly("me.onebone:economyapi:1.0.0")
    compileOnly("com.github.ReefNetwork:SimpleBuilder:a076f0ff8c")
    testCompileOnly("com.github.ReefNetwork:SimpleBuilder:a076f0ff8c")
    compileOnly("com.github.Creeperface01:ScoreboardAPI:master")
    testCompileOnly("com.github.Creeperface01:ScoreboardAPI:master")
    compileOnly("com.github.ReefNetwork:Nukkit:790d1c16cd")
    testCompileOnly("com.github.ReefNetwork:Nukkit:790d1c16cd")
    compileOnly("com.github.Ree-jp-minecraft:StackStorage_nukkit:master-SNAPSHOT")
    testCompileOnly("com.github.Ree-jp-minecraft:StackStorage_nukkit:master-SNAPSHOT")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.xerial:sqlite-jdbc:3.30.1")
    implementation("com.github.Tea-Ayataka:Kordis:0.2.3")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.5")
    implementation("com.google.code.gson:gson:2.8.6")
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

nukkit {
    name = "ReefSeichi"
    main = "net.ree_jp.reefseichi.ReefSeichiPlugin"
    api = listOf("1.0.0")
    author = "Ree-jp"
    description = "ReefSeichiServerPlugin"
    website = "https://github.com/ReefNetwork/ReefServer"
    version = "1.0.0"
}