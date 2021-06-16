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
	List<String> porzioni;
	Graph<String,DefaultWeightedEdge> grafo;
	List<String> listaBest;
	int pesoMax;
	
	public Model()
	{
		this.dao=new FoodDao();
	}
	
	public void creaGrafo(Integer calorie)
	{
		this.porzioni=new ArrayList<String>();
		this.grafo= new SimpleWeightedGraph<String,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		//aggiungo i vertici
		this.dao.getVertici(porzioni, calorie);
		Graphs.addAllVertices(this.grafo, this.porzioni);
		
		//aggiungo gli archi
		for(Adiacenza a:this.dao.getAdiacenze(porzioni, calorie))
		{
			if(this.grafo.vertexSet().contains(a.getTipo1()) && this.grafo.vertexSet().contains(a.getTipo2()))
			{
				Graphs.addEdge(this.grafo, a.getTipo1(), a.getTipo2(), a.getPeso());
			}
		}
	}
	
	public List<String> camminoBest(String partenza,Integer N)
	{
		this.listaBest=new ArrayList<String>();
		List<String> parziale=new ArrayList<String>();
		this.pesoMax=0;
		parziale.add(partenza);
		ricorsione(parziale,N);
		return this.listaBest;
	}
	private void ricorsione(List<String> parziale, Integer N) {
		
		//caso terminale
		if(parziale.size()==N)
		{
			int pesoP=getpeso(parziale);
			if(pesoP>this.pesoMax)
			{
				this.pesoMax=pesoP;
				this.listaBest=new ArrayList<String>(parziale);
				return;
			}
			else
			{
				return;
			}
		}
		
		//fuori dal caso terminale
		String ultimo=parziale.get(parziale.size()-1);
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

	public int getpeso(List<String> parziale) {
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

	public List<Adiacenza> correlate(String porzione)
	{
		List<Adiacenza> vicini=new ArrayList<Adiacenza>();
		for(String s:Graphs.neighborListOf(this.grafo, porzione))
		{
			int peso=(int) this.grafo.getEdgeWeight(this.grafo.getEdge(porzione, s));
			Adiacenza ad=new Adiacenza(porzione,s,peso);
			vicini.add(ad);
		}
		return vicini;
	}
	public List<String> tipiPorzioni(Integer calorie)
	{
		List<String> porz=new ArrayList<String>(porzioni);
		return porz;
	}
	public int getArchi()
	{
		return this.grafo.edgeSet().size();
	}
	public int getVertici()
	{
		return this.grafo.vertexSet().size();
	}
}
