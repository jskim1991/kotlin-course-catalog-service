package io.jay.coursecatalog.dto

import jakarta.validation.constraints.NotBlank

data class CourseDTO(
    val id: Int?,
    @field:NotBlank(message = "CourseDTO.name must have a value")
    val name: String,
    @field:NotBlank(message = "CourseDTO.category must have a value")
    val category: String,
)