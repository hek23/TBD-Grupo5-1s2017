package tk.ww3app.model;

import java.util.List;

public class CircData {
	private String cantidadPaises;
	private String cantidadTweets;
	private CircObject info;
		
	public CircData() {
		
	}
	public CircData(String cantidadPaises, String cantidadTweets, CircObject info) {
		super();
		this.cantidadPaises = cantidadPaises;
		this.cantidadTweets = cantidadTweets;
		this.info = info;
	}
	public String getCantidadPaises() {
		return cantidadPaises;
	}
	public void setCantidadPaises(String cantidadPaises) {
		this.cantidadPaises = cantidadPaises;
	}
	public String getCantidadTweets() {
		return cantidadTweets;
	}
	public void setCantidadTweets(String cantidadTweets) {
		this.cantidadTweets = cantidadTweets;
	}
	public CircObject getInfo() {
		return info;
	}
	public void setInfo(CircObject info) {
		this.info = info;
	}
	
	
}
	
	