package tk.ww3app.facade;

import java.util.List;

import javax.ejb.Local;

import tk.ww3app.model.KeywordResume;

@Local
public interface KeywordResumeFacade {

	public void create(KeywordResume entity);

	public void edit(KeywordResume entity);

	public void remove(KeywordResume entity);

	public KeywordResume find(Object id);

	public List<KeywordResume> findAll();

	public List<KeywordResume> findRange(int[] range);

	public int count();

}
