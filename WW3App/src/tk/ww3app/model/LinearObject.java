package tk.ww3app.model;

import java.util.List;

public class LinearObject {

	String concept;
	List<String> fechas;
	List<Integer> tweets;
	
	public LinearObject() {
		super();
	}
	public LinearObject(String concept, List<String> fechas, List<Integer> tweets) {
		super();
		this.concept = concept;
		this.fechas = fechas;
		this.tweets = tweets;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public List<String> getFechas() {
		return fechas;
	}
	public void setFechas(List<String> fechas) {
		this.fechas = fechas;
	}
	public List<Integer> getTweets() {
		return tweets;
	}
	public void setTweets(List<Integer> tweets) {
		this.tweets = tweets;
	}
	
}