package com.example.examinevio.Controller

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.Subject
import com.example.examinevio.Models.User
import com.example.examinevio.Service.SubjectService
import com.example.examinevio.Service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import springfox.documentation.spi.service.contexts.SecurityContextBuilder
import java.security.Principal

@RestController
class UserController(
    @Autowired
    val userService: UserService,
    @Autowired
    val subjectService: SubjectService
) {
    @PostMapping(value = arrayOf("/add_user"))
    fun add_user(@RequestBody user: User): User {
        return userService.signUpUser(user)
    }
    @GetMapping(value = arrayOf("/subjects"))
    fun get_subjects(principal: Principal): MutableList<Subject> {
        return subjectService.getSubjects(principal.name)
    }
}