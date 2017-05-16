package tk.ww3app.model;

import java.util.List;

public class CircObject {

	List<String> paises;
	List<String> tweets;
	
	public CircObject() {
		super();
	}
	public CircObject(List<String> paises, List<String> tweets) {
		super();
		this.paises = paises;
		this.tweets = tweets;
	}
	public List<String> getPaises() {
		return paises;
	}
	public void setPaises(List<String> paises) {
		this.paises = paises;
	}
	public List<String> getTweets() {
		return tweets;
	}
	public void setTweets(List<String> tweets) {
		this.tweets = tweets;
	}
	
	
	
}
