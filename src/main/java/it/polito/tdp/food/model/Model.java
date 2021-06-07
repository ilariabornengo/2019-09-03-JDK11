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
	Graph<String,DefaultWeightedEdge>grafo;
	List<String> camminoBest;
	Integer pesoMax;
	public Model()
	{
		this.dao=new FoodDao();
	}
	
	public void creaGrafo(Integer calorie)
	{
		this.grafo=new SimpleWeightedGraph<String,DefaultWeightedEdge>(DefaultWeightedEdge.class);
		//aggiungo i vertici
		List<String> vertici=new ArrayList<String>(this.dao.getVertici(calorie));
		Graphs.addAllVertices(this.grafo, vertici);
		
		//aggiungo gli archi
		for(Adiacenza a:this.dao.getAdiacenze(calorie, vertici))
		{
			if(this.grafo.vertexSet().contains(a.getP1()) && this.grafo.vertexSet().contains(a.getP2()))
			{
				Graphs.addEdge(this.grafo, a.getP1(), a.getP2(), a.getPeso());
			}
		}
	}
	public List<String> getPorzioni()
	{
		List<String> porzioni=new ArrayList<String>(this.grafo.vertexSet());
		Collections.sort(porzioni, new ComparatorNome());
		return porzioni;
	}
	
	public List<String> getBestCammino(String partenza, Integer N )
	{
		this.camminoBest=new ArrayList<String>();
		List<String> parziale=new ArrayList<String>();
		parziale.add(partenza);
		pesoMax=0;
		ricorsione(parziale,1,N);
		return this.camminoBest;
	}
	
	
		
	
	private void ricorsione(List<String> parziale, int i, Integer N) {
		String ultimo=parziale.get(parziale.size()-1);
		//condizione di terminazioneù
		//N+1 perchè partiamo da 1 avendo messo parziale
		if(i==N+1)
		{
			int peso=calcolaPeso(parziale);
			
				if(peso>this.pesoMax)
				{
					this.camminoBest=new ArrayList<String>(parziale);
					this.pesoMax=peso;
					return;
				}
				else
				{
					return;
				}
			
		}
		
		//fuori dal caso terminale
		for(String s:Graphs.neighborListOf(this.grafo, ultimo))
		{
			if(!parziale.contains(s) && i<=N)
			{
				parziale.add(s);
				ricorsione(parziale,i+1,N);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	public Integer getpesoMax()
	{
		return this.pesoMax;
	}
	private int calcolaPeso(List<String> parziale) {
		Integer pesoTot=0;
		for(int i=1;i<parziale.size();i++)
		{
			String v1=parziale.get(i-1);
			String v2=parziale.get(i);
			Integer peso=(int) this.grafo.getEdgeWeight(this.grafo.getEdge(v1, v2));
			pesoTot+=peso;
		}
		
		return pesoTot;
	}

	public List<Adiacenza> getCorrelati(String categoria)
	{
		List<Adiacenza> correlate=new ArrayList<Adiacenza>();
		for(String s:Graphs.neighborListOf(this.grafo, categoria))
		{
			Adiacenza ad=new Adiacenza(categoria,s,(int)this.grafo.getEdgeWeight(this.grafo.getEdge(categoria, s)));
			correlate.add(ad);
		}
		return correlate;
	}
	
	public int getVertici()
	{
		return this.grafo.vertexSet().size();
	}
	
	public int getArchi()
	{
		return this.grafo.edgeSet().size();
	}
}
