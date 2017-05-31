package tk.ww3app.persistance;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.CountryStatFacade;
import tk.ww3app.model.CountryStat;
import tk.ww3app.model.KeywordJSONResume;

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
	
	public List<CountryStat> findByCountry(String Country){
		String formato = "SELECT CountryStat.* FROM CountryStat INNER JOIN Country ON "
				+ "(CountryStat.Country = Country.idCountry) WHERE Country.Name =" + "'" + Country + "';";
		return this.em.createNativeQuery(formato, CountryStat.class).getResultList();
		}

}
