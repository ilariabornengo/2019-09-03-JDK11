package it.polito.tdp.food.model;

public class Adiacenza {

	String tipo1;
	String tipo2;
	Integer peso;
	public Adiacenza(String tipo1, String tipo2, Integer peso) {
		super();
		this.tipo1 = tipo1;
		this.tipo2 = tipo2;
		this.peso = peso;
	}
	public String getTipo1() {
		return tipo1;
	}
	public void setTipo1(String tipo1) {
		this.tipo1 = tipo1;
	}
	public String getTipo2() {
		return tipo2;
	}
	public void setTipo2(String tipo2) {
		this.tipo2 = tipo2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	
}
