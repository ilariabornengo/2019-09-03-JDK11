package it.polito.tdp.food.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.food.db.FoodDao;

public class Model {
	
	FoodDao dao;
	List<String> vertici;
	Graph<String,DefaultWeightedEdge> grafo;
	List<String> camminoBest;
	int pesoMax;
	
	public Model()
	{
		this.dao=new FoodDao();
	}
	
	public void creaGrafo(Integer calorie)
	{
		this.vertici=new ArrayList<String>();
		this.grafo=new SimpleWeightedGraph<String,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		this.dao.getVertci(vertici, calorie);
		Graphs.addAllVertices(this.grafo, this.vertici);
		
		//aggiungo gli archi
		for(Adiacenza a:this.dao.getAdiacenze(vertici, calorie))
		{
			if(this.grafo.vertexSet().contains(a.getS1()) && this.grafo.vertexSet().contains(a.getS2()))
			{
				Graphs.addEdge(this.grafo, a.getS1(), a.getS2(), a.getPeso());
			}
		}
	}
	public List<Adiacenza> correlate(String s)
	{
		List<Adiacenza> list=new ArrayList<Adiacenza>();
		for(String st:Graphs.neighborListOf(this.grafo, s))
		{
			int peso=(int) this.grafo.getEdgeWeight(this.grafo.getEdge(s, st));
			Adiacenza a=new Adiacenza(s,st,peso);
			list.add(a);
		}
		return list;
	}
	
	public List<String> best(String partenza,int N)
	{
		this.camminoBest=new ArrayList<String>();
		List<String> parziale=new ArrayList<String>();
		this.pesoMax=0;
		parziale.add(partenza);
		ricorsione(parziale,N);
		return this.camminoBest;
	}
	private void ricorsione(List<String> parziale, int N) {
		String ultimo=parziale.get(parziale.size()-1);
		if(parziale.size()==N)
		{
			int pesoP=calcolaPeso(parziale);
			if(pesoP>this.pesoMax)
			{
				this.pesoMax=pesoP;
				this.camminoBest=new ArrayList<String>(parziale);
				return;
			}
		}
		
		//fuori dal caso terminale
		for(String s:Graphs.neighborListOf(this.grafo, ultimo))
		{
			if(!parziale.contains(s))
			{
				parziale.add(s);
				ricorsione(parziale,N);
				parziale.remove(s);
			}
		}
		
		
	}

	private int calcolaPeso(List<String> parziale) {
		int pesoTot=0;
		for(int i=1;i<parziale.size();i++)
		{
			String s1=parziale.get(i-1);
			String s2=parziale.get(i);
			int peso=(int) this.grafo.getEdgeWeight(this.grafo.getEdge(s1, s2));
			pesoTot+=peso;
		}
		return pesoTot;
	}

	public List<String> vertici()
	{
		List<String> vert=new ArrayList<String>(this.grafo.vertexSet());
		return vert;
	}
	public int getVertici()
	{
		return this.grafo.vertexSet().size();
	}
	public int getArco()
	{
		return this.grafo.edgeSet().size();
	}
}
