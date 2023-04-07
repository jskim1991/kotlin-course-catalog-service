package io.jay.coursecatalog.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GreetingService {

    @Value("\${message:}")
    lateinit var message: String

    fun greet(date: LocalDate): String {
        return "$message $date".trim()
    }
}