package tk.ww3app.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;	
import java.nio.file.Paths;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import javax.ejb.EJB;
import tk.ww3app.model.Keyword;
import tk.ww3app.facade.KeywordFacade;

public class Indexador {
	
	KeywordFacade KWFacadeInjection;
    public Indexador() {
    }
    private IndexWriter escritura = null;
    
    public IndexWriter getIndexadoEscrito(boolean create) throws IOException {
        if (escritura == null) {
            escritura = new IndexWriter("src/tk/ww3app/lucene/lucene_index",
                                          new StandardAnalyzer(),
                                          create);

            
        }
        return escritura;
   }    
    
    public void cerrarIndice() throws IOException {
        if (escritura != null) {
            escritura.close();
        }
   }
  
    public void creaIndice() throws IOException {
          
          getIndexadoEscrito(true);

          List<String> palabras = null;
		try {
			palabras = obtenerPalabras();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

          for (String palabra : palabras) {
        	  System.out.println("Indexando keywords: " + palabra);
              IndexWriter writer = getIndexadoEscrito(false);
              Document doc = new Document();
              doc.add(new Field("keyword", palabra, Field.Store.YES, Field.Index.TOKENIZED));
              writer.addDocument(doc);             
          }

          cerrarIndice();
     }  
    
    public List<String> obtenerPalabras() throws SQLException{
    	try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
        Connection con = null;
        String sURL = "jdbc:mysql://localhost:3306/WW3App";
        con = DriverManager.getConnection(sURL,"root","root");
    	Statement stmt = null;
        String query = "select word " +
                       "from WW3App.Keyword";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            List<String> palabras = new ArrayList<String>();
            while (rs.next()) {
            	palabras.add(rs.getString("word"));
       
            }
            return palabras;
        } catch (SQLException e ) {
            System.out.println(e);
        } finally {
            if (stmt != null) { stmt.close(); }
        }
		return null;
    }
  
    
    
}
