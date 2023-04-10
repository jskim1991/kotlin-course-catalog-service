package io.jay.coursecatalog.service

import io.jay.coursecatalog.dto.CourseDTO

interface CourseService {
    fun addCourse(courseDTO: CourseDTO): CourseDTO
    fun getAll(): List<CourseDTO>
    fun update(courseId: Int, courseDTO: CourseDTO): CourseDTO
    fun delete(courseId: Int)
}
