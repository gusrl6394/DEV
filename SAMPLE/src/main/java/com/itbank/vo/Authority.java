package com.itbank.vo;

public class Authority {
	private String email;
	private String authority;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}	
	@Override
	public String toString() {
		return "Authority [email=" + email + ", authority=" + authority + "]";
	}
	public boolean isRole(String role) {
        return authority.equals("ROLE_" + role.toUpperCase());
    }
}
