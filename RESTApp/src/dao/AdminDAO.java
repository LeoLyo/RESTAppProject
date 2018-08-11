package dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.Admin;

public class AdminDAO {
	private Map<String, Admin> admins = new HashMap<>();

	public Map<String, Admin> getAdmins() {
		return admins;
	}

	public void setAdmins(Map<String, Admin> admins) {
		this.admins = admins;
	}

	public AdminDAO() {

	}

	public AdminDAO(String contextPath) {
	}

	public Admin find(String username, String password) {
		if (!admins.containsKey(username)) {
			return null;
		}
		Admin user = admins.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}

	public Collection<Admin> findAll() {
		return admins.values();
	}

	public boolean add(Admin u) {
		if (admins.containsKey(u.getUsername())) {
			return false;
		} else {

			admins.put(u.getUsername(), u);
			return true;

		}
	}

	public Admin find(String name) {
		if (admins.containsKey(name)) {
			return admins.get(name);
		} else {
			return null;
		}
	}

	public boolean remove(String name) {
		if (admins.containsKey(name)) {
			admins.remove(name);
			return true;
		} else {
			return false;
		}
	}
}
