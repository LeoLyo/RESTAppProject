package dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.BasicUser;

public class BasicUserDAO {
	private Map<String, BasicUser> basicUsers = new HashMap<>();
	
	
	public Map<String, BasicUser> getBasicUsers() {
		return basicUsers;
	}

	public void setBasicUsers(Map<String, BasicUser> basicUsers) {
		this.basicUsers = basicUsers;
	}

	public BasicUserDAO() {
		
	}
	
	public BasicUserDAO(String contextPath) {
	}

	public BasicUser find(String username, String password) {
		if (!basicUsers.containsKey(username)) {
			return null;
		}
		BasicUser user = basicUsers.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}
	
	public Collection<BasicUser> findAll() {
		return basicUsers.values();
	}
	
	public boolean add(BasicUser u) {
		if(basicUsers.containsKey(u.getUsername())) {
			return false;
		} else {

				basicUsers.put(u.getUsername(),u);
				return true;

		}
	}

	public BasicUser find(String name) {
		if(basicUsers.containsKey(name)) {
			return basicUsers.get(name);
		} else {
			return null;
		}
	}
	
	public boolean remove(String name) {
		if(basicUsers.containsKey(name)) {
			basicUsers.remove(name);
			return true;
		} else {
			return false;
		}
	}
	
}
