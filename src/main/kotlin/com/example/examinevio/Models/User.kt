package com.example.examinevio.Models

import com.example.examinevio.Models.Role
import nonapi.io.github.classgraph.json.Id
import org.bson.types.ObjectId
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*


@Document("users")
data class User(
    @Id
    private val id: ObjectId = ObjectId.get(),
    private val email:String,
    private val username: String,
    private var password: String,
    var UserRole: Role,
    private var enabled:Boolean = false
):UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? {
        val simpleGrantedAuthority = SimpleGrantedAuthority(UserRole.name)
        return Collections.singletonList(simpleGrantedAuthority)
    }
    fun setPassword(password: String){
        this.password = password
    }
    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return username
    }
    fun getEmail(): String {
        return email;
    }
    fun setEnabled(){
        enabled = true
    }
    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean {
        return enabled
    }
}