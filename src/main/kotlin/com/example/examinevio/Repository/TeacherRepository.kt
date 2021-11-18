package com.example.examinevio.Repository

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.Subject
import com.example.examinevio.Models.Teacher
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface TeacherRepository:MongoRepository<Teacher,ObjectId> {
    fun findByFio(name:String): Teacher?
    @Query(value = "{'subjects.id':?0}")
    fun findTeacherBySubjects(subject_id:ObjectId): ArrayList<Teacher>
}