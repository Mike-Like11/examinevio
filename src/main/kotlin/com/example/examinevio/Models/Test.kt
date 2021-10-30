package com.example.examinevio.Models

import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document

@Document("tests")
data class Test(
    @Id
    val id: ObjectId = ObjectId.get(),
    val name_chapter:String,
    val name_test:String,
    val date:String,
    val question_number:String,
    val time:Int,
    var questions:ArrayList<Question> = ArrayList<Question>()
) {
}
