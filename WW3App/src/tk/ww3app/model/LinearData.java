package tk.ww3app.model;

import java.util.List;

public class LinearData {
	private String cantidadFechas;
	private List<LinearObject> info;
		
	public LinearData() {
		
	}
	public LinearData(String cantidadFechas, List<LinearObject> info) {
		super();
		this.cantidadFechas = cantidadFechas;
		this.info = info;
	}
	public String getCantidadFechas() {
		return cantidadFechas;
	}
	public void setCantidadFechas(String cantidadFechas) {
		this.cantidadFechas = cantidadFechas;
	}
	public List<LinearObject> getInfo() {
		return info;
	}
	public void setInfo(List<LinearObject> info) {
		this.info = info;
	}
	
}
	
	