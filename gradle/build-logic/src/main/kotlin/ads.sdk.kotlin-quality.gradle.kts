plugins {
    id("quality.detekt")
}

tasks.register("qualityCheck") {
    setDependsOn(
        listOf(
            tasks.getByName("detekt")
        )
    )
}
