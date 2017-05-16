package tk.ww3app.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
