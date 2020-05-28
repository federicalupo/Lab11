package it.polito.tdp.rivers.model;

import java.util.PriorityQueue;

import it.polito.tdp.rivers.model.Evento.EventType;

public class Simulatore {

	//stato del mondo
	private double capienzaTot;
	private double quantitaPresente;
	private double quantitaMedia;

	private int cont;
	
	//input 
	private double k;
	private double f_out_min ;
	private final double PERC = 0.05;
	
	
	//coda
	private PriorityQueue<Evento> queue;
	
	//output
	private int numGg;
	private double cMedia;
	
	
	
	
	public int getNumGg() {
		return numGg;
	}

	public double getcMedia() {
		return cMedia;
	}

	public void setK(double k) {
		this.k = k;
	}

	public void init(Double k, River r) {
		
		this.k = k;
		this.f_out_min = 0.8*r.getFlowAvg();
		this.capienzaTot = this.k * r.getFlowAvg()*86400*30; 
		this.quantitaPresente = capienzaTot/2;
	
		this.numGg=0;
		this.cMedia=0;
		this.quantitaMedia=0;
		this.cont=0;
		
		this.queue = new PriorityQueue<>(); //creo
		
		for(Flow f : r.getFlows()) {
			Evento e = new Evento(f.getDay(), EventType.INGRESSO, f);
			queue.add(e);
		}	
	}
	
	public void run() {
		
		while(!queue.isEmpty()) {
			Evento e = queue.poll();
			
			processEvent(e);
		}
		
		this.cMedia = this.quantitaMedia/this.cont;
		
	}
	
	private void processEvent(Evento e) {
		
		switch(e.getType()) {
			case INGRESSO:
				double somma = this.quantitaPresente+e.getFlow().getFlow(); //prova la somma
				
				//incremento 
				if(somma > this.capienzaTot) { //la parte extra non la aggiungo direttamente(?)
					this.quantitaPresente= this.capienzaTot;
					
					this.cont++;
					this.quantitaMedia+=this.quantitaPresente;
				}else {	
					this.quantitaPresente+=e.getFlow().getFlow();
					
					this.cont++;
					this.quantitaMedia+=this.quantitaPresente;
				}
				
				Evento evento = new Evento(e.getDate(), EventType.USCITA, this.f_out_min);
				this.queue.add(evento);			
				
				break;
		
			case USCITA: 
		
				//riduco
				
				double random = Math.random();
				
				
				if(random < PERC) { //riduzione extra
					if((this.quantitaPresente-(10*e.getFlusso()))>=0 ) {
							this.quantitaPresente-= (10*e.getFlusso());
							
							this.cont++;
							this.quantitaMedia+= this.quantitaPresente;
					}
					else //non puoi ridurre
						this.numGg++;
					
					
				}else { 
					if((this.quantitaPresente-e.getFlusso())>=0 ) {
						this.quantitaPresente-= e.getFlusso();
					
						this.cont++;
						this.quantitaMedia+= this.quantitaPresente;
					}
				   else //non puoi ridurre
					 this.numGg++;
				}
				break;
		
		}

	}
	
	
}
