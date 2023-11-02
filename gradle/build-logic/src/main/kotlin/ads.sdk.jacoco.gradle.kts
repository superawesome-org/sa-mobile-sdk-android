plugins {
    id("com.android.library")
    jacoco
}

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

jacoco {
    toolVersion = libs.findVersion("jacoco").get().requiredVersion
    reportsDirectory.set(file("$buildDir/reports"))
}

tasks.register("jacocoTestReport", JacocoReport::class) {
    dependsOn("testDebugUnitTest")
    group = "Reporting"

    reports {
        xml.required.set(true)
        csv.required.set(false)
        html.required.set(true)
    }
    
    val excludes = listOf(
        "android/databinding/**/*.class",
        "**/android/databinding/*Binding.class",
        "**/android/databinding/*",
        "**/androidx/databinding/*",
        "**/BR.*",
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        "**/*MapperImpl*.*",
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        "**/BuildConfig.*",
        "**/*Component*.*",
        "**/*BR*.*",
        "**/Manifest*.*",
        "**/*\$Lambda$*.*",
        "**/*Companion*.*",
        "**/*Module*.*",
        "**/*Dagger*.*",
        "**/*Hilt*.*",
        "**/*MembersInjector*.*",
        "**/*_MembersInjector.class",
        "**/*_Factory*.*",
        "**/*_Provide*Factory*.*",
        "**/*serializer.*",
        "**/ui/**/*.*",
        "**/di/*.*"
    )

    val javaTree = fileTree(
        "dir" to "$buildDir/intermediates/javac/debug/classes",
        "excludes" to excludes,
    )
    val kotlinTree = fileTree(
        "dir" to "$buildDir/tmp/kotlin-classes/debug",
        "excludes" to excludes,
    )

    classDirectories.setFrom(kotlinTree + javaTree)
    sourceDirectories.setFrom(files(android.sourceSets.getByName("main").java.srcDirs))
    executionData.setFrom(files("$buildDir/jacoco/testDebugUnitTest.exec"))
}
