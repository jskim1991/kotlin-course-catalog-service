package io.jay.coursecatalog.repository

import io.jay.coursecatalog.entity.Course
import io.jay.coursecatalog.entity.Instructor
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.Arguments.arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest
@ActiveProfiles("test")
class CourseRepositoryTests {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()
        val savedInstructor = instructorRepository.save(Instructor(null, "Dr.Jay"))
        courseRepository.save(Course(null, "About React", "Engineering", savedInstructor))
        courseRepository.save(Course(null, "React.js", "Engineering", savedInstructor))

    }

    companion object {
        @JvmStatic
        fun courseAndSize(): Stream<Arguments> {
            return Stream.of(arguments("React", 2), arguments("js", 1))
        }
    }

    @ParameterizedTest
    @MethodSource("courseAndSize")
    fun findByNameContaining(name: String, expectedSize: Int) {
        val courses = courseRepository.findByNameContaining(name)
        assertEquals(expectedSize, courses.size)
    }

    @Test
    fun findCoursesByName() {
        val courses = courseRepository.findCoursesByName("React")
        assertEquals(2, courses.size)
    }
}