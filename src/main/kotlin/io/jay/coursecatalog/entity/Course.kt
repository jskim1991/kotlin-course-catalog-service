package io.jay.coursecatalog.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "Courses")
open class Course(
    id: Int?,
    name: String,
    category: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id = id
    var name = name
    var category = category
}