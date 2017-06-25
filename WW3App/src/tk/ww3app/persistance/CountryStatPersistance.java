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
	
	public List<Object[]> findByCountry(String Country){
		String formato = "SELECT SUM(CountryStat.RetweetsCount), SUM(CountryStat.TweetsCount), CountryStat.Date as fecha, Country.Name as pais FROM CountryStat INNER JOIN Country ON "
				+ "(CountryStat.Country = Country.idCountry) WHERE Country.Name =" + "'" + Country + "' GROUP BY Fecha, pais;";
		List<Object[]> resultado = this.em.createNativeQuery(formato).getResultList();
		return resultado;
	
		}
	public void borrarStats(int idConcepto){
	
		Query query = this.em.createNativeQuery("DELETE FROM CountryStat where CountryStat.Keyword = ?");
		query.setParameter(1, idConcepto);
		query.executeUpdate();
	}
	
	public List<Object[]> getRankInfo(){
		List<Object[]> resultado =this.em.createNativeQuery("SELECT * FROM (SELECT Sum(CountryStat.RetweetsCount+ CountryStat.TweetsCount) AS puntaje, Country.Name as pais FROM WW3App.CountryStat INNER JOIN Country ON (CountryStat.Country = Country.idCountry) group by CountryStat.Country ORDER BY puntaje DESC) t where puntaje>0;").getResultList();
		return resultado;
	}
}
