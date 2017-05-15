package tk.ww3app.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.KeywordResumeFacade;
import tk.ww3app.model.KeywordResume;

@Stateless
public class KeywordResumePersistance extends AbstractFacade<KeywordResume> implements KeywordResumeFacade {
	
	
	@PersistenceContext(unitName = "KeywordResumePU")
	private EntityManager em;
	
	public KeywordResumePersistance() {
		super(KeywordResume.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}
