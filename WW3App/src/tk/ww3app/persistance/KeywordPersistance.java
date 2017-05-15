package tk.ww3app.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

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

}
