package io.jay.coursecatalog.controller

import io.jay.coursecatalog.dto.CourseDTO
import io.jay.coursecatalog.service.CourseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/courses")
class CourseController(val courseService: CourseService) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addCourse(@RequestBody courseDTO: CourseDTO): CourseDTO {
        return courseService.addCourse(courseDTO)
    }

    @GetMapping
    fun retrieveAll(): List<CourseDTO> {
        return courseService.getAll()
    }

    @PutMapping("/{courseId}")
    fun updateCourse(@PathVariable courseId: Int, @RequestBody courseDTO: CourseDTO): CourseDTO {
        return courseService.update(courseId, courseDTO)
    }

    @DeleteMapping("/{courseId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable courseId: Int) {
        courseService.delete(courseId)
    }
}