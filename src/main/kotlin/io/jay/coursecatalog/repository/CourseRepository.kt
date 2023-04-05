package io.jay.coursecatalog.repository

import io.jay.coursecatalog.entity.Course
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {
}