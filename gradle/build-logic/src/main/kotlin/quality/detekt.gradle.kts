package quality

import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension

val libs: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

apply<DetektPlugin>()

configure<DetektExtension> {
    config = files("$rootDir/config/filters/detekt.yml")
    allRules = true
    baseline = file("${rootProject.projectDir}/config/baseline/baseline-${name}.xml")
}

tasks.withType<Detekt>().configureEach {
    exclude("**/resources/**,**/build/**")
    jvmTarget = "17"
}
