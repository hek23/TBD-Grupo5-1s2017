package tk.ww3app.persistance;

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

}
