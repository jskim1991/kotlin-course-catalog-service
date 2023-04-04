package io.jay.coursecatalog.controller

import io.jay.coursecatalog.service.GreetingService
import mu.KLogging
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class GreetingController(val greetingService: GreetingService) {

    companion object: KLogging()

    @GetMapping("/greet")
    fun greet(@RequestParam date: LocalDate): String {
        logger.info("Date is $date")
        return greetingService.greet(date)
    }
}