package tk.ww3app.facade;

import java.util.List;

import javax.ejb.Local;

import tk.ww3app.model.Keyword;

@Local
public interface KeywordFacade {

	public void create(Keyword entity);

	public void edit(Keyword entity);

	public void remove(Keyword entity);

	public Keyword find(Object id);

	public List<Keyword> findAll();

	public List<Keyword> findRange(int[] range);

	public int count();

}
