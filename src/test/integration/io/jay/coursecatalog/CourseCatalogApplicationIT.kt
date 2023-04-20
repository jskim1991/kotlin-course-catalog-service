package io.jay.coursecatalog

import io.jay.coursecatalog.dto.CourseDTO
import io.jay.coursecatalog.dto.InstructorDTO
import io.jay.coursecatalog.entity.Course
import io.jay.coursecatalog.entity.Instructor
import io.jay.coursecatalog.repository.CourseRepository
import io.jay.coursecatalog.repository.InstructorRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient
class CourseCatalogApplicationIT {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
        instructorRepository.deleteAll()
    }

    @Nested
    inner class CourseTests {
        @Test
        fun addCourse() {
            val savedInstructor = instructorRepository.save(Instructor(null, "Dr.Jay"))
            val courseToAdd = CourseDTO(null, "About Java", "Engineering", savedInstructor.id!!)

            val responseBody = webTestClient.post()
                .uri("/v1/courses")
                .bodyValue(courseToAdd)
                .exchange()
                .expectStatus().isCreated
                .expectBody(CourseDTO::class.java)
                .returnResult()
                .responseBody!!

            assertTrue(responseBody.id != null)
        }

        @Test
        fun retrieveAll() {
            val savedInstructor = instructorRepository.save(Instructor(null, "Dr.Jay"))
            courseRepository.save(Course(null, "About React", "Engineering", savedInstructor))

            val responseBody = webTestClient.get()
                .uri("/v1/courses")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(CourseDTO::class.java)
                .returnResult()
                .responseBody!!

            assertEquals(1, responseBody.size)
            assertEquals("About React", responseBody[0].name)
            assertEquals("Engineering", responseBody[0].category)
        }

        @Test
        fun retrieveAllCoursesByName() {
            val savedInstructor = instructorRepository.save(Instructor(null, "Dr.Jay"))
            courseRepository.save(Course(null, "About React", "Engineering", savedInstructor))
            courseRepository.save(Course(null, "React.js", "Engineering", savedInstructor))

            val uri = UriComponentsBuilder.fromUriString("/v1/courses")
                .queryParam("course_name", "js")
                .toUriString()
            val responseBody = webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(CourseDTO::class.java)
                .returnResult()
                .responseBody!!

            assertEquals(1, responseBody.size)
            assertEquals("React.js", responseBody[0].name)
            assertEquals("Engineering", responseBody[0].category)
        }

        @Test
        fun updateCourse() {
            val savedInstructor = instructorRepository.save(Instructor(null, "Dr.Jay"))
            val savedCourse = courseRepository.save(Course(null, "About React", "Engineering", savedInstructor))
            val courseForUpdate = CourseDTO(null, "About Money", "Economics", 1)

            val responseBody = webTestClient.put()
                .uri("/v1/courses/{courseId}", savedCourse.id)
                .bodyValue(courseForUpdate)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(CourseDTO::class.java)
                .returnResult()
                .responseBody!!

            assertEquals(1, responseBody.size)
            assertEquals(savedCourse.id, responseBody[0].id)
            assertEquals("About Money", responseBody[0].name)
            assertEquals("Economics", responseBody[0].category)
            assertEquals(savedInstructor.id, responseBody[0].instructorId)
        }

        @Test
        fun deleteCourse() {
            val savedInstructor = instructorRepository.save(Instructor(null, "Dr.Jay"))
            val savedCourse = courseRepository.save(Course(null, "About React", "Engineering", savedInstructor))

            webTestClient.delete()
                .uri("/v1/courses/{courseId}", savedCourse.id)
                .exchange()
                .expectStatus().isNoContent

            val deletedCourse = courseRepository.findById(savedCourse.id!!)
            assertEquals(false, deletedCourse.isPresent)
        }
    }

    @Nested
    inner class InstructorTests {
        @Test
        fun createInstructor() {
            val result = webTestClient.post()
                .uri("/v1/instructors")
                .bodyValue(InstructorDTO(null, "Dr.K"))
                .exchange()
                .expectStatus().isCreated
                .expectBody(InstructorDTO::class.java)
                .returnResult()
                .responseBody!!

            assertNotNull(result.id)
            assertEquals("Dr.K", result.name)
        }
    }
}