package tk.ww3app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LinearGraphFrame {

	private String concepto;
	private List<GraphPoint> puntos;
	
	public LinearGraphFrame(){}
	
	public LinearGraphFrame(String concepto, List<GraphPoint> puntos){
		this.concepto = concepto;
		this.puntos = puntos;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public List<GraphPoint> getPuntos() {
		return puntos;
	}

	public void setPuntos(List<GraphPoint> puntos) {
		this.puntos = puntos;
	}
	
	
}
