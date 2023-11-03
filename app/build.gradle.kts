plugins {
    id("java")
    application
//    checkstyle
    jacoco
    id("com.github.johnrengelman.shadow") version "8.1.1"
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
    implementation("io.javalin:javalin:5.6.1")
    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("org.projectlombok:lombok:1.18.22")
    implementation("org.projectlombok:lombok:1.18.22")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)

        //html.outputLocation = layout.buildDirectory.dir('jacocoHtml')
        //html.stylesheet = resources.text.fromFile("config/xsl/checkstyle-custom.xsl")
        //finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
        //dependsOn(tasks.test) // tests are required to run before generating the report
    }
}