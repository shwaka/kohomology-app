plugins {
    val kotlinVersion = "1.4.32"
    kotlin("js") version kotlinVersion
    kotlin("plugin.serialization") version kotlinVersion
    id("org.jlleitschuh.gradle.ktlint") version "10.0.0"
}

group = "me.shun"
version = "1.0-SNAPSHOT"

repositories {
    jcenter()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/kotlin/p/kotlin/kotlin-js-wrappers") }
    maven { url = uri("https://shwaka.github.io/maven/") }
}

dependencies {
    testImplementation(kotlin("test-js"))
    implementation("org.jetbrains:kotlin-react:16.13.1-pre.113-kotlin-1.4.0")
    implementation("org.jetbrains:kotlin-react-dom:16.13.1-pre.113-kotlin-1.4.0")
    implementation("org.jetbrains:kotlin-styled:5.2.1-pre.148-kotlin-1.4.21")
    implementation(npm("styled-components", "~5.2.1"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.1.0")
    implementation("com.github.shwaka.kohomology:kohomology:0.5")
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport.enabled = true
            }
        }
    }
}

tasks.register("kc") {
    // alias
    dependsOn("ktlintCheck")
}

tasks.register("kf") {
    // alias
    dependsOn("ktlintFormat")
}
