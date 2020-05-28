package it.polito.tdp.rivers.model;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


import it.polito.tdp.rivers.db.RiversDAO;

public class Model {

	private RiversDAO dao;
	private Simulatore simulatore;
	// private Map<Integer, River> idMap; //va bene?? non serve sec me 
	
	public Model() {
		dao = new RiversDAO();
		this.simulatore= new Simulatore();
	}
	
	
	public List<River> getAllRivers() {
		/*idMap  = new HashMap<>();
		
		for(River r : dao.getAllRivers()) {
			idMap.put(r.getId(),r);
		}*/
		return dao.getAllRivers();
	}


	public List<Double> popolaInterfacciaM(River river) {
		
		return this.dao.misuraMedia(river);
	}
	
	public List<LocalDate> popolaInterfacciaD(River river) {
		
		List<LocalDate> date = new LinkedList<>();
		
		date.add(this.dao.flussi(river).get(0).getDay()); //data inizio
		date.add(this.dao.flussi(river).get(dao.flussi(river).size()-1).getDay()); //ultima misurazione
		
		return date;
	}
	
	public void simula(double k, River r) {
		this.simulatore.init(k, r);
		this.simulatore.run();
		
	}
	
	public int getNumGg() {
		return this.simulatore.getNumGg();
	}

	public double getcMedia() {
		return this.simulatore.getcMedia();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
