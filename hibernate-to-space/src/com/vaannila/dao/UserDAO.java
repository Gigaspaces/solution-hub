package com.vaannila.dao;

import java.util.List;
import com.vaannila.domain.User;

public interface UserDAO {
	
	public void saveUser(User user) ;
	public List<User> listUser() ;
}
