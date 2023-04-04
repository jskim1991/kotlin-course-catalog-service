package io.jay.coursecatalog.controller

import com.ninjasquad.springmockk.MockkBean
import io.jay.coursecatalog.service.GreetingService
import io.mockk.every
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

@WebMvcTest(controllers = [GreetingController::class])
@AutoConfigureWebTestClient
class GreetingControllerTests {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var mockGreetingService: GreetingService

    @Test
    fun test_greet_returnsStatusOk() {
        every { mockGreetingService.greet(any()) }
            .returns("")

        webTestClient.get()
            .uri("/greet?date=2021-03-04")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun test_greet_invokesGreetingService() {
        every { mockGreetingService.greet(any()) }
            .returns("")

        webTestClient.get()
            .uri("/greet?date=2021-03-04")
            .exchange()

        verify { mockGreetingService.greet(LocalDate.parse("2021-03-04")) }
    }

    @Test
    fun test_greet_returnsGreetingMessage() {
        every {
            mockGreetingService.greet(LocalDate.parse("2021-03-04"))
        } returns "Hello 2021-03-04"

        val result = webTestClient.get()
            .uri("/greet?date=2021-03-04")
            .exchange()
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("Hello 2021-03-04", result.responseBody)
    }
}