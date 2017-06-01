package tk.ww3app.facade;

import java.util.List;

import javax.ejb.Local;

import tk.ww3app.model.Country;

@Local
public interface CountryFacade {

	public void create(Country entity);

	public void edit(Country entity);

	public void remove(Country entity);

	public Country find(Object id);

	public List<Country> findAll();

	public List<String> findNames();
	
	public List<Country> findRange(int[] range);

	public int count();

}
