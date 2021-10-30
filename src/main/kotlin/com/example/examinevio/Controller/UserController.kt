package com.example.examinevio.Controller

import com.example.examinevio.Models.User
import com.example.examinevio.Service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    val userService: UserService
) {
    @PostMapping(value = arrayOf("/add_user"))
    fun add_test(@RequestBody user: User): User {
        return userService.signUpUser(user)
    }

}