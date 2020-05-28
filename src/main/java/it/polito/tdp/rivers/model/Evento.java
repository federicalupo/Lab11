package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Evento implements Comparable<Evento>{

	public enum EventType{
		INGRESSO, USCITA
	}
	
	private LocalDate date;
	private EventType type;
	private Flow flow;
	private double flusso;
	
	public Evento(LocalDate date, EventType type, Flow flow) {
		super();
		this.date = date;
		this.type = type;
		this.flow = flow;
	}
	
	public Evento(LocalDate date, EventType type, double flow) {
		super();
		this.date = date;
		this.type = type;
		this.flusso = flow;
	}
	

	public LocalDate getDate() {
		return date;
	}

	public EventType getType() {
		return type;
	}

	public Flow getFlow() {
		return flow;
	}

	public double getFlusso() {
		return flusso;
	}
	@Override
	public int compareTo(Evento o) {
		return this.date.compareTo(o.getDate());
	}
	
	
	
}
