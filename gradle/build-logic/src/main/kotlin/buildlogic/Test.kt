package buildlogic

import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.kotlin

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun DependencyHandlerScope.addTestDependencies(project: Project) {
    testImplementation(kotlin("test-junit"))
    testImplementation(kotlin("test"))
    testImplementation(project, alias = "junit4")
    testImplementation(project, alias = "mockk")
    testImplementation(project, alias = "kotlinx-coroutines-test")
}

internal fun DependencyHandlerScope.testImplementation(dependencyNotation: Any) =
    add("testImplementation", dependencyNotation)

internal fun testImplementation(project: Project, alias: String) {
    project
        .dependencies
        .addProvider(
            "testImplementation",
            project.provider {
                project.libs.findLibrary(alias).get().get()
            }
        )
}
