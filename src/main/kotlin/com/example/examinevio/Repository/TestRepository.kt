package com.example.examinevio.Repository

import com.example.examinevio.Models.Test
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository: MongoRepository<Test,ObjectId> {
}