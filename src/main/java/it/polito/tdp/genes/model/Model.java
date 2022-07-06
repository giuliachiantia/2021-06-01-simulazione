package it.polito.tdp.genes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	
	private GenesDao dao;
	private Graph <Genes, DefaultWeightedEdge> grafo;
	private List<Genes> geniEssenziali= new LinkedList<>();
	private Map<String, Genes> essentialGenesIdMap;
	
	
	public Model() {
		dao= new GenesDao();
	}
	
	public String creaGrafo() {
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.geniEssenziali=dao.getAllEssentialGenes();
		this.essentialGenesIdMap = new HashMap<>();
		for (Genes g : this.geniEssenziali)
			this.essentialGenesIdMap.put(g.getGeneId(), g);
		
		//aggiungo i vertici
		Graphs.addAllVertices(this.grafo, this.geniEssenziali);
		
		//aggiungo gli archi
		List<Adiacenza> archi = dao.getInteractions(essentialGenesIdMap);
		for (Adiacenza a : archi) {
			if (a.getGene1().getChromosome() == a.getGene2().getChromosome()) {
				Graphs.addEdge(this.grafo, a.getGene1(), a.getGene2(), Math.abs(a.getExpressionCorr() * 2.0));
			} else {
				Graphs.addEdge(this.grafo, a.getGene1(), a.getGene2(), Math.abs(a.getExpressionCorr()));
			}
		}
		
		
		
		return String.format("Grafo creato con %d vertici e %d archi\n",
				this.grafo.vertexSet().size(),
				this.grafo.edgeSet().size()) ;
	}
	
	public List<Genes> getEssentialGenes() {
		return geniEssenziali;
	}
	
	public List<Adiacenza> geniAdiacenti(Genes g){ 
	/*Set<DefaultWeightedEdge>adiac= grafo.outgoingEdgesOf(g);  
	List<Adiacenza> result = new ArrayList<Adiacenza>(); 
	for(DefaultWeightedEdge d: adiac) { 
		result.add(new Adiacenza( g,
	Graphs.getOppositeVertex(grafo, d, g),
	grafo.getEdgeWeight(d)));
	} 
	Collections.sort(result); 
	return result;*/
	List<Genes> vicini = Graphs.neighborListOf(this.grafo, g);
	List<Adiacenza> result = new ArrayList<>();
		for (Genes v : vicini) {
			result.add(new Adiacenza(g, v, this.grafo.getEdgeWeight(this.grafo.getEdge(g, v))));
		}
		Collections.sort(result);
		return result;
	 
	}
	 

	public Map<Genes, Integer> simulaIngegneri(Genes start, int n){
		try {
		Simulatore sim= new Simulatore(start, n, grafo);
		sim.run();
		return sim.getGeniStudiati();
		} catch(IllegalArgumentException ex) {
			return null;
		}
		
		
	}
	
	
	
}
