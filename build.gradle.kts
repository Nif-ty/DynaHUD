plugins {
    id("java")
}

group = "com.niftymods"
version = "1.3.1"

repositories {
    mavenCentral()
    maven("https://maven.hytale.com/release")
}

dependencies {
    compileOnly("com.hypixel.hytale:Server:2026.02.19-1a311a592")
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    destinationDirectory.set(file("server/mods"))
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    from("src/main/resources")
}