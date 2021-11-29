package com.example.examinevio.Repository

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.SubjectResult
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface SubjectResultRepository: MongoRepository<SubjectResult, ObjectId> {

}