package com.example.examinevio

import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface TestRepository: MongoRepository<Test,ObjectId> {
}