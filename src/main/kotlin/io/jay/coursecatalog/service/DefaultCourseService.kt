package io.jay.coursecatalog.service

import io.jay.coursecatalog.dto.CourseDTO
import io.jay.coursecatalog.entity.Course
import io.jay.coursecatalog.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

@Service
class DefaultCourseService(val courseRepository: CourseRepository) : CourseService {

    companion object : KLogging()

    override fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category)
        }

        val savedCourse = courseRepository.save(courseEntity)
        logger.info("Saved course is: $savedCourse")

        return savedCourse.let {
            CourseDTO(it.id, it.name, it.category)
        }
    }

    override fun getAll(): List<CourseDTO> {
        return courseRepository.findAll()
            .map { CourseDTO(it.id, it.name, it.category) }
    }
}