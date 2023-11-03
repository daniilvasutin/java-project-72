plugins {
    id("java")
    application
    checkstyle
    jacoco
}

application {
    mainClass.set("hexlet.code.Main")
}

group = "hexlet.code"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<Checkstyle>().configureEach {
    reports {
        xml.required.set(false)
        html.required.set(true)
        finalizedBy(tasks.jacocoTestReport) // report is always generated after tests run
        dependsOn(tasks.test) // tests are required to run before generating the report
    }


}
