package tk.ww3app.facade;

import java.util.List;

import javax.ejb.Local;

import tk.ww3app.model.KeywordJSONResume;

@Local
public interface KeywordResumeJSONFacade {

	public void create(KeywordJSONResume entity);

	public void edit(KeywordJSONResume entity);

	public void remove(KeywordJSONResume entity);

	public KeywordJSONResume find(Object id);

	public List<KeywordJSONResume> findAll();

	public List<KeywordJSONResume> findRange(int[] range);

	public int count();

}
