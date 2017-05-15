package tk.ww3app.facade;

import java.util.List;

import javax.ejb.Local;

import tk.ww3app.model.CountryResume;

@Local
public interface CountryResumeFacade {

	public void create(CountryResume entity);

	public void edit(CountryResume entity);

	public void remove(CountryResume entity);

	public CountryResume find(Object id);

	public List<CountryResume> findAll();

	public List<CountryResume> findRange(int[] range);

	public int count();

}
