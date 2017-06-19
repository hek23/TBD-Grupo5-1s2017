package tk.ww3app.neo4j;

import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GraphTest { 
	private Driver driver;
    private Session session;
    

    public GraphTest(){
    	this.driver = GraphDatabase.driver( "bolt://localhost", AuthTokens.basic( "neo4j", "441441" ));
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
    
    public void crearPais(String nombrePais, int tweets, int idPais, String acronimo){
    	String consulta = "CREATE (a:Pais {abreviatura:'"+ acronimo + "', tweetsOriginados:"+ 
    			String.valueOf(tweets)+ ", idPais:"+String.valueOf(idPais)+ ", pais:'"+nombrePais+"'})";
    	this.session.run(consulta);
    }
    
    public void crearInfluencia (int paisOrigen, int paisDestino, int retweets){
    	String consulta = "MATCH (a:Pais),(b:Pais) WHERE a.idPais ="+String.valueOf(paisOrigen)+
    			" AND b.idPais ="+String.valueOf(paisDestino)+""
    			+" CREATE (a)-[r:Influencia {retweets:"+
    			String.valueOf(retweets)+"}]->(b)";
    	this.session.run(consulta);
    }
    
    public static Connection conexionMySQL(){
    	Connection con = null;
 
        String url = "jdbc:mysql://localhost:3306/WW3App";
        String user = "root";
        String password = "root";

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url, user, password);
            System.out.println("conection");
            return con;
            }
        catch (Exception e){
        	System.out.println("fail");
        	return null;
        }
    }

    public void obtenerNodosSQL (java.sql.Connection con){ 
    	java.sql.Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        String sql = "SELECT c.idCountry as CountryID, SUM(tweetsqty) as sumTweets, c.Name, c.Code FROM Influence inner join Country c on  (Influence.origin = c.idCountry) GROUP BY c.idCountry";
        ResultSet rs = null;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			while (rs.next()){
				this.crearPais(rs.getString(3), rs.getInt(2), rs.getInt(1), rs.getString(4).toLowerCase());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void obtenerArcosSQL (java.sql.Connection con){ 
    	java.sql.Statement st = null;
		try {
			st = con.createStatement();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String sql = "SELECT tweetsqty, c.idCountry, d.idCountry FROM Influence inner join Country c on (Influence.origin = c.idCountry) inner join Country d on (d.idCountry = Influence.destiny)";
    	ResultSet rs = null;
    	int i = 0;
    	try {
			rs = st.executeQuery(sql);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			while (rs.next()){
				this.crearInfluencia(rs.getInt(2), rs.getInt(3), rs.getInt(1));
				i = i+1;
			}
			System.out.println(String.valueOf(i));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public void showGraph(){
    	
    }
    
    //MATCH (n:Pais)-[r:Influencia]->(m:Pais) WHERE n.idPais=217 RETURN m LIMIT 25
    public void cerrarConexion(java.sql.Connection con){
    	try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
}