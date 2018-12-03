package com.itbank.vo;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

public class User {
	private int id;
	@Email
	private String email;
	@Size(min=4, max=20)
	private String password;
	private List<Authority> authorities;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public List<Authority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<Authority> authorities) {
		this.authorities = authorities;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", authorities=" + authorities + "]";
	}
	public boolean hasRole(String role) {
        for (Authority auth : authorities)
            if (auth.isRole(role))
                return true;
        return false;
    }
}
