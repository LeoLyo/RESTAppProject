package dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import beans.Firm;

public class FirmDAO {
	

	private Map<String, Firm> firms = new HashMap<>();
	
	
	public Map<String, Firm> getFirms() {
		return firms;
	}

	public void setFirms(Map<String, Firm> firms) {
		this.firms = firms;
	}

	public FirmDAO() {
		
	}
	
	public FirmDAO(String contextPath) {
	}

	
	public Collection<Firm> findAll() {
		return firms.values();
	}
	
	public boolean add(Firm f) {
		if(firms.containsKey(f.getPib())) {
			return false;
		} else {

				firms.put(f.getPib(),f);
				return true;

		}
	}

	public Firm find(String pib) {
		if(firms.containsKey(pib)) {
			return firms.get(pib);
		} else {
			return null;
		}
	}
	
	public boolean remove(String pib) {
		if(firms.containsKey(pib)) {
			firms.remove(pib);
			return true;
		} else {
			return false;
		}
	}

}
