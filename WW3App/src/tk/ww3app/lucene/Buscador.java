package tk.ww3app.lucene;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;

public class Buscador {

	 private IndexSearcher buscador = null;
	    private QueryParser analizador = null;

	    public Buscador() throws IOException {
	        buscador = new IndexSearcher("src/tk/ww3app/lucene/lucene_index");
	        analizador = new QueryParser("Contenido", new StandardAnalyzer());
	    }
	    

	    
	    public Hits busqueda(String consulta)
	    throws IOException, ParseException {
	        Query query = analizador.parse(consulta);
	        
	        Hits hits = buscador.search(query);

	        return hits;
	    }
	    
	  
}
