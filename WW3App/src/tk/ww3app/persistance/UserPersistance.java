package tk.ww3app.persistance;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import tk.ww3app.facade.AbstractFacade;
import tk.ww3app.facade.UserFacade;
import tk.ww3app.model.User;

@Stateless
public class UserPersistance extends AbstractFacade<User> implements UserFacade {
	
	
	@PersistenceContext(unitName = "UserPU")
	private EntityManager em;
	
	public UserPersistance() {
		super(User.class);
	}

	@Override
	protected EntityManager getEntityManager() {
		return this.em;
	}

	public boolean checkUser(String user, String password){
		List<User> usuarios = this.em.createNativeQuery("SELECT * FROM WW3App.User WHERE username ='" + user + "' and password ='"+ password + "';",User.class).getResultList();
		if (usuarios.get(0) != null || usuarios.size() > 0){
			return true;
		}
		return false;
	}
}
