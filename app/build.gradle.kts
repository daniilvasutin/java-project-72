plugins {
    id("java")
    application
//    checkstyle
    jacoco
    id("io.freefair.lombok") version "8.3"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id ("com.adarshr.test-logger") version "4.0.0"
}

application {
    mainClass.set("hexlet.code.App")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {

    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("org.projectlombok:lombok:1.18.26")

    implementation("io.javalin:javalin:5.6.1")
    implementation("io.javalin:javalin-bundle:5.6.2")
    implementation("io.javalin:javalin-rendering:5.6.2")
    implementation("gg.jte:jte:3.1.4")

    implementation ("org.jsoup:jsoup:1.16.2")

    implementation("org.postgresql:postgresql:42.6.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.220")

    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("com.squareup.okhttp3:mockwebserver:4.11.0")
    testImplementation("org.assertj:assertj-core:3.24.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation("com.konghq:unirest-java:3.14.5")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}
tasks.test {
    finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
    testLogging {
        exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
        events = mutableSetOf(org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED, org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED, org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED)
        // showStackTraces = true
        // showCauses = true
        showStandardStreams = true
    }
}
tasks.jacocoTestReport {
    dependsOn(tasks.test) // tests are required to run before generating the report
}



tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(true)

        //html.outputLocation = layout.buildDirectory.dir('jacocoHtml')
        //html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-custom.xsl")
        //finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
        //dependsOn(tasks.test) // tests are required to run before generating the report
    }
}
