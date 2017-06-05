/*package tk.ww3app.service;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;


@Path("/grafo")
@ApplicationPath("/")

public class GraphTest extends Application{
	private Driver driver;
    private Session session;
    
    public GraphTest(){
    	this.driver =GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "neo4j" ));
    	this.session= driver.session();
    }
    
    public void dumpGraphDatabase(){
    	//Dump de Base de datos
    	this.session.run("match (a)-[r]->(b) delete r");
        this.session.run("match (n) delete n");
    }
    
    public void closeConnection(){
    	this.session.close();
        this.driver.close();
    }
    
    public void crearPais(String nombrePais, int tweets, int retweets, int idPais){
    	String consulta = "CREATE ("+String.valueOf(idPais)+ 
    			":Pais {pais:"+nombrePais+", tweetsOriginados:"+ 
    			String.valueOf(tweets)+", retweetsOriginados:"+ String.valueOf(retweets)+"})";
    	this.session.run(consulta);
    }
    
    public void crearInfluencia (int paisOrigen, int paisDestino, int retweets){
    	String consulta = "("+String.valueOf(paisOrigen)+")-[:Influencia {retweets:"+
    			String.valueOf(retweets)+"}]->("+String.valueOf(paisDestino)+")";
    	this.session.run(consulta);
    }
    
    //SERVICIOS
    /*public JsonObject getAllInfo(){
    	
    	JsonArrayBuilder arrayNodos = Json.createArrayBuilder();
    	JsonObjectBuilder builderExterno = Json.createObjectBuilder();
    }
    public static void main(String[] args) {
    	Estados Unidos
    	ID 1
    	Tamaño 100
    	Target XX
    }
}*/