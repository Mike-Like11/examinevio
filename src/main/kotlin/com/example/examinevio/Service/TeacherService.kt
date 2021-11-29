package com.example.examinevio.Service

import com.example.examinevio.Models.Teacher
import com.example.examinevio.Repository.TeacherRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TeacherService(
    @Autowired
    private val teacherRepository: TeacherRepository,
) {
    fun addTeacher(teacher: Teacher): Teacher {
        return teacherRepository.save(teacher)
    }
    fun findTeacher(name:String): Teacher? {
        return teacherRepository.findByFio(name)
    }
    fun getAllTeachers(): MutableList<Teacher> {
        return teacherRepository.findAll()
    }
}