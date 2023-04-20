package io.jay.coursecatalog.repository

import io.jay.coursecatalog.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {

    fun findByNameContaining(courseName: String): List<Course>

    /* Native Query example */
    @Query(value = "select * from courses where name like %?1%", nativeQuery = true)
    fun findCoursesByName(courseName: String): List<Course>
}