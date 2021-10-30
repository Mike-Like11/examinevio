package com.example.examinevio.Repository

import com.example.examinevio.Models.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import java.util.*

interface UserRepository : MongoRepository<User,ObjectId> {
    fun findByEmail(email:String):Optional<User>
    fun findAllByEnabledFalse():Optional<MutableList<User>>
}