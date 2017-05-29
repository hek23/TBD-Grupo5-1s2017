package tk.ww3app.lucene;

import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Hit;
import org.apache.lucene.search.Hits;

import tk.ww3app.lucene.Indexador;
import tk.ww3app.model.Keyword;
import tk.ww3app.facade.KeywordFacade;
import tk.ww3app.lucene.Buscador;

public class TestLucene {
	
	
	
	public TestLucene() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

      try {
	// Se construye el indice con Lucene
      	  System.out.println("Inicio creacion indice");
        
        Indexador  indexer = new Indexador();
        indexer.creaIndice();
        System.out.println("Indice creado!!");

        // Se realiza una Busqueda ejemplo
        System.out.println("Buscando..");
        Buscador instancia = new Buscador();
        Hits hits = instancia.busqueda(" trump ");

        System.out.println("Resultados Encontrados: " + hits.length());
        Iterator<Hit> iter =  hits.iterator();
        while(iter.hasNext()){
            Hit hit = iter.next();
            Document doc = hit.getDocument();
            System.out.println(doc.get("keyword")
                                                          
                               + " (" + hit.getScore() + ")");

        }
        
        System.out.println("Busqueda Finalizada");
      } 
      catch (Exception e) {
        System.out.println("Exception!!\n");
      }
    }
    
}



