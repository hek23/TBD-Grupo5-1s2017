package tk.ww3app.service;

import java.util.List;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;

import tk.ww3app.facade.UserFacade;
import tk.ww3app.model.User;

@Path("/us")
@ApplicationPath("/")

public class UserService extends Application{
	
	@EJB 
	UserFacade FacadeInjection;
	
	Logger logger = Logger.getLogger(UserService.class.getName());
	
	@GET
	@Path("/listusers")
	@Produces("application/json")
	public List<User> findAll(){
		return FacadeInjection.findAll();
	}
	
	@GET
    @Path("/getuser")
    @Produces("application/json")
    public User find(@QueryParam("id") Integer id) {
        return FacadeInjection.find(id);
    }
	
	

}
