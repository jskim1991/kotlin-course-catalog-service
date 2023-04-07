package io.jay.coursecatalog.controller

import io.jay.coursecatalog.service.GreetingService
import io.mockk.every
import io.mockk.mockkClass
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class GreetingControllerITUsingMockMvc {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun test_greet_returnsGreetingMessage() {
        val responseBody = mockMvc.perform(get("/greet?date=2021-03-04"))
            .andReturn()
            .response.contentAsString

        Assertions.assertEquals("2021-03-04", responseBody)
    }
}