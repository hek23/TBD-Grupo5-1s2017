package tk.ww3app.facade;

import java.util.List;

import javax.ejb.Local;

import tk.ww3app.model.CountryStat;

@Local
public interface CountryStatFacade {

	public void create(CountryStat entity);

	public void edit(CountryStat entity);

	public void remove(CountryStat entity);

	public CountryStat find(Object id);

	public List<CountryStat> findAll();

	public List<CountryStat> findRange(int[] range);

	public int count();

}
