package io.jay.coursecatalog.service

import io.jay.coursecatalog.dto.CourseDTO
import io.jay.coursecatalog.entity.Course
import io.jay.coursecatalog.exception.CourseNotFoundException
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

    override fun update(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val fetchedCourse = courseRepository.findById(courseId)
        return if (fetchedCourse.isPresent) {
            fetchedCourse.get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    val savedCourse = courseRepository.save(it)
                    CourseDTO(savedCourse.id, savedCourse.name, savedCourse.category)
                }
        } else {
            throw CourseNotFoundException("No course found for id $courseId")
        }
    }

    override fun delete(courseId: Int) {
        val fetchedCourse = courseRepository.findById(courseId)
        return if (fetchedCourse.isPresent) {
            fetchedCourse.get()
                .let {
                    courseRepository.deleteById(courseId)
                }
        } else {
            throw CourseNotFoundException("No course found for id $courseId")
        }
    }
}