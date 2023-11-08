plugins {
    id("java")
    application
//    checkstyle
    jacoco
    id("io.freefair.lombok") version "8.3"
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

    implementation("org.slf4j:slf4j-simple:2.0.5")
    implementation("org.projectlombok:lombok:1.18.26")

    implementation("io.javalin:javalin:5.6.1")
    implementation("io.javalin:javalin-bundle:5.6.2")
    implementation("io.javalin:javalin-rendering:5.6.2")
    implementation("gg.jte:jte:3.1.4")

    implementation("org.postgresql:postgresql:42.6.0")
    implementation("com.zaxxer:HikariCP:5.1.0")
    implementation("com.h2database:h2:2.2.220")


    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
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
