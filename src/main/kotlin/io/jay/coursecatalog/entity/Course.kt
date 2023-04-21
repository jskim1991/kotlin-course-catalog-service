package io.jay.coursecatalog.entity

import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "Courses")
class Course(
    id: Int?,
    name: String,
    category: String,
    instructor: Instructor
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id = id
    var name = name
    var category = category

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "INSTRUCTOR_ID", nullable = false)
    val instructor: Instructor = instructor

    override fun toString(): String {
        return "Course(id=$id, name='$name', category='$category', instructor=${instructor.id})"
    }
}