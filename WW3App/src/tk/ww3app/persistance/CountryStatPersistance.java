package tk.ww3app.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.CountryStatFacade;
import tk.ww3app.model.CountryStat;

@Stateless
public class CountryStatPersistance extends AbstractFacade<CountryStat> implements CountryStatFacade {
	
	
	@PersistenceContext(unitName = "CountryStatPU")
	private EntityManager em;
	
	public CountryStatPersistance() {
		super(CountryStat.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}
