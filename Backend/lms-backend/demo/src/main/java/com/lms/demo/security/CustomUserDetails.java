package com.lms.demo.security;

import com.lms.demo.entity.Employee;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final Employee employee;
    public CustomUserDetails(Employee employee) { this.employee = employee; }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Role enum must use ROLE_ prefix so hasRole("USER") → matches ROLE_USER
        return List.of(new SimpleGrantedAuthority(employee.getRole().name()));
    }
    @Override public String getPassword() { return
            employee.getPassword(); }


    @Override
    public String getUsername() {
        return employee.getEmail();
    }



    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
