package tk.ww3app.facade;

import java.util.List;

import javax.ejb.Local;

import tk.ww3app.model.Keyword;
import tk.ww3app.model.Sinonimos;

@Local
public interface SinonimosFacade {

	public void create(Sinonimos entity);

	public void edit(Sinonimos entity);

	public void remove(Sinonimos entity);

	public Sinonimos find(Object id);

	public List<Sinonimos> findAll();

	public List<Sinonimos> findRange(int[] range);
	
	public boolean insertarSinonimo(String concepto, int idPalabra);
	public boolean borrarSinonimo (String sinonimo, int idPalabra);
	public void borrarSinonimos (int idConcepto);

	public int count();

}
