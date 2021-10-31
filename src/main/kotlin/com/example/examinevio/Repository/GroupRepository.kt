package com.example.examinevio.Repository

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.Student
import com.example.examinevio.Models.Test
import com.example.examinevio.Models.User
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query

interface GroupRepository:MongoRepository<Group,ObjectId>{
    fun findByName(name:String):Group
    @Query(value = "{'users.email':?0}")
    fun findGroupByUserEmail(email: String): Group
    @Query(value = "{'users.fio':?0}")
    fun findGroupByUserName(name: String): Group
}