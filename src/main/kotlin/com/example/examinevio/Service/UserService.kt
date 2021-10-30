package com.example.examinevio.Service

import com.example.examinevio.Models.Group
import com.example.examinevio.Models.Role
import com.example.examinevio.Models.Student
import com.example.examinevio.Models.User
import com.example.examinevio.Repository.GroupRepository
import com.example.examinevio.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service

@Transactional
class UserService  constructor(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    private val groupRepository: GroupRepository,
    private var bCryptPasswordEncoder:BCryptPasswordEncoder = BCryptPasswordEncoder()
) :UserDetailsService {
    override fun loadUserByUsername(email: String?): UserDetails {
        var optionalUser: Optional<User> = userRepository.findByEmail(email!!);
        if (optionalUser.isPresent){
            return optionalUser.get()
        }
        else {
//            for (group: Group in groupRepository.findAll()) {
//                val optionalStudent = Optional.of(group.users.filter {
//                    it.user.getEmail().equals(email)
//                }.get(0))
//              if(optionalStudent.isPresent){
//                  return optionalStudent.get().user
//              }
//            }
        }
        return optionalUser.orElseThrow {
            UsernameNotFoundException("dasdsadasdsadasd")
        };
    }
    fun getAllStudents(): MutableList<User> {
        return userRepository.findAll();
    }
    fun getUnEnabled(): MutableList<User> {
        return userRepository.findAllByEnabledFalse().get()
    }
    fun enableUser(email: String?){
        val user= userRepository.findByEmail(email!!).get()
        user.setEnabled()
        userRepository.save(user)
    }
    fun signUpUser(user: User): User {
        val encryptedPassword = bCryptPasswordEncoder.encode(user.password)
        user.setPassword(encryptedPassword)
        user.UserRole = Role.STUDENT
        return userRepository.save(user)
    }

}