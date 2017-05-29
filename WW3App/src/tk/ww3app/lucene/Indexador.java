package tk.ww3app.lucene;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
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
	
	@EJB 
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

          List<Keyword> palabras = KWFacadeInjection.findAll();

          for (Keyword palabra : palabras) {
        	  System.out.println("Indexando keywords: " + palabra.getWord());
              IndexWriter writer = getIndexadoEscrito(false);
              Document doc = new Document();
              doc.add(new Field("keyword", palabra.getWord(), Field.Store.YES, Field.Index.TOKENIZED));
              
              writer.addDocument(doc);             
          }

          cerrarIndice();
     }  
    
  
    
    
}
