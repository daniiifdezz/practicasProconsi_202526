plugins {
    kotlin("jvm") version "2.2.10"
    application
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.postgresql:postgresql:42.6.0")

}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}
application{
    mainClass.set("Mainkt")
}