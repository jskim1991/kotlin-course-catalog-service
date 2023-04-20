package io.jay.coursecatalog.service

import io.jay.coursecatalog.dto.CourseDTO
import io.jay.coursecatalog.entity.Course
import io.jay.coursecatalog.exception.CourseNotFoundException
import io.jay.coursecatalog.exception.InstructorNotFoundException
import io.jay.coursecatalog.repository.CourseRepository
import mu.KLogging
import org.springframework.stereotype.Service

interface CourseService {
    fun addCourse(courseDTO: CourseDTO): CourseDTO
    fun getAll(courseName: String?): List<CourseDTO>
    fun update(courseId: Int, courseDTO: CourseDTO): CourseDTO
    fun delete(courseId: Int)
}

@Service
class DefaultCourseService(val courseRepository: CourseRepository, val instructorService: InstructorService) :
    CourseService {

    companion object : KLogging()

    override fun addCourse(courseDTO: CourseDTO): CourseDTO {
        val foundInstructor = instructorService.findByInstructorId(courseDTO.instructorId!!)
        if (!foundInstructor.isPresent) {
            throw InstructorNotFoundException("No instructor found for id ${courseDTO.instructorId}")
        }

        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category, foundInstructor.get())
        }

        val savedCourse = courseRepository.save(courseEntity)
        logger.info("Saved course is: $savedCourse")

        return savedCourse.let {
            CourseDTO(it.id, it.name, it.category, it.instructor.id!!)
        }
    }

    override fun getAll(courseName: String?): List<CourseDTO> {
        val courses = courseName?.let {
            courseRepository.findByNameContaining(courseName)
        } ?: courseRepository.findAll()


        return courses.map { CourseDTO(it.id, it.name, it.category, it.instructor.id!!) }
    }

    override fun update(courseId: Int, courseDTO: CourseDTO): CourseDTO {
        val fetchedCourse = courseRepository.findById(courseId)
        return if (fetchedCourse.isPresent) {
            fetchedCourse.get()
                .let {
                    it.name = courseDTO.name
                    it.category = courseDTO.category
                    val savedCourse = courseRepository.save(it)
                    CourseDTO(savedCourse.id, savedCourse.name, savedCourse.category, savedCourse.instructor.id!!)
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