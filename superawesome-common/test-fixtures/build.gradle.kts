plugins {
    `java-library`
    `java-test-fixtures`
    kotlin("jvm")
}

dependencies {
    testFixturesImplementation(libs.junit4)
    testFixturesImplementation(libs.mockk)
    testFixturesImplementation(libs.mockwebserver)
}
