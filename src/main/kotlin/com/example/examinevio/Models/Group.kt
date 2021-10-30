package com.example.examinevio.Models

import com.example.examinevio.Models.User
import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document("groups")
data class Group (
    @Id
    val id: ObjectId = ObjectId.get(),
    val name:String,
    var users:ArrayList<Student> = ArrayList()
)