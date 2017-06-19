package tk.ww3app.neo4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

import tk.ww3app.neo4j.GraphTest;

public class NeoApp {

    public static void main(String[] args) {
    	
    	GraphTest gt = new GraphTest();
    	gt.dumpGraphDatabase();
    	Connection cn = gt.conexionMySQL();
    	gt.obtenerNodosSQL(cn);
    	gt.obtenerArcosSQL(cn);
    	gt.cerrarConexion(cn);
    	gt.closeConnection();
    }
}

