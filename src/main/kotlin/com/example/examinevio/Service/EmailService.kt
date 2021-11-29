package com.example.examinevio.Service

import lombok.AllArgsConstructor
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.io.IOException
import javax.mail.MessagingException
import javax.mail.internet.AddressException


@Service
@Transactional
@AllArgsConstructor
class EmailService(
    @Autowired
    private val javaMailSender: JavaMailSender
) {

    @Throws(AddressException::class, MessagingException::class, IOException::class)
    fun sendmail(email: SimpleMailMessage?) {
        if (email != null) {
            javaMailSender.send(email)
        }
    }
}