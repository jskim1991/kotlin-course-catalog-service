package io.jay.coursecatalog.service

import io.jay.coursecatalog.dto.CourseDTO

interface CourseService {
    fun addCourse(courseDTO: CourseDTO): CourseDTO
}
