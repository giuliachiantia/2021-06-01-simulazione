package it.polito.tdp.genes.model;

public class Event implements Comparable <Event>{
	
	private int t; //tempo
	private int nIng; //numero ingegnere
	
	public Event(int t, int nIng) {
		super();
		this.t = t;
		this.nIng = nIng;
	}

	public int getT() {
		return t;
	}


	public int getnIng() {
		return nIng;
	}

	@Override
	public int compareTo(Event o) {
		// TODO Auto-generated method stub
		return this.t-o.t;
	}

	
	
	
	

}
