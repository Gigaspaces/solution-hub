/*
 * The GigaSpaces DAO
 */
package com.vaannila.dao;

import java.util.ArrayList;
import java.util.List;
import org.openspaces.core.GigaSpace;
import com.vaannila.domain.User;

public class UserDAOSpaceImpl implements UserDAO {

	private GigaSpace gigaspace;
	
	@Override
	public void saveUser(User user) {
		System.out.println(">>>>>>> Space saveUser");
		gigaspace.write(user);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<User> listUser() {
		System.out.println(">>>>>>>Space listUser");
		List<User> users = new ArrayList<User>();
		User usersArry[] = gigaspace.readMultiple(new User(), Integer.MAX_VALUE);
		for (User user : usersArry) {
			users.add(user);
		}
		return users;
	}

	public GigaSpace getGigaspace() {
		return gigaspace;
	}

	public void setGigaspace(GigaSpace gigaspace) {
		this.gigaspace = gigaspace;
	}
}
