package io.jay.coursecatalog.controller

import io.jay.coursecatalog.service.GreetingService
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

class GreetingControllerTestsUsingMockMvc {

    @Test
    fun test_greet_returnsStatusOk() {
        val mockGreetingService = mockkClass(GreetingService::class)
        val mockMvc = MockMvcBuilders
            .standaloneSetup(GreetingController(mockGreetingService))
            .build()

        every { mockGreetingService.greet(any()) }
            .returns("")

        mockMvc.perform(get("/greet?date=2021-03-04"))
            .andExpect(status().isOk)
    }

    @Test
    fun test_greet_invokesGreetingService() {
        val mockGreetingService = mockkClass(GreetingService::class)
        val mockMvc = MockMvcBuilders
            .standaloneSetup(GreetingController(mockGreetingService))
            .build()

        every { mockGreetingService.greet(any()) }
            .returns("")

        mockMvc.perform(get("/greet?date=2021-03-04"))

        verify { mockGreetingService.greet(LocalDate.parse("2021-03-04")) }
    }

    @Test
    fun test_greet_returnsGreetingMessage() {
        val mockGreetingService = mockkClass(GreetingService::class)
        val mockMvc = MockMvcBuilders
            .standaloneSetup(GreetingController(mockGreetingService))
            .build()

        every {
            mockGreetingService.greet(LocalDate.parse("2021-03-04"))
        } returns "Hello 2021-03-04"

        val responseBody = mockMvc.perform(get("/greet?date=2021-03-04"))
            .andReturn()
            .response.contentAsString

        Assertions.assertEquals("Hello 2021-03-04", responseBody)
    }
}