package com.courier.overc360.api.model.idmaster;

@SuppressWarnings("serial")
public class UsersHelper extends org.springframework.security.core.userdetails.User {

	public UsersHelper(UserLogin userLogin) {
		super(
			userLogin.getName(),
			userLogin.getPassword(),
			userLogin.getGrantedAutoriyList()
		);
	}
}
