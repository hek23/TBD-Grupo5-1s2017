package tk.ww3app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class LinearGraphInfo {
	
	private int cantidadFechas;
	private List<LinearGraphFrame> info;
	
	public LinearGraphInfo(){}
	
	public LinearGraphInfo(int cantidadFechas, List<LinearGraphFrame> info){
		this.cantidadFechas = cantidadFechas;
		this.info = info;
	}

	public int getCantidadFechas() {
		return cantidadFechas;
	}

	public void setCantidadFechas(int cantidadFechas) {
		this.cantidadFechas = cantidadFechas;
	}

	public List<LinearGraphFrame> getInfo() {
		return info;
	}

	public void setInfo(List<LinearGraphFrame> info) {
		this.info = info;
	}
	
}
