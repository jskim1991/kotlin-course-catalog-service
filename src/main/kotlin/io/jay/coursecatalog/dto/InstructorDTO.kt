package io.jay.coursecatalog.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDTO (
    val id: Int?,
    @field:NotBlank(message = "InstructorDTO.name must have a value")
    val name: String
)