package tk.ww3app.model;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CircularGraphInfo {
	private int cantidadPaises;
	//private int cantidadTweets;
	private List<GraphPoint> info;
	
	public CircularGraphInfo(){}
	public CircularGraphInfo(int cantidadPaises, List<GraphPoint> info){
		this.cantidadPaises = cantidadPaises;
		//this.cantidadTweets = cantidadTweets;
		this.info = info;
	}

	public int getCantidadPaises() {
		return cantidadPaises;
	}

	public void setCantidadPaises(int cantidadPaises) {
		this.cantidadPaises = cantidadPaises;
	}

	public List<GraphPoint> getInfo() {
		return info;
	}

	public void setInfo(List<GraphPoint> info) {
		this.info = info;
	}
	

}
