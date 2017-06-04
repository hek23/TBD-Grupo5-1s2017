package tk.ww3app.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.KeywordFacade;
import tk.ww3app.model.Keyword;

@Stateless
public class KeywordPersistance extends AbstractFacade<Keyword> implements KeywordFacade {
	
	
	@PersistenceContext(unitName = "KeywordPU")
	private EntityManager em;
	
	public KeywordPersistance() {
		super(Keyword.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}
	
	public int insertarConcepto(String concepto){
		Query query = this.em.createNativeQuery("INSERT INTO Keyword " +
				"(word, creator) "+
				"VALUES (?, '1')");
		query.setParameter(1, concepto);
		query.executeUpdate();
		query = this.em.createNativeQuery("SELECT Keyword.idKeyword FROM Keyword where Keyword.word = ?");
		query.setParameter(1, concepto);
		return (int)query.getResultList().get(0);
	}

	public int buscarWord(String concepto){
		Query query = this.em.createNativeQuery("SELECT Keyword.idKeyword FROM Keyword where Keyword.word = ?");
		query.setParameter(1, concepto);
		return (int)query.getResultList().get(0);
	}
	
	public void deleteWord(int idconcepto){
		Query query = this.em.createNativeQuery("DELETE FROM Keyword where Keyword.idkeyword = ?");
		query.setParameter(1, idconcepto);
	}

}
