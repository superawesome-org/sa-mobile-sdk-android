plugins {
    `java-library`
    `java-test-fixtures`
    kotlin("jvm")
}

kotlin {
    jvmToolchain(8)
}

dependencies {
    testFixturesImplementation(libs.junit4)
    testFixturesImplementation(libs.mockk)
    testFixturesImplementation(libs.mockwebserver)
    testFixturesImplementation(libs.kotlinx.coroutines.test)
    testFixturesImplementation(libs.kotlinx.serialization.json)
}
