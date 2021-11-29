package com.example.examinevio.Service

import com.example.examinevio.JwtProvider
import com.example.examinevio.Models.AuthInput
import com.example.examinevio.Models.Role
import com.example.examinevio.Models.User
import com.example.examinevio.Repository.GroupRepository
import com.example.examinevio.Repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.servlet.http.Cookie
import kotlin.collections.ArrayList

@Service

@Transactional
class UserService  constructor(
    @Autowired
    private val userRepository: UserRepository,
    @Autowired
    val jwtProvider: JwtProvider,
    @Autowired
    private val emailService: EmailService,
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
            UsernameNotFoundException(email)
        };
    }
    fun getAllStudents(): MutableList<User> {
        return userRepository.findAll();
    }
    fun getUnEnabledStudents(): List<User> {
        return userRepository.findAllByEnabledFalse().get().filter {
            it.UserRole.equals(Role.ROLE_STUDENT)
        }
    }
    fun getUnEnabledTeachers(): List<User> {
        return userRepository.findAllByEnabledFalse().get().filter {
            it.UserRole.equals(Role.ROLE_TEACHER)
        }
    }
    fun enableUser(email: String?){
        val user= userRepository.findByEmail(email!!).get()
        user.setEnabled()
        userRepository.save(user)
        val mailMessage = SimpleMailMessage();
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Подтверждение аккаунта на  Examinevio!");
        mailMessage.setText(
            "Спасибо за ожидание, ваш аккаунт был проверен и активирован, теперь вы можете войти в свой аккаунт."+"\n"+"Администратор портала Examinevio");
        emailService.sendmail(mailMessage);
    }
    fun findByLoginAndPassword(authInput: AuthInput): ArrayList<String?> {
        val userEntity = userRepository.findByEmail(authInput.email)
        if(userEntity.isPresent) {
            if (bCryptPasswordEncoder.matches(authInput.password, userEntity.get().getPassword())) {
                val userDetails:UserDetails = userEntity.get()
                val token: String = jwtProvider.generateToken(authInput.email, userDetails.authorities)
                if(userDetails.isEnabled) {
                    return arrayListOf(token, token)
                }
                else{
                    return arrayListOf(null,"Ваш аккаунт еще не подтвержден, ожидайте")
                }
            }
            else{
                return arrayListOf(null,"Ошибка ввода данных")
            }
        }
        return arrayListOf(null,"Ошибка ввода данных")
    }
    fun signUpUser(user: User): User {
        val encryptedPassword = bCryptPasswordEncoder.encode(user.password)
        user.setPassword(encryptedPassword)
        return userRepository.save(user)
    }

}