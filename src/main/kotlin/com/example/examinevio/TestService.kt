package com.example.examinevio

import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class TestService (
    @Autowired
    private val testRepository:TestRepository
){
    fun createTest(test: Test){
        testRepository.save(test)
    }
    fun getTest(id: ObjectId): Test {
        if (testRepository.findById(id).isPresent) {
            return testRepository.findById(id).get()
        }
        else{
            return testRepository.findById(id).get()
        }
    }
    fun addQuestion(id: ObjectId,question: Question){
        val test = testRepository.findById(id).get()
        test.questions.add(question)
        testRepository.save(test)
    }
}