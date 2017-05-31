package tk.ww3app.persistance;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.KeywordResumeJSONFacade;
import tk.ww3app.model.KeywordJSONResume;

@Stateless
public class KeywordResumeJSONPersistance extends AbstractFacade<KeywordJSONResume> implements KeywordResumeJSONFacade {
	
	
	@PersistenceContext(unitName = "KeywordJSONPU")
	private EntityManager em;
	
	public KeywordResumeJSONPersistance() {
		super(KeywordJSONResume.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}
	
	public List<KeywordJSONResume> findByWord(String word){
		
		return this.em.createNamedQuery("KeywordJSONResume.findByWord", KeywordJSONResume.class).setParameter("word", word).getResultList();
		}

}
