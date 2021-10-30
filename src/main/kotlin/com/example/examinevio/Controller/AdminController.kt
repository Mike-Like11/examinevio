package com.example.examinevio.Controller

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.Student
import com.example.examinevio.Models.User
import com.example.examinevio.Service.GroupService
import com.example.examinevio.Service.UserService
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class AdminController(
    @Autowired
    val groupService: GroupService,
    @Autowired
    val userService: UserService
) {
    @GetMapping(value = arrayOf("/admin/groups"))
    fun get_groups(): MutableList<Group> {
        return groupService.getAllGroups()
    }
    @GetMapping(value = arrayOf("/admin/students"))
    fun get_students(): MutableList<User> {
        println(userService.getUnEnabled())
        return userService.getUnEnabled();
    }
    @PostMapping(value = arrayOf("/admin/group"))
    fun add_group(@RequestBody group: Group): Group {
        return groupService.createGroup(group)
    }
    @PostMapping(value = arrayOf("/admin/{name}/student"))
    fun add_student(@PathVariable name: String,@RequestBody student: Student): Group {
        userService.enableUser(student.email)
        return groupService.addStudent(student,name)
    }
}