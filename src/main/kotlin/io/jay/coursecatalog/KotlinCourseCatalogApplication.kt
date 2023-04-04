package io.jay.coursecatalog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinCourseCatalogApplication

fun main(args: Array<String>) {
    runApplication<KotlinCourseCatalogApplication>(*args)
}
