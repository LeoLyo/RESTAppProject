package dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import beans.Operator;

public class OperatorDAO {
	private Map<String, Operator> operators = new HashMap<>();

	public Map<String, Operator> getOperators() {
		return operators;
	}

	public void setOperators(Map<String, Operator> operators) {
		this.operators = operators;
	}

	public OperatorDAO() {

	}

	public OperatorDAO(String contextPath) {
	}

	public Operator find(String username, String password) {
		if (!operators.containsKey(username)) {
			return null;
		}
		Operator user = operators.get(username);
		if (!user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}

	public Collection<Operator> findAll() {
		return operators.values();
	}

	public boolean add(Operator u) {
		if (operators.containsKey(u.getUsername())) {
			return false;
		} else {

			operators.put(u.getUsername(), u);
			return true;

		}
	}

	public Operator find(String name) {
		if (operators.containsKey(name)) {
			return operators.get(name);
		} else {
			return null;
		}
	}

	public boolean remove(String name) {
		if (operators.containsKey(name)) {
			operators.remove(name);
			return true;
		} else {
			return false;
		}
	}
}
