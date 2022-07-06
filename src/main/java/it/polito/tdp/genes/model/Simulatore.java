package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;



public class Simulatore {
	
	//input
	private Genes startGene;
	private int nIngegneri;
	private int TMAX=36; //mesi simulazione
	private double probMantenereGene=0.3;
	private Graph<Genes, DefaultWeightedEdge> grafo;
	
	//output
	 //fotografia al mese 36
	 //li deduco da gene studiato
	
	//stato del mondo
	 //dato un ingegnere dimmi su quale gene lavora
	 List <Genes> geneStudiato; //geneStudiato.get(n.Ingegnere)
	 //dato un gene dimmi quanti ingegneri ci lavorano
	 //Map<Genes, Integer> numIngegneri;
	
	//code
	private PriorityQueue<Event> queue;
	
	public Simulatore(Genes start, int n, Graph<Genes, DefaultWeightedEdge> grafo) { //come se fosse init
		this.startGene=start;
		this.nIngegneri=n;
		this.grafo=grafo;
		
		if(this.grafo.degreeOf(this.startGene)==0) {
			throw new IllegalArgumentException("Vertice partenza isolato");
		}
		//inizializzo coda
		this.queue= new PriorityQueue<>();
		for(int nIng=0; nIng<this.nIngegneri; nIng++) {
			this.queue.add(new Event(0, nIng));
		}
		//inizializzo modello mondo, creando array con nIngegneri valori pari a start gene
		this.geneStudiato= new ArrayList<>();
		for(int nIng=0; nIng<this.nIngegneri; nIng++) {
			this.geneStudiato.add(this.startGene);
			
		}
	}
	
	public void run() {
		while(!this.queue.isEmpty()) {
			Event ev=queue.poll();
			
			//solo un evento posso fare tutto qua
			int T=ev.getT();
			int nIng=ev.getnIng();
			Genes g=this.geneStudiato.get(nIng);
			if(T<this.TMAX) {
				//cosa studia nIng al mese t+1
				if(Math.random()<this.probMantenereGene) {
					//mantieni
					this.queue.add(new Event(T+1, nIng));
	
				} else {
					//cambia
					
					//calcola la somma dei pesi degli adiacenti, S
					double S=0;
					for(DefaultWeightedEdge edge: grafo.edgesOf(g)) {
						S+=this.grafo.getEdgeWeight(edge);
					}
					//estrai un numero casuale R tra 0 e S
					double R=Math.random()*S;
					
					//confronta R con somme parziali dei pesi
					Genes nuovo=null;
					double somma=0.0;
					for(DefaultWeightedEdge edge: grafo.edgesOf(g)) {
						somma+=this.grafo.getEdgeWeight(edge);
						if(somma>R) {
							nuovo=Graphs.getOppositeVertex(this.grafo, edge, g);
							break;
						}
					}
					this.geneStudiato.set(nIng, nuovo);
					
				}
			}
		}
		
	}
	public Map<Genes, Integer> getGeniStudiati() {
		Map<Genes, Integer> studiati = new HashMap<>() ;
		
		for(int nIng=0; nIng<this.nIngegneri; nIng++) {
			Genes g = this.geneStudiato.get(nIng) ;
			if(studiati.containsKey(g)) {
				studiati.put(g, studiati.get(g)+1) ;
			} else {
				studiati.put(g, 1) ;
			}
		}
		
		return studiati ;
	}

}
