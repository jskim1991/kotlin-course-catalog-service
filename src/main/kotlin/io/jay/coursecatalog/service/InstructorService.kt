package io.jay.coursecatalog.service

import io.jay.coursecatalog.dto.InstructorDTO
import io.jay.coursecatalog.entity.Instructor
import io.jay.coursecatalog.repository.InstructorRepository
import org.springframework.stereotype.Service
import java.util.*

interface InstructorService {
    fun addInstructor(instructorDTO: InstructorDTO): InstructorDTO
    fun findByInstructorId(instructorId: Int): Optional<Instructor>
}

@Service
class DefaultInstructorService(val instructorRepository: InstructorRepository) : InstructorService {
    override fun addInstructor(instructorDTO: InstructorDTO): InstructorDTO {

        val instructorEntity = instructorDTO.let {
            Instructor(it.id, it.name)
        }
        val saved = instructorRepository.save(instructorEntity)
        return saved.let {
            InstructorDTO(it.id, it.name)
        }
    }

    override fun findByInstructorId(instructorId: Int): Optional<Instructor> {
        return instructorRepository.findById(instructorId)
    }
}