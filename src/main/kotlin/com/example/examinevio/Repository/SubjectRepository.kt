package com.example.examinevio.Repository

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.Subject
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface SubjectRepository: MongoRepository<Subject,ObjectId>{
    @Query(value = "{'groups':?0}")
    fun findSubjects(group_name:String): ArrayList<Subject>?
    @Query(value = "{'teachers':?0}")
    fun findSubjectsByTeacher(teacher_id:ObjectId): ArrayList<Subject>?
}