package com.example.examinevio.Service

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.Student
import com.example.examinevio.Repository.GroupRepository
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*

@Service
class GroupService(
    @Autowired
    val groupRepository: GroupRepository
) {
    fun createGroup(group: Group): Group {
        return groupRepository.save(group)
    }
    fun getAllGroups(): MutableList<Group> {
        return groupRepository.findAll()
    }
    fun addStudent(student: Student, name:String): Group {
        val group: Group = groupRepository.findByName(name)
        group.users.add(student)
        return groupRepository.save(group)
    }
}