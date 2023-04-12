package io.jay.coursecatalog.controller

import com.ninjasquad.springmockk.MockkBean
import io.jay.coursecatalog.dto.CourseDTO
import io.jay.coursecatalog.service.CourseService
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [CourseController::class])
@AutoConfigureWebTestClient
class CourseControllerTests {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var mockCourseService: CourseService

    @Nested
    inner class AddCourse {
        lateinit var course: CourseDTO
        @BeforeEach
        fun setUp() {
            course = CourseDTO(null, "Build House", "Engineering")
            every { mockCourseService.addCourse(course) }
                .returns(CourseDTO(1, "Build House", "Engineering"))
        }

        @Test
        fun returnsStatusCreated() {
            webTestClient.post()
                .uri("/v1/courses")
                .bodyValue(course)
                .exchange()
                .expectStatus().isCreated
        }

        @Test
        fun callsCourseService() {
            webTestClient.post()
                .uri("/v1/courses")
                .bodyValue(course)
                .exchange()

            verify { mockCourseService.addCourse(course) }
        }

        @Test
        fun returnsCourseDTO() {
            val course = CourseDTO(null, "Build House", "Engineering")

            every { mockCourseService.addCourse(course) }
                .returns(CourseDTO(1, "Build House", "Engineering"))

            val result = webTestClient.post()
                .uri("/v1/courses")
                .bodyValue(course)
                .exchange()
                .expectBody(CourseDTO::class.java)
                .returnResult()
                .responseBody!!

            assertEquals(1, result.id)
            assertEquals("Build House", result.name)
            assertEquals("Engineering", result.category)
        }
    }

    @Nested
    inner class RetrieveAll {
        @Test
        fun returnsStatusOK() {
            every { mockCourseService.getAll() }
                .returns(emptyList())

            webTestClient.get()
                .uri("/v1/courses")
                .exchange()
                .expectStatus().isOk
        }

        @Test
        fun callsCourseService() {
            every { mockCourseService.getAll() }
                .returns(emptyList())

            webTestClient.get()
                .uri("/v1/courses")
                .exchange()

            verify { mockCourseService.getAll() }
        }

        @Test
        fun returnsCourseDTOList() {
            every { mockCourseService.getAll() }
                .returns(listOf(CourseDTO(1,"Java", "CS")))

            val result = webTestClient.get()
                .uri("/v1/courses")
                .exchange()
                .expectBodyList(CourseDTO::class.java)
                .returnResult()
                .responseBody!!

            assertEquals(1, result.size)
            assertEquals(1, result[0].id)
            assertEquals("Java", result[0].name)
            assertEquals("CS", result[0].category)
        }
    }

    @Nested
    inner class UpdateCourse {
        lateinit var courseForUpdate: CourseDTO

        @BeforeEach
        fun setUp() {
            courseForUpdate = CourseDTO(null, "Java", "CS")
            every { mockCourseService.update(1, courseForUpdate) }
                .returns(CourseDTO(1, "Java", "CS"))
        }

        @Test
        fun returnsStatusOK() {
            webTestClient.put()
                .uri("/v1/courses/{courseId}", 1)
                .bodyValue(courseForUpdate)
                .exchange()
                .expectStatus().isOk
        }

        @Test
        fun callsCourseService() {
            webTestClient.put()
                .uri("/v1/courses/{courseId}", 1)
                .bodyValue(courseForUpdate)
                .exchange()

            verify { mockCourseService.update(1, courseForUpdate) }
        }

        @Test
        fun returnsCourseDTO() {
            val result = webTestClient.put()
                .uri("/v1/courses/{courseId}", 1)
                .bodyValue(courseForUpdate)
                .exchange()
                .expectBody(CourseDTO::class.java)
                .returnResult()
                .responseBody!!

            assertEquals(1, result.id)
            assertEquals("Java", result.name)
            assertEquals("CS", result.category)
        }
    }

    @Nested
    inner class Delete() {

        @BeforeEach
        fun setUp() {
            every { mockCourseService.delete(1) } just runs
        }

        @Test
        fun returnsStatusNoContent() {
            webTestClient.delete()
                .uri("/v1/courses/{courseId}", 1)
                .exchange()
                .expectStatus().isNoContent
        }

        @Test
        fun callsCourseService() {
            webTestClient.delete()
                .uri("/v1/courses/{courseId}", 1)
                .exchange()

            verify { mockCourseService.delete(1) }
        }
    }
}