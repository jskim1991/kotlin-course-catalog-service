package io.jay.coursecatalog.controller

import com.ninjasquad.springmockk.MockkBean
import io.jay.coursecatalog.dto.InstructorDTO
import io.jay.coursecatalog.service.InstructorService
import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(controllers = [InstructorController::class])
@AutoConfigureWebTestClient
class InstructorControllerTests {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var mockInstructorService: InstructorService

    @Test
    fun createInstructor_returnsStatusCreated() {
        val instructorToCreate = InstructorDTO(null, "Dr.Jay")
        every { mockInstructorService.addInstructor(instructorToCreate) }
            .returns(InstructorDTO(1, "Dr.Jay"))

        webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(instructorToCreate)
            .exchange()
            .expectStatus().isCreated
    }

    @Test
    fun createInstructor_returnsInstructorDTO() {
        val instructorToCreate = InstructorDTO(null, "Dr.Jay")
        every { mockInstructorService.addInstructor(instructorToCreate) }
            .returns(InstructorDTO(1, "Dr.Jay"))

        val result = webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(instructorToCreate)
            .exchange()
            .expectBody(InstructorDTO::class.java)
            .returnResult()
            .responseBody!!

        assertNotNull(result.id)
        assertEquals("Dr.Jay", result.name)
    }

    @Test
    fun createInstructor_returns400_whenNameIsEmpty() {
        val response = webTestClient.post()
            .uri("/v1/instructors")
            .bodyValue(InstructorDTO(null, ""))
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(String::class.java)
            .returnResult()
            .responseBody

        assertEquals("InstructorDTO.name must have a value", response)
    }
}