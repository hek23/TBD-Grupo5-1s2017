package tk.ww3app.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.CountryResumeFacade;
import tk.ww3app.model.CountryResume;

@Stateless
public class CountryResumePersistance extends AbstractFacade<CountryResume> implements CountryResumeFacade {
	
	
	@PersistenceContext(unitName = "CountryResumePU")
	private EntityManager em;
	
	public CountryResumePersistance() {
		super(CountryResume.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}
