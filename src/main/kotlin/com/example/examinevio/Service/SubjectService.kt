package com.example.examinevio.Service

import com.example.examinevio.Models.Subject
import com.example.examinevio.Models.Test
import com.example.examinevio.Repository.GroupRepository
import com.example.examinevio.Repository.SubjectRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class SubjectService(
    @Autowired
    val subjectRepository: SubjectRepository,
    @Autowired
    val groupRepository:GroupRepository
) {
    fun createSubject(subject: Subject): Subject {
        return subjectRepository.save(subject)
    }
    fun getSubjects(email:String): MutableList<Subject> {
        return subjectRepository.findSubjects(groupRepository.findGroupByUserName(email).name)
    }
    fun getSubject(id:ObjectId): Subject {
        return subjectRepository.findById(id).get()
    }
    fun addTest(id:ObjectId, test: Test){
        val subject = subjectRepository.findById(id)
        subject.get().tests.add(test)
        subjectRepository.save(subject.get())
    }
}