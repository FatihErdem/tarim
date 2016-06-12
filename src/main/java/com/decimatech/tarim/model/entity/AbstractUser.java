package com.decimatech.tarim.model.entity;

import com.decimatech.tarim.model.entity.AbstractBaseModel;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractUser extends AbstractBaseModel implements UserDetails {

    private static final long serialVersionUID = 1L;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    @Column(nullable = false)
    private String password;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
