package tk.ww3app.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.CountryFacade;
import tk.ww3app.model.Country;

@Stateless
public class CountryPersistance extends AbstractFacade<Country> implements CountryFacade {
	
	
	@PersistenceContext(unitName = "CountryPU")
	private EntityManager em;
	
	public CountryPersistance() {
		super(Country.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

}
