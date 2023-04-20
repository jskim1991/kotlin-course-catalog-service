package io.jay.coursecatalog

import io.jay.coursecatalog.dto.CourseDTO
import io.jay.coursecatalog.entity.Course
import io.jay.coursecatalog.repository.CourseRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
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

    @BeforeEach
    fun setUp() {
        courseRepository.deleteAll()
    }

    @Test
    fun test_addCourse() {
        val courseToAdd = CourseDTO(null, "About Java", "Engineering")

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
    fun test_retrieveAll() {
        courseRepository.save(Course(null, "About React", "Engineering"))

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
    fun test_retrieveAllCoursesByName() {
        courseRepository.save(Course(null, "About React", "Engineering"))
        courseRepository.save(Course(null, "React.js", "Engineering"))

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
    fun test_update() {
        val savedCourse = courseRepository.save(Course(null, "About React", "Engineering"))
        val courseForUpdate = CourseDTO(null, "About Money", "Economics")

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
    }

    @Test
    fun test_delete() {
        val savedCourse = courseRepository.save(Course(null, "About React", "Engineering"))

        webTestClient.delete()
            .uri("/v1/courses/{courseId}", savedCourse.id)
            .exchange()
            .expectStatus().isNoContent

        val deletedCourse = courseRepository.findById(savedCourse.id!!)
        assertEquals(false, deletedCourse.isPresent)
    }
}