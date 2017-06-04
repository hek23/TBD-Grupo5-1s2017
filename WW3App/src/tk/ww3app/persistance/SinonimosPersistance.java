package tk.ww3app.persistance;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.KeywordFacade;
import tk.ww3app.facade.SinonimosFacade;
import tk.ww3app.model.Keyword;
import tk.ww3app.model.Sinonimos;

@Stateless
public class SinonimosPersistance extends AbstractFacade<Sinonimos> implements SinonimosFacade {
	
	
	@PersistenceContext(unitName = "SinonimosPU")
	private EntityManager em;
	
	public SinonimosPersistance() {
		super(Sinonimos.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}
	public boolean insertarSinonimo(String sinonimo, int idPalabra){
		//this.em.createNativeQuery("INSERT INTO Sinonimos (sinonimo, concepto) VALUES '"+ sinonimo+"', '"+idPalabra+"');");
		Query query = this.em.createNativeQuery("INSERT INTO Sinonimos (sinonimo, concepto) VALUES (?, ?)");
		query.setParameter(1, sinonimo);
		query.setParameter(2, idPalabra);
		query.executeUpdate();
		return true;
	}
	
	public boolean borrarSinonimo (String sinonimo, int idPalabra){
		Query query = this.em.createNativeQuery("DELETE FROM Sinonimos Where sinonimo = ? and concepto = ?");
		query.setParameter(1, sinonimo);
		query.setParameter(2, idPalabra);
		query.executeUpdate();
		return true;
	}
	
	public void borrarSinonimos (int idConcepto){
		Query query = this.em.createNativeQuery("DELETE FROM Sinonimos Where concepto = ?");
		query.setParameter(1, idConcepto);
		query.executeUpdate();
	}
		
	
}
