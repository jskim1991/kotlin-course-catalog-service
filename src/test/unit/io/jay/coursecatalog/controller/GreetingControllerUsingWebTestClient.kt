package io.jay.coursecatalog.controller

import io.jay.coursecatalog.service.GreetingService
import io.mockk.every
import io.mockk.mockkClass
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.LocalDate

class GreetingControllerUsingWebTestClient {

    lateinit var webTestClient: WebTestClient
    lateinit var mockGreetingService: GreetingService

    @BeforeEach
    fun setup() {
        mockGreetingService = mockkClass(GreetingService::class)
        webTestClient = WebTestClient
            .bindToController(GreetingController(mockGreetingService))
            .build()
    }

    @Test
    fun test_greet() {
        every {
            mockGreetingService.greet(LocalDate.parse("2021-03-04"))
        } returns "Hello 2021-03-04"

        val result = webTestClient.get()
            .uri("/greet?date=2021-03-04")
            .exchange()
            .expectStatus().is2xxSuccessful
            .expectBody(String::class.java)
            .returnResult()

        Assertions.assertEquals("Hello 2021-03-04", result.responseBody)
    }
}