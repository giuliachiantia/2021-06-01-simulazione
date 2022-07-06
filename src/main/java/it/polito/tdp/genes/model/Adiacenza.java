package it.polito.tdp.genes.model;

public class Adiacenza implements Comparable <Adiacenza>{
	
	private Genes gene1;
	private Genes gene2;
	private Double expressionCorr;
	
	public Adiacenza(Genes gene1, Genes gene2, Double expressionCorr) {
		super();
		this.gene1 = gene1;
		this.gene2 = gene2;
		this.expressionCorr = expressionCorr;
	}
	
	public Genes getGene1() {
		return gene1;
	}
	public void setGene1(Genes gene1) {
		this.gene1 = gene1;
	}
	public Genes getGene2() {
		return gene2;
	}
	public void setGene2(Genes gene2) {
		this.gene2 = gene2;
	}
	
	public Double getExpressionCorr() {
		return expressionCorr;
	}
	public void setExpressionCorr(Double expressionCorr) {
		this.expressionCorr = expressionCorr;
	}

	@Override
	public int compareTo(Adiacenza o) {
		
		return -(expressionCorr.compareTo(o.getExpressionCorr()));
	}
	
	


	
	
}