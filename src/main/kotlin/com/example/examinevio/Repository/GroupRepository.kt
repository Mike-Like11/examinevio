package com.example.examinevio.Repository

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.Test
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface GroupRepository:MongoRepository<Group,ObjectId>{
    fun findByName(name:String):Group
}