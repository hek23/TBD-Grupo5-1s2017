package tk.ww3app.model;

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GraphPoint {
	private String identificador;
	private Double tweets;
	
	public GraphPoint(){}
	public GraphPoint (String id, Double tweets){
		this.identificador = id;
		this.tweets = tweets;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	public Double getTweets() {
		return tweets;
	}
	public void setTweets(Double tweets) {
		this.tweets = tweets;
	}
	public void insertarFecha(Date fecha){
		System.out.println(fecha);
		String id = fecha.toString();
		String[] fechaString = id.split(" ");
		id = fechaString[2] + "-" + fechaString[1]+ "-" + fechaString[5]; 
		this.identificador = id;
	}

}
