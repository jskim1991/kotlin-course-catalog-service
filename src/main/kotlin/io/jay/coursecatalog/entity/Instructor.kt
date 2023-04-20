package io.jay.coursecatalog.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "INSTRUCTORS")
class Instructor(
    id: Int?,
    name: String,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id = id
    val name = name

    @OneToMany(mappedBy = "instructor", cascade = [CascadeType.ALL], orphanRemoval = true)
    val courses: List<Course> = mutableListOf()
}