package com.example.examinevio.Service

import com.example.examinevio.Models.*
import com.example.examinevio.Repository.GroupRepository
import com.example.examinevio.Repository.SubjectRepository
import com.example.examinevio.Repository.SubjectResultRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GroupService(
    @Autowired
    val groupRepository: GroupRepository,
    @Autowired
    val subjectResultRepository: SubjectResultRepository,
    @Autowired
    val subjectRepository: SubjectRepository
) {
    fun createGroup(group: Group): Group {
        return groupRepository.save(group)
    }
    fun getAllGroups(): MutableList<Group> {
        return groupRepository.findAll()
    }
    fun addStudent(student: Student, name:String): Group {
        val group: Group = groupRepository.findByName(name)
        group.subjects.forEach {
            val subjectResult = SubjectResult(name = it.name)
            student.subjects_results.add(subjectResult)
            subjectResultRepository.save(subjectResult)
        }
        group.users.add(student)
        return groupRepository.save(group)
    }
    fun updateMark(id: ObjectId, student: StudentInput): Group? {
        val group: Group? = groupRepository.findGroupByUserName(student.fio)
        val subject = subjectRepository.findById(id)
        val subjectResult = group?.users?.find {
            it.fio.equals(student.fio)
        }?.subjects_results?.find {
            it.name.equals(subject.get().name)
        }
        subjectResult?.mark = student.mark
        subjectResultRepository.save(subjectResult!!)
        return group
    }
    fun findUser(email:String): Student {
        return groupRepository.findGroupByUserName(email)?.users?.find {
            it.fio.equals(email)
        }!!
    }
}