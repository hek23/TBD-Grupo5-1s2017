package tk.ww3app.model;

import java.util.List;

public class InfoGenerationGraph {
	
	private String pais;
	private List<GraphPoint> puntos;
	
	public InfoGenerationGraph(){}
	public InfoGenerationGraph(String pais, List<GraphPoint> puntos){
		this.pais =  pais;
		this.puntos = puntos;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public List<GraphPoint> getPuntos() {
		return puntos;
	}
	public void setPuntos(List<GraphPoint> puntos) {
		this.puntos = puntos;
	}
	
}
