package io.jay.coursecatalog.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO(
    val id: Int?,
    @field:NotBlank(message = "CourseDTO.name must have a value")
    val name: String,
    @field:NotBlank(message = "CourseDTO.category must have a value")
    val category: String,
    @field:NotNull(message = "CourseDTO.instructorId must have a value")
    val instructorId: Int
)