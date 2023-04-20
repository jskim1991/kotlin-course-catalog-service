package io.jay.coursecatalog.repository

import io.jay.coursecatalog.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor, Int> {
}